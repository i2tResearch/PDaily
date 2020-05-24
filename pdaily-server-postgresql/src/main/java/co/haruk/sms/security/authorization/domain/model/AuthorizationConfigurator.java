package co.haruk.sms.security.authorization.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.util.Collection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.security.authorization.infraestructure.persistence.AuthorizationGroupRepository;
import co.haruk.sms.security.authorization.infraestructure.persistence.ProtectedActivityRepository;

/**
 * @author andres2508 on 1/5/20
 **/
@ApplicationScoped
public class AuthorizationConfigurator {
	private static final Logger log = Logger.getLogger( AuthorizationConfigurator.class.getName() );
	@Inject
	Instance<IAuthorizationProducer> activityProducers;
	@Inject
	AuthorizationGroupRepository groupRepository;
	@Inject
	ProtectedActivityRepository activityRepository;
	@Inject
	UserTransaction transaction;

	public void configureAuthorization() {
		log.info( "Configuring HARUK authorization " );
		saveAuthorizationGroups( discoverGroups() );
	}

	public Set<AuthorizationGroup> discoverGroups() {
		return activityProducers.stream()
				.flatMap( it -> it.authorizationGroups().stream() )
				.collect( Collectors.toSet() );
	}

	public void saveAuthorizationGroups(Collection<AuthorizationGroup> groups) {
		log.fine( () -> "Security Groups Count: " + groups.size() );
		// Deletes removed groups
		removeNonExistent( groups );
		// Create new or update existent groups
		groups.forEach( this::saveAuthorizationGroup );
	}

	private void removeNonExistent(Collection<AuthorizationGroup> groups) {
		for ( AuthorizationGroup current : groupRepository.findAll() ) {
			if ( !groups.contains( current ) ) {
				try {
					transaction.begin();
					groupRepository.delete( current.id() );
					transaction.commit();
				} catch (Exception ex) {
					log.log( Level.SEVERE, "Error eliminando grupo de autorización", ex );
					JTAUtils.rollback( transaction );
					throw new IllegalStateException( "Error eliminando grupo de autorización", ex );
				}
			}
		}
	}

	public void saveAuthorizationGroup(AuthorizationGroup changed) {
		require( !changed.activities().isEmpty(), "El grupo de autorización debe tener actividades" );
		try {
			transaction.begin();
			final var savedGroup = findOrCreateGroup( changed );
			savedGroup.setName( changed.name() );
			for ( ProtectedActivity activity : changed.activities() ) {
				savedGroup.addActivity( activity );
				final var savedActivity = findOrCreateActivity( activity );
				savedActivity.setDescription( activity.description() );
			}
			final var currentActivities = savedGroup.activities().iterator();
			while ( currentActivities.hasNext() ) {
				final var current = currentActivities.next();
				if ( !changed.activities().contains( current ) ) {
					currentActivities.remove();
					activityRepository.delete( current.id() );
				}
			}
			transaction.commit();
		} catch (Exception ex) {
			log.log( Level.SEVERE, "Error creando grupo de autorización", ex );
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( "Error creando grupo de autorización", ex );
		}
	}

	private ProtectedActivity findOrCreateActivity(ProtectedActivity activity) {
		return activityRepository.find( activity.id() )
				.orElseGet( () -> activityRepository.create( activity ) );
	}

	private AuthorizationGroup findOrCreateGroup(AuthorizationGroup group) {
		return groupRepository.find( group.id() )
				.orElseGet( () -> groupRepository.create( group ) );
	}

}
