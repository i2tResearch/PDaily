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

import co.icesi.pdaily.business.structure.geography.app.CountryDTO;
import co.icesi.pdaily.business.structure.geography.app.GeographyAppService;

/**
 * @author andres2508 on 5/12/19
 **/
@Path("/business/structure/geography/country")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountryResource {
	@Inject
	GeographyAppService appService;

	@GET
	public List<CountryDTO> findAllCountries() {
		return appService.findAllCountries();
	}

	@POST
	public CountryDTO saveCountry(CountryDTO dto) {
		return appService.saveCountry( dto );
	}

	@GET
	@Path("/{id}")
	public CountryDTO findById(@PathParam("id") String id) {
		return appService.findCountryById( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteCountryById( id );
	}
}
