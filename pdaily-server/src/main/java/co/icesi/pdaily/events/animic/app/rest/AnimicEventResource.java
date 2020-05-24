package co.icesi.pdaily.events.animic.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.events.animic.app.AnimicEventAppService;
import co.icesi.pdaily.events.animic.app.AnimicEventDTO;
import co.icesi.pdaily.events.animic.domain.model.view.AnimicEventReadView;

@Path("/event/animic")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnimicEventResource {
	@Inject
	AnimicEventAppService appService;

	@POST
	public AnimicEventDTO saveAnimicEvent(AnimicEventDTO dto) {
		return appService.saveAnimicEvent( dto );
	}

	@GET
	@Path("/for-patient/{patientId}")
	public List<AnimicEventReadView> findAll(@PathParam("patientId") String patientId) {
		return appService.findAllByPatient( patientId );
	}

	@GET
	@Path("/{id}")
	public AnimicEventReadView findById(@PathParam("id") String id) {
		return appService.findByIdAsReadView( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteAnimicEvent(@PathParam("id") String id) {
		appService.deleteAnimicEvent( id );
	}
}
