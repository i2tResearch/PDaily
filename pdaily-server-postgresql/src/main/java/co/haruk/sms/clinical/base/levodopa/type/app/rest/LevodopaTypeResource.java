package co.haruk.sms.clinical.base.levodopa.type.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.clinical.base.levodopa.type.app.LevodopaTypeAppService;
import co.haruk.sms.clinical.base.levodopa.type.app.LevodopaTypeDTO;

@Path("/medicine/levodopa/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LevodopaTypeResource {
	@Inject
	LevodopaTypeAppService appService;

	@GET
	public List<LevodopaTypeDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public LevodopaTypeDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public LevodopaTypeDTO saveLevodopaType(LevodopaTypeDTO dto) {
		return appService.saveLevodopaType( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteLevodopaType( id );
	}
}
