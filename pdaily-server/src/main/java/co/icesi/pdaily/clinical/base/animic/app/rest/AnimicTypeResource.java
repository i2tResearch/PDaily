package co.icesi.pdaily.clinical.base.animic.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import co.icesi.pdaily.clinical.base.animic.app.AnimicTypeAppService;
import co.icesi.pdaily.clinical.base.animic.app.AnimicTypeDTO;

@Path("/animic/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnimicTypeResource {
	@Inject
	AnimicTypeAppService appService;

	@GET
	public List<AnimicTypeDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public AnimicTypeDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public AnimicTypeDTO saveAnimicType(AnimicTypeDTO dto) {
		return appService.saveAnimicType( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteAnimicType( id );
	}
}
