package co.icesi.pdaily.subscription.account.app.rest;

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

import co.icesi.pdaily.subscription.account.app.AccountAppService;
import co.icesi.pdaily.subscription.account.app.AccountDTO;

/**
 * @author andres2508 on 30/10/19
 **/
@Path("/subscription/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@Inject
	AccountAppService appService;

	@POST
	public AccountDTO saveAccount(AccountDTO dto) {
		return appService.saveAccount( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteAccount(@PathParam("id") String id) {
		appService.deleteAccount( id );
	}

	@GET
	@Path("/{id}")
	public AccountDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@GET
	public List<AccountDTO> findForCurrentUser() {
		return appService.forCurrentUser();
	}
}
