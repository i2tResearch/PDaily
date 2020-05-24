package co.haruk.sms.analytics.market.attribute.app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.analytics.market.attribute.app.AttributeDashboardAppService;

@Path("/reports/market/attributes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AttributeDashboardResource {

	@Inject
	AttributeDashboardAppService appService;

	@POST
	@Path("/by-contact")
	public JsonObject marketAttributesForContacts(List<String> attributes) {
		return appService.marketAttributesForContacts( attributes );
	}

	@POST
	@Path("/by-customer")
	public JsonObject marketAttributesForCustomers(List<String> attributes) {
		return appService.marketAttributesForCustomers( attributes );
	}
}
