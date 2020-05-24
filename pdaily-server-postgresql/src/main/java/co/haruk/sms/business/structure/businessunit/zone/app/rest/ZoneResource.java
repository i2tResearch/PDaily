package co.haruk.sms.business.structure.businessunit.zone.app.rest;

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

import co.haruk.sms.business.structure.businessunit.zone.app.ZoneAppService;
import co.haruk.sms.business.structure.businessunit.zone.app.ZoneDTO;

/**
 * @author andres2508 on 24/11/19
 **/
@Path("/business/structure/zone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZoneResource {
	@Inject
	ZoneAppService appService;

	@GET
	public List<ZoneDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/for-business-unit/{unitId}")
	public List<ZoneDTO> findByBusinessUnit(@PathParam("unitId") String unitId) {
		return appService.findForBusinessUnit( unitId );
	}

	@GET
	@Path("/{id}")
	public ZoneDTO findById(@PathParam("id") String id) {
		return appService.findZoneById( id );
	}

	@POST
	public ZoneDTO saveEntity(ZoneDTO zone) {
		return appService.saveZone( zone );
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) {
		appService.deleteZone( id );
	}
}
