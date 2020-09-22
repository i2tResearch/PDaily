package co.icesi.pdaily.events.levodopa.event.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.levodopa.event.app.LevodopaEventAppService;
import co.icesi.pdaily.events.levodopa.event.app.LevodopaEventDTO;

@Path("/event/levodopa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LevodopaEventResource {
	@Inject
	LevodopaEventAppService appService;

	@POST
	public LevodopaEventDTO saveLevodopaEvent(LevodopaEventDTO dto) {
		return appService.saveLevodopaEvent( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<LevodopaEventDTO> findByPatient(@PathParam("patientId") String patientId) {
		return appService.findByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public LevodopaEventDTO findById(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteLevodopaEvent(@PathParam("id") String id) {
		appService.deleteLevodopaEvent( id );
	}
}
