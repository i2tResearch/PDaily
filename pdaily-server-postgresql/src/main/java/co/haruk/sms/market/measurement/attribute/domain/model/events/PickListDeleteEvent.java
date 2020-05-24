package co.haruk.sms.market.measurement.attribute.domain.model.events;

import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;

public class PickListDeleteEvent extends PickListEvent {

	private PickListDeleteEvent(PickListDetailId pickListId, TenantId tenantId) {
		super( pickListId, tenantId );
	}

	public static PickListDeleteEvent of(PickListDetailId pickListId, TenantId tenantId) {
		return new PickListDeleteEvent( pickListId, tenantId );
	}
}
