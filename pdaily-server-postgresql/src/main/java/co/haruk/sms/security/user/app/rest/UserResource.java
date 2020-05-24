package co.haruk.sms.security.user.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.security.user.app.UserAppService;
import co.haruk.sms.security.user.app.UserDTO;

/**
 * @author andres2508 on 16/11/19
 **/
@Path("/subscription/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	UserAppService appService;

	@GET
	public List<UserDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-account/{accountId}")
	public List<UserDTO> findForAccount(@PathParam("accountId") String accountId) {
		return appService.findForAccount( accountId );
	}

	@GET
	@Path("/{id}")
	public UserDTO findById(@PathParam("id") String id) {
		return appService.findByIdOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteUser( id );
	}

	@POST
	public UserDTO saveEntity(UserDTO dto) {
		return appService.saveUser( dto );
	}

	@POST
	@Path("/reset-password/{id}")
	public void resetPassword(@PathParam("id") String userId, String newPassword) {
		appService.resetPassword( userId, newPassword );
	}

	@GET
	@Path("/exists-email/{email}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean existsEmail(@PathParam("email") String email) {
		return appService.existsEmail( email );
	}

	@GET
	@Path("/exists-username/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean existsUsername(@PathParam("username") String username) {
		return appService.existsUserName( username );
	}
}
