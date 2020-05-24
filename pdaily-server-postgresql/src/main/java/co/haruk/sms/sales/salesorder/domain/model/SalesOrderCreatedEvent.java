package co.haruk.sms.sales.salesorder.domain.model;

import co.haruk.sms.common.model.tenancy.TenantId;

public class SalesOrderCreatedEvent extends SalesOrderEvent {

	private SalesOrderCreatedEvent(SalesOrderId salesOrderId, TenantId tenantId) {
		super( salesOrderId, tenantId );
	}

	public static SalesOrderCreatedEvent of(SalesOrderId salesOrderId, TenantId tenantId) {
		return new SalesOrderCreatedEvent( salesOrderId, tenantId );
	}
}
