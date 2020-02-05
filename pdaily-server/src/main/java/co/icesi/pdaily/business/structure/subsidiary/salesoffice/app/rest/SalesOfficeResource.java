package co.icesi.pdaily.business.structure.subsidiary.salesoffice.app.rest;

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

import co.icesi.pdaily.business.structure.subsidiary.salesoffice.app.SalesOfficeAppService;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.app.SalesOfficeDTO;

/**
 * @author cristhiank on 21/11/19
 **/
@Path("/business/sales-office")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalesOfficeResource {

	@Inject
	SalesOfficeAppService appService;

	@GET
	public List<SalesOfficeDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-subsidiary/{subId}")
	public List<SalesOfficeDTO> findForSubsidiary(@PathParam("subId") String subsidiaryId) {
		return appService.findForSubsidiary( subsidiaryId );
	}

	@GET
	@Path("/{id}")
	public SalesOfficeDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public SalesOfficeDTO saveEntity(SalesOfficeDTO dto) {
		return appService.saveSalesOffice( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteEntity(@PathParam("id") String id) {
		appService.deleteSalesOffice( id );
	}
}
