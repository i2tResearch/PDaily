package co.haruk.sms.business.structure.geography.app.rest;

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

import co.haruk.sms.business.structure.geography.app.CityDTO;
import co.haruk.sms.business.structure.geography.app.GeographyAppService;

/**
 * @author andres2508 on 5/12/19
 **/
@Path("/business/structure/geography/city")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityResource {
	@Inject
	GeographyAppService appService;

	@GET
	@Path("/for-state/{stateId}")
	public List<CityDTO> findForCountry(@PathParam("stateId") String stateId) {
		return appService.findCitiesForState( stateId );
	}

	@POST
	public CityDTO saveCity(CityDTO dto) {
		return appService.saveCity( dto );
	}

	@GET
	@Path("/{id}")
	public CityDTO findById(@PathParam("id") String id) {
		return appService.findCityById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteCityById( id );
	}
}
