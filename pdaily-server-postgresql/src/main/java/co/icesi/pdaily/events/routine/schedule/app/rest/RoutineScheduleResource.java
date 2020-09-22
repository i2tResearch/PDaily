package co.icesi.pdaily.events.routine.schedule.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.routine.schedule.app.RoutineScheduleAppService;
import co.icesi.pdaily.events.routine.schedule.app.RoutineScheduleDTO;
import co.icesi.pdaily.events.routine.schedule.domain.model.view.RoutineScheduleReadView;

@Path("/schedule/routine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoutineScheduleResource {
	@Inject
	RoutineScheduleAppService appService;

	@POST
	public RoutineScheduleDTO saveRoutineSchedule(RoutineScheduleDTO dto) {
		return appService.saveRoutineSchedule( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<RoutineScheduleReadView> findAll(@PathParam("patientId") String patientId) {
		return appService.findAllByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public RoutineScheduleReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteRoutineSchedule(@PathParam("id") String id) {
		appService.deleteRoutineSchedule( id );
	}
}
