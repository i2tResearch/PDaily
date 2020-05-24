package co.haruk.sms.subscription.license.app.rest;

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

import co.haruk.sms.subscription.license.app.LicenseAppService;
import co.haruk.sms.subscription.license.app.LicenseDTO;

/**
 * @author cristhiank on 15/11/19
 **/
@Path("/subscription/license")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LicenseResource {
	@Inject
	LicenseAppService appService;

	@GET
	public List<LicenseDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public LicenseDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteEntity(@PathParam("id") String id) {
		appService.delete( id );
	}

	@POST
	public LicenseDTO saveEntity(LicenseDTO dto) {
		return appService.saveLicense( dto );
	}
}
