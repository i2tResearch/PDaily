package co.icesi.pdaily.sales.activities.purpose.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.sales.activities.purpose.app.PurposeAppService;
import co.icesi.pdaily.sales.activities.purpose.app.PurposeDTO;

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
