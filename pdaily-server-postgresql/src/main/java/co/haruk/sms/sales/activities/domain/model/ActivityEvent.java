package co.haruk.sms.sales.activities.domain.model;

import co.haruk.sms.common.model.tenancy.TenantId;

/**
 * @author cristhiank on 20/2/20
 **/
public abstract class ActivityEvent {
	private final ActivityId activityId;
	private final TenantId tenantId;

	protected ActivityEvent(ActivityId activityId, TenantId tenantId) {
		this.activityId = activityId;
		this.tenantId = tenantId;
	}

	public ActivityId activityId() {
		return activityId;
	}

	public TenantId tenantId() {
		return tenantId;
	}
}
