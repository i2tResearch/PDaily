package co.haruk.sms.market.measurement.attribute.domain.model.events;

import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;

public abstract class MarketAttributeEvent {
	private final MarketAttributeId attributeId;
	private final TenantId tenantId;

	protected MarketAttributeEvent(MarketAttributeId attributeId, TenantId tenantId) {
		this.attributeId = attributeId;
		this.tenantId = tenantId;
	}

	public MarketAttributeId attributeId() {
		return attributeId;
	}

	public TenantId tenantId() {
		return tenantId;
	}
}
