package co.haruk.sms.sales.salesorder.domain.model;

import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.TenantId;

public class SalesOrderUpdateEvent extends SalesOrderEvent {

	private final UTCDateTime updateDate;

	private SalesOrderUpdateEvent(SalesOrderId salesOrderId, TenantId tenantId, UTCDateTime updateDate) {
		super( salesOrderId, tenantId );
		this.updateDate = updateDate;
	}

	public static SalesOrderUpdateEvent of(SalesOrderId salesOrderId, TenantId tenantId, UTCDateTime beforeUpdate) {
		return new SalesOrderUpdateEvent( salesOrderId, tenantId, beforeUpdate );
	}

	public UTCDateTime updateDate() {
		return updateDate;
	}
}
