package co.haruk.sms.events.food.schedule.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.events.food.schedule.app.FoodScheduleAppService;
import co.haruk.sms.events.food.schedule.app.FoodScheduleDTO;

@Path("/schedule/food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodScheduleResource {
	@Inject
	FoodScheduleAppService appService;

	@POST
	public FoodScheduleDTO saveFoodSchedule(FoodScheduleDTO dto) {
		return appService.saveFoodSchedule( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<FoodScheduleDTO> findAll(@PathParam("patientId") String patientId) {
		return appService.findAllByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public FoodScheduleDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteFoodSchedule(@PathParam("id") String id) {
		appService.deleteFoodSchedule( id );
	}
}
