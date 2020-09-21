package co.icesi.pdaily.security.authorization.app;

import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.common.model.tenancy.TenantId;
import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivityId;
import co.icesi.pdaily.security.authorization.domain.model.permission.UserPermission;
import co.icesi.pdaily.security.authorization.domain.model.permission.UserPermissionId;
import co.icesi.pdaily.security.user.domain.model.UserId;
import co.icesi.pdaily.security.user.domain.model.UserName;

/**
 * @author andres2508 on 1/5/20
 **/
public class UserPermissionDTO {
	public String userId;
	public String tenantId;
	public String activityId;
	public Long grantedOn;
	public String grantedBy;

	protected UserPermissionDTO() {
	}

	private UserPermissionDTO(String userId, String tenantId, String activityId) {
		this.userId = userId;
		this.tenantId = tenantId;
		this.activityId = activityId;
	}

	public UserPermissionDTO(String userId, String tenantId, String activityId, Long grantedOn, String grantedBy) {
		this( userId, tenantId, activityId );
		this.grantedOn = grantedOn;
		this.grantedBy = grantedBy;
	}

	public static UserPermissionDTO of(String userId, String tenantId, String activityId) {
		return new UserPermissionDTO( userId, tenantId, activityId );
	}

	public static UserPermissionDTO of(UserPermission permission) {
		return new UserPermissionDTO(
				permission.id().userId().text(),
				permission.id().tenantId().text(),
				permission.id().activityId().text(),
				permission.grantedOn().dateAsLong(),
				permission.grantedBy().text()
		);
	}

	public UserPermission toUserPermission() {
		return UserPermission.of(
				UserPermissionId.of(
						UserId.of( this.userId ),
						TenantId.of( this.tenantId ),
						ProtectedActivityId.of( activityId )
				),
				UTCDateTime.now(),
				UserName.of( HarukSession.currentUser().username )
		);
	}

	public UserPermissionId toUserPermissionId() {
		return UserPermissionId.of(
				UserId.of( this.userId ),
				TenantId.of( this.tenantId ),
				ProtectedActivityId.of( activityId )
		);
	}
}
