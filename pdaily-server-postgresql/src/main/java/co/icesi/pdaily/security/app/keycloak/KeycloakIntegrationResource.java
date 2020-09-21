package co.icesi.pdaily.security.app.keycloak;

import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.security.user.app.UserAppService;
import co.icesi.pdaily.subscription.account.app.AccountAppService;

/**
 * @author andres2508 on 9/2/20
 **/
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeycloakIntegrationResource {

	/**
	 * Endpoints consumidos por Keycloak
	 *
	 * peopleTarget = serviceTarget.path( "/people" ); NO IMPLEMENTADO
	 * checkCredentialsTarget = serviceTarget.path( "/people/validate-credentials" ); OK
	 * countPeopleTarget = serviceTarget.path( "/people/count" ); OK
	 * getAllPeopleTarget = serviceTarget.path( "/people/all" ); OK
	 * changePasswordTarget = serviceTarget.path( "/people/change-password" ); OK
	 * findByEmailTarget = serviceTarget.path( "/people/by-email/{email}" );
	 * findByUsernameTarget = serviceTarget.path( "/people/by-username/{username}" ); OK
	 * searchUserTarget = serviceTarget.path( "/people/search/{term}" ); OK
	 * deleteUserTarget = serviceTarget.path( "/people/{id}" ); NO IMPLEMENTADO
	 * findPersonCompaniesTarget = serviceTarget.path( "/people/companies-for/{id}" ); OK
	 * findPersonRolesTarget = serviceTarget.path( "/security/role/for-user/{id}" ); OK
	 */
	@Inject
	UserAppService userAppService;
	@Inject
	AccountAppService accountAppService;

	@GET
	@Path("/people/all")
	public List<KeycloakUserDTO> findAll(@QueryParam("first") int first, @QueryParam("max") int max) {
		final var all = userAppService.findAllPaged( first, max );
		return StreamUtils.map( all, KeycloakUserDTO::of );
	}

	@GET
	@Path("/people/count")
	public JsonObject countAll() {
		var builder = Json.createObjectBuilder();
		final int count = userAppService.countAll();
		builder.add( "count", count );
		return builder.build();
	}

	@POST
	@Path("/people/validate-credentials")
	public Response validateCredentials(KeycloakCredentialDTO credential) {
		final boolean valid = userAppService.isValidPassword( credential.username, credential.rawPassword );
		if ( !valid ) {
			return Response.status( Response.Status.UNAUTHORIZED ).build();
		}
		return Response.ok().build();
	}

	@POST
	@Path("/people/change-password")
	public Response changePassword(KeycloakCredentialDTO credential) {
		final var found = userAppService.findByUserNameOrFail( credential.username );
		userAppService.resetPassword( found.id, credential.rawPassword );
		return Response.ok().build();
	}

	@GET
	@Path("/people/search/{term}")
	public List<KeycloakUserDTO> searchPerson(@PathParam("term") String term,
			@QueryParam("first") int first,
			@QueryParam("max") int max) {
		final var all = userAppService.searchPerson( term, first, max );
		return StreamUtils.map( all, KeycloakUserDTO::of );
	}

	@GET
	@Path("/people/by-username/{username}")
	public KeycloakUserDTO findByUserName(@PathParam("username") String username) {
		final var found = userAppService.findByUserNameOrFail( username );
		return KeycloakUserDTO.of( found );
	}

	@GET
	@Path("/people/companies-for/{id}")
	public List<KeycloakGroupDTO> findGroupsForUser(@PathParam("id") String userId) {
		final var accountId = userAppService.findByIdOrFail( userId ).accountId;
		final var account = accountAppService.findById( accountId );
		return List.of( KeycloakGroupDTO.of( account ) );
	}

	@GET
	@Path("/security/role/for-user/{id}")
	public List<KeycloakRoleDTO> findRolesForUser(@PathParam("id") String userId) {
		userAppService.findByIdOrFail( userId );
		return List.of( KeycloakRoleDTO.of( "612C2FC5-A2F6-4285-B6F6-3228AE430E20", "USER" ) );
	}
}
