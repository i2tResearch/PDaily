package co.haruk.sms.security.authorization.infraestructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.security.authorization.domain.model.permission.UserPermission;
import co.haruk.sms.security.user.domain.model.UserId;

/**
 * @author andres2508 on 1/5/20
 **/
@ApplicationScoped
public class UserPermissionRepository extends JPARepository<UserPermission> {

	public List<UserPermission> findForUserInTenant(UserId userId, TenantId tenantId) {
		requireNonNull( userId );
		requireNonNull( tenantId );
		return findWithNamedQuery(
				UserPermission.findForUserInTenant,
				QueryParameter.with( "userId", userId ).and( "tenantId", tenantId ).parameters()
		);
	}
}
