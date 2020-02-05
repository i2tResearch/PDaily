package co.icesi.pdaily.business.structure.geography.app.rest;

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

import co.icesi.pdaily.business.structure.geography.app.GeographyAppService;
import co.icesi.pdaily.business.structure.geography.app.StateDTO;

/**
 * @author cristhiank on 5/12/19
 **/
@Path("/business/structure/geography/state")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StateResource {
	@Inject
	GeographyAppService appService;

	@GET
	@Path("/for-country/{countryId}")
	public List<StateDTO> findForCountry(@PathParam("countryId") String countryId) {
		return appService.findStatesForCountry( countryId );
	}

	@POST
	public StateDTO saveState(StateDTO dto) {
		return appService.saveState( dto );
	}

	@GET
	@Path("/{id}")
	public StateDTO findById(@PathParam("id") String id) {
		return appService.findStateById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteStateById( id );
	}
}
