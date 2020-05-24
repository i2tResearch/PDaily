package co.haruk.sms.security.authorization.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.security.authorization.app.AuthorizationAppService;
import co.haruk.sms.security.authorization.app.AuthorizationGroupDTO;
import co.haruk.sms.security.authorization.app.AuthorizationResult;
import co.haruk.sms.security.authorization.app.UserPermissionDTO;
import co.haruk.sms.subscription.account.app.AccountDTO;

/**
 * @author cristhiank on 1/5/20
 **/
@Path("/security/authz")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

	@Inject
	AuthorizationAppService appService;

	@GET
	@Path("/groups")
	public List<AuthorizationGroupDTO> getAllGroups(@QueryParam("iacts") boolean includeActivities) {
		if ( includeActivities ) {
			return appService.findAllGroupsIncActivities();
		} else {
			return appService.findAllGroups();
		}
	}

	@GET
	@Path("/accounts-for-user/{userId}")
	public List<AccountDTO> availableAccountsForUser(@PathParam("userId") String userId) {
		return appService.availableAccountsForUser( userId );
	}

	@GET
	@Path("/activities-granted-to-user/{userId}/{tenantId}")
	public List<String> getActivitiesGrantedToUser(@PathParam("userId") String userId,
			@PathParam("tenantId") String tenantId) {
		return appService.getActivitiesGrantedToUser( userId, tenantId );
	}

	@GET
	@Path("/is-authorized-to/{activityId}")
	public AuthorizationResult currentUserIsAuthorizedTo(@PathParam("activityId") String activityId) {
		return appService.currentUserIsAuthorizedTo( activityId );
	}

	@POST
	@Path("/grant-permission")
	public void grantPermission(UserPermissionDTO dto) {
		appService.grantUserPermission( dto );
	}

	@POST
	@Path("/revoke-permission")
	public void revokePermission(UserPermissionDTO dto) {
		appService.revokeUserPermission( dto );
	}
}
