package co.icesi.pdaily.clinical.base.levodopa.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.clinical.base.levodopa.app.LevodopaMedicineAppService;
import co.icesi.pdaily.clinical.base.levodopa.app.LevodopaMedicineDTO;

@Path("/medicine/levodopa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LevodopaMedicineResource {
	@Inject
	LevodopaMedicineAppService appService;

	@GET
	public List<LevodopaMedicineDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public LevodopaMedicineDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public LevodopaMedicineDTO saveLevodopaMedicine(LevodopaMedicineDTO dto) {
		return appService.saveLevodopaMedicine( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteLevodopaMedicine( id );
	}
}
