package co.icesi.pdaily.events.food.event.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.food.event.app.FoodEventAppService;
import co.icesi.pdaily.events.food.event.app.FoodEventDTO;

@Path("/event/food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodEventResource {
	@Inject
	FoodEventAppService appService;

	@POST
	public FoodEventDTO saveFoodEvent(FoodEventDTO dto) {
		return appService.saveFoodEvent( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<FoodEventDTO> findByPatient(@PathParam("patientId") String patientId) {
		return appService.findByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public FoodEventDTO findById(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteFoodEvent(@PathParam("id") String id) {
		appService.deleteFoodEvent( id );
	}
}
