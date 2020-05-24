package co.haruk.sms.market.measurement.attribute.domain.model.events;

import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;

public class MarketAttributeDeleteEvent extends MarketAttributeEvent {

	private MarketAttributeDeleteEvent(MarketAttributeId attributeId, TenantId tenantId) {
		super( attributeId, tenantId );
	}

	public static MarketAttributeDeleteEvent of(MarketAttributeId attributeId, TenantId tenantId) {
		return new MarketAttributeDeleteEvent( attributeId, tenantId );
	}
}
