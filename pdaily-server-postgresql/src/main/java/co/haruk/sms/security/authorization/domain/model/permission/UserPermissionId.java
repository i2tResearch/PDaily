package co.haruk.sms.security.authorization.domain.model.permission;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.security.authorization.domain.model.ProtectedActivityId;
import co.haruk.sms.security.user.domain.model.UserId;

/**
 * @author andres2508 on 1/5/20
 **/
@Embeddable
public class UserPermissionId implements Serializable {
	@AttributeOverride(name = "id", column = @Column(name = "user_id"))
	private UserId userId;
	@AttributeOverride(name = "id", column = @Column(name = "activity_id"))
	private ProtectedActivityId activityId;
	@AttributeOverride(name = "id", column = @Column(name = "tenant_id"))
	private TenantId tenantId;

	protected UserPermissionId() {
	}

	private UserPermissionId(UserId userId, TenantId tenantId, ProtectedActivityId activityId) {
		this.userId = requireNonNull( userId, "El identificador de usuario es requerido" );
		this.activityId = requireNonNull( activityId, "El identificador de la actividad es requerido" );
		this.tenantId = requireNonNull( tenantId, "El identificador del cliente es requerido" );
	}

	public static UserPermissionId of(UserId userId, TenantId tenantId, ProtectedActivityId activityId) {
		return new UserPermissionId( userId, tenantId, activityId );
	}

	public UserId userId() {
		return userId;
	}

	public ProtectedActivityId activityId() {
		return activityId;
	}

	public TenantId tenantId() {
		return tenantId;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		UserPermissionId that = (UserPermissionId) o;
		return userId.equals( that.userId ) &&
				tenantId.equals( that.tenantId ) &&
				activityId.equals( that.activityId );
	}

	@Override
	public int hashCode() {
		return Objects.hash( userId, tenantId, activityId );
	}
}
