package co.haruk.sms.market.measurement.attribute.app.rest;

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

import co.haruk.sms.market.measurement.attribute.app.MarketAttributeAppService;
import co.haruk.sms.market.measurement.attribute.app.MarketAttributeDTO;
import co.haruk.sms.market.measurement.attribute.domain.model.view.MarketAttributeReadView;

@Path("/market/attribute")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarketAttributeResource {
	@Inject
	MarketAttributeAppService appService;

	@POST
	@Path("/group")
	public List<MarketAttributeReadView> saveMultiple(List<MarketAttributeDTO> listDTO) {
		return appService.saveMultipleMarketAttributes( listDTO );
	}

	@POST
	public MarketAttributeReadView saveMarketAttribute(MarketAttributeDTO dto) {
		return appService.saveMarketAttribute( dto );
	}

	@GET
	@Path("/for-business-unit/{businessId}")
	public List<MarketAttributeReadView> findByBusinessUnit(@PathParam("businessId") String businessId) {
		return appService.findForBusiness( businessId );
	}

	@GET
	@Path("/{id}")
	public MarketAttributeReadView findOrFail(@PathParam("id") String id) {
		return appService.findOrFail( id );
	}

	@DELETE
	@Path("/{id}")
	public void deleteMarketAttribute(@PathParam("id") String id) {
		appService.deleteMarketAttribute( id );
	}
}
