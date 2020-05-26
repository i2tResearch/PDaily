package co.haruk.sms.clinical.base.routines.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.clinical.base.routines.app.RoutineTypeAppService;
import co.haruk.sms.clinical.base.routines.app.RoutineTypeDTO;

@Path("/routine/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoutineTypeResource {
	@Inject
	RoutineTypeAppService appService;

	@GET
	public List<RoutineTypeDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public RoutineTypeDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public RoutineTypeDTO saveRoutineType(RoutineTypeDTO dto) {
		return appService.saveRoutineType( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteRoutineType( id );
	}

}
