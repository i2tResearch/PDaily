package co.icesi.pdaily.events.physical.body.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.physical.body.app.BodyPartAppService;
import co.icesi.pdaily.events.physical.body.app.BodyPartDTO;

@Path("/events/physical/body_part")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BodyPartResource {
	@Inject
	BodyPartAppService appService;

	@GET
	public List<BodyPartDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public BodyPartDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public BodyPartDTO saveBodyPart(BodyPartDTO dto) {
		return appService.saveBodyPart( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteBodyPart( id );
	}
}
