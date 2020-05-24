package co.haruk.sms.sales.salesorder.domain.model;

import co.haruk.sms.common.model.tenancy.TenantId;

public abstract class SalesOrderEvent {
	private final SalesOrderId salesOrderId;
	private final TenantId tenantId;

	protected SalesOrderEvent(SalesOrderId salesOrderId, TenantId tenantId) {
		this.salesOrderId = salesOrderId;
		this.tenantId = tenantId;
	}

	public SalesOrderId salesOrderId() {
		return salesOrderId;
	}

	public TenantId tenantId() {
		return tenantId;
	}
}
