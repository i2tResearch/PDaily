package co.icesi.pdaily.security.authorization.domain.model;

import static co.haruk.core.StreamUtils.findOrFail;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.PdailyEntity;

/**
 * @author andres2508 on 1/5/20
 **/
@Entity
@Table(name = "security_auth_groups")
@NamedEntityGraph(name = AuthorizationGroup.graphActivities, attributeNodes = @NamedAttributeNode("activities"))
public class AuthorizationGroup extends PdailyEntity<AuthorizationGroupId> {
	private static final String PREFIX = "AuthorizationGroup.";
	public static final String graphActivities = PREFIX + "graphActivities";
	@EmbeddedId
	private AuthorizationGroupId id;
	@Embedded
	private PlainName name;
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<ProtectedActivity> activities = new HashSet<>();

	protected AuthorizationGroup() {
	}

	private AuthorizationGroup(AuthorizationGroupId id, PlainName name) {
		this.id = requireNonNull( id, "El identificador del grupo es requerido" );
		setName( name );
	}

	public static AuthorizationGroup of(AuthorizationGroupId id, PlainName name) {
		return new AuthorizationGroup( id, name );
	}

	@Override
	public AuthorizationGroupId id() {
		return id;
	}

	@Override
	public void setId(AuthorizationGroupId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del grupo es requerido" );
	}

	public Set<ProtectedActivity> activities() {
		return activities;
	}

	public void addActivity(String activityId, String activityName) {
		addActivity(
				ProtectedActivity.of( ProtectedActivityId.of( activityId ),
						PlainName.of( activityName ),
						this
				)
		);
	}

	public void addActivity(ProtectedActivity activity) {
		activity.setGroup( this );
		activities.add( activity );
	}

	public void removeActivity(ProtectedActivityId id) {
		activities.removeIf( it -> it.id().equals( id ) );
	}

	public ProtectedActivity getActivity(ProtectedActivityId activityId) {
		return findOrFail( activities, it -> it.id().equals( activityId ), "No se encontró la actividad" );
	}
}
