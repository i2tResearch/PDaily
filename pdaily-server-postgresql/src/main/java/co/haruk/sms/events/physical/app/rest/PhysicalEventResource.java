package co.haruk.sms.events.physical.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.events.physical.app.PhysicalEventAppService;
import co.haruk.sms.events.physical.app.PhysicalEventDTO;
import co.haruk.sms.events.physical.domain.model.view.PhysicalEventReadView;

@Path("/event/physical")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PhysicalEventResource {
	@Inject
	PhysicalEventAppService appService;

	@POST
	public PhysicalEventReadView savePhysicalEvent(PhysicalEventDTO dto) {
		return appService.savePhysicalEvent( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<PhysicalEventReadView> findAll(@PathParam("patientId") String patientId) {
		return appService.findAllByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public PhysicalEventReadView findById(@PathParam("id") String id) {
		return appService.findByIdAsReadView( id );
	}

	@DELETE
	@Path("/{id}")
	public void deletePhysicalEvent(@PathParam("id") String id) {
		appService.deletePhysicalEvent( id );
	}

}
