package co.haruk.sms.market.measurement.attribute.domain.model.events;

import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;

public abstract class PickListEvent {
	private final PickListDetailId pickListId;
	private final TenantId tenantId;

	protected PickListEvent(PickListDetailId pickListId, TenantId tenantId) {
		this.pickListId = pickListId;
		this.tenantId = tenantId;
	}

	public PickListDetailId pickListId() {
		return pickListId;
	}

	public TenantId tenantId() {
		return tenantId;
	}
}
