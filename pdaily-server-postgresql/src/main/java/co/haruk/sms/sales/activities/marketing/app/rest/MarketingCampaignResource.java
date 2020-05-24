package co.haruk.sms.sales.activities.marketing.app.rest;

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

import co.haruk.sms.sales.activities.marketing.app.MarketingCampaignAppService;
import co.haruk.sms.sales.activities.marketing.app.MarketingCampaignDTO;

@Path("/sales/activities/marketing-campaign")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarketingCampaignResource {
	@Inject
	MarketingCampaignAppService appService;

	@GET
	public List<MarketingCampaignDTO> findAll() {
		return appService.findAll();
	}

	@GET
	@Path("/{id}")
	public MarketingCampaignDTO findById(@PathParam("id") String id) {
		return appService.findById( id );
	}

	@POST
	public MarketingCampaignDTO saveMarketingCampaign(MarketingCampaignDTO dto) {
		return appService.saveMarketingCampaign( dto );
	}

	@DELETE
	@Path("/{id}")
	public void deleteById(@PathParam("id") String id) {
		appService.deleteMarketingCampaign( id );
	}
}
