package co.haruk.sms.analytics.market.attribute.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

import co.haruk.sms.analytics.market.attribute.domain.model.AttributeQueryManager;

@ApplicationScoped
public class AttributeDashboardAppService {
	@Inject
	AttributeQueryManager queryManager;

	public JsonObject marketAttributesForCustomers(List<String> attributes) {
		return queryManager.getCustomersMarketAttributes( attributes );
	}

	public JsonObject marketAttributesForContacts(List<String> attributes) {
		return queryManager.getContactsMarketAttributes( attributes );
	}

}
