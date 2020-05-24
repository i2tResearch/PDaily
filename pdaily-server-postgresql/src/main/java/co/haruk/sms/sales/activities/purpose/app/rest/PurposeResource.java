package co.haruk.sms.sales.activities.purpose.app.rest;

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

import co.haruk.sms.sales.activities.purpose.app.PurposeAppService;
import co.haruk.sms.sales.activities.purpose.app.PurposeDTO;

@Path("/sales/activities/purpose")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PurposeResource {
	@Inject
	PurposeAppService appService;

	@GET
	public List<PurposeDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public PurposeDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public PurposeDTO savePurpose(PurposeDTO dto) {
		return appService.savePurpose( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deletePurpose( id );
	}
}
