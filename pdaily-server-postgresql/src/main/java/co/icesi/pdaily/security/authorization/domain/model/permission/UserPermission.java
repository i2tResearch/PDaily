package co.icesi.pdaily.security.authorization.domain.model.permission;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.security.user.domain.model.UserName;

/**
 * @author andres2508 on 1/5/20
 **/
@Entity
@Table(name = "security_auth_user_permissions")
@NamedQuery(name = UserPermission.findForUserInTenant, query = "SELECT u FROM UserPermission u WHERE u.id.userId = :userId AND u.id.tenantId = :tenantId")
public class UserPermission {
	private static final String PREFIX = "UserPermission.";
	public static final String findForUserInTenant = PREFIX + "findForUserInTenant";
	@EmbeddedId
	private UserPermissionId id;
	@AttributeOverride(name = "date", column = @Column(name = "granted_on"))
	private UTCDateTime grantedOn;
	@AttributeOverride(name = "text", column = @Column(name = "granted_by"))
	private UserName grantedBy;

	protected UserPermission() {
	}

	private UserPermission(
			UserPermissionId id,
			UTCDateTime grantedOn,
			UserName grantedBy) {
		this.id = requireNonNull( id, "El identificador es requerido" );
		this.grantedOn = requireNonNull( grantedOn, "La fecha de asignación es requerida" );
		this.grantedBy = requireNonNull( grantedBy, "El usuario que asigna es requerido" );
	}

	public static UserPermission of(
			UserPermissionId id,
			UTCDateTime grantedOn,
			UserName grantedBy) {
		return new UserPermission( id, grantedOn, grantedBy );
	}

	public UserPermissionId id() {
		return id;
	}

	public UTCDateTime grantedOn() {
		return grantedOn;
	}

	public UserName grantedBy() {
		return grantedBy;
	}
}
