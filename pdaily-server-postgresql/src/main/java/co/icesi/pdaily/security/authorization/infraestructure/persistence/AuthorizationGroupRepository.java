package co.icesi.pdaily.security.authorization.infraestructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroup;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroupId;

/**
 * @author andres2508 on 1/5/20
 **/
@ApplicationScoped
public class AuthorizationGroupRepository extends JPARepository<AuthorizationGroup> {

	public AuthorizationGroup findOrFailWithActivities(AuthorizationGroupId id) {
		requireNonNull( id );
		return super.findOrFail( id, AuthorizationGroup.graphActivities );
	}
}
