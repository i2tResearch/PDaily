package co.icesi.pdaily.events.levodopa.schedule.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.levodopa.schedule.app.LevodopaScheduleAppService;
import co.icesi.pdaily.events.levodopa.schedule.app.LevodopaScheduleDTO;
import co.icesi.pdaily.events.levodopa.schedule.domain.model.view.LevodopaScheduleReadView;

@Path("/schedule/levodopa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LevodopaScheduleResource {
	@Inject
	LevodopaScheduleAppService appService;

	@POST
	public LevodopaScheduleDTO saveLevodopaSchedule(LevodopaScheduleDTO dto) {
		return appService.saveLevodopaSchedule( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<LevodopaScheduleReadView> findAll(@PathParam("patientId") String patientId) {
		return appService.findAllByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public LevodopaScheduleReadView findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteLevodopaSchedule(@PathParam("id") String id) {
		appService.deleteLevodopaSchedule( id );
	}
}
