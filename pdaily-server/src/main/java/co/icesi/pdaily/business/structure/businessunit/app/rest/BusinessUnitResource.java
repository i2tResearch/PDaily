package co.icesi.pdaily.business.structure.businessunit.app.rest;

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

import co.icesi.pdaily.business.structure.businessunit.app.BusinessUnitAppService;
import co.icesi.pdaily.business.structure.businessunit.app.BusinessUnitDTO;

@Path("/business/business-unit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BusinessUnitResource {
	@Inject
	BusinessUnitAppService appService;

	@POST
	public BusinessUnitDTO saveBusinessUnit(BusinessUnitDTO dto) {
		return appService.saveBusinessUnit( dto );
	}

	@GET
	@Path("/{id}")
	public BusinessUnitDTO getBusinessUnit(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@GET
	public List<BusinessUnitDTO> findAll() {
		return appService.findAll();
	}

	@DELETE
	@Path("/{id}")
	public void deleteBusinessUnit(@PathParam("id") String id) {
		appService.deleteBusinessUnit( id );
	}
}
