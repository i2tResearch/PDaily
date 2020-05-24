package co.haruk.sms.business.structure.geography.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.business.structure.geography.app.GeographyAppService;
import co.haruk.sms.business.structure.geography.domain.model.ImporterResult;

/**
 * @author andres2508 on 2/12/19
 **/
@Path("/business/structure/geography")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GeographyResource {
	@Inject
	GeographyAppService appService;

	@POST
	@Path("/run-importers")
	public List<ImporterResult> runImporters() {
		return appService.runImporters();
	}
}
