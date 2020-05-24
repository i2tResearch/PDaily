package co.haruk.sms.business.structure.subsidiary.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.business.structure.subsidiary.app.SubsidiaryAppService;
import co.haruk.sms.business.structure.subsidiary.app.SubsidiaryDTO;

/**
 * @author cristhiank on 19/11/19
 **/
@Path("/business/subsidiary")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubsidiaryResource {
	@Inject
	SubsidiaryAppService appService;

	@GET
	public List<SubsidiaryDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public SubsidiaryDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteEntity(@PathParam("id") String id) {
		appService.deleteById( id );
	}

	@POST
	public SubsidiaryDTO saveEntity(SubsidiaryDTO dto) {
		return appService.saveSubsidiary( dto );
	}
}
