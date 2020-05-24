package co.haruk.sms.business.structure.subsidiary.salesrep.app.rest;

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

import co.haruk.sms.business.structure.subsidiary.salesrep.app.SalesRepAppService;
import co.haruk.sms.business.structure.subsidiary.salesrep.app.SalesRepRequestDTO;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;

/**
 * @author cristhiank on 25/11/19
 **/
@Path("/business/sales-rep")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesRepResource {

	@Inject
	SalesRepAppService appService;

	@GET
	public List<SalesRepReadView> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/available-users-for-subsidiary/{subsidiaryId}")
	public List<SalesRepReadView> findAvailableForSubsidiary(@PathParam("subsidiaryId") String subsidiaryId) {
		return appService.findAvailableUsersForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/for-subsidiary/{subsidiaryId}")
	public List<SalesRepReadView> findForSubsidiary(@PathParam("subsidiaryId") String subsidiaryId) {
		return appService.findForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/{id}")
	public SalesRepReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteEntity(@PathParam("id") String id) {
		appService.delete( id );
	}

	@POST
	@Path("/save-for-user/{userId}")
	public SalesRepReadView createForUser(@PathParam("userId") String userId, SalesRepRequestDTO request) {
		appService.saveForUser( userId, request );
		return appService.findById( userId );
	}

	@POST
	@Path("/{salesRepId}/business-unit/{businessId}")
	public SalesRepReadView addBusinessUnit(@PathParam("salesRepId") String salesRepId,
			@PathParam("businessId") String businessId) {
		return appService.addBusinessUnit( businessId, salesRepId );
	}
}
