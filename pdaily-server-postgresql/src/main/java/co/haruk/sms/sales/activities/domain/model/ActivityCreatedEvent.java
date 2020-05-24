package co.haruk.sms.sales.activities.domain.model;

import co.haruk.sms.common.model.tenancy.TenantId;

/**
 * @author andres2508 on 20/2/20
 **/
public final class ActivityCreatedEvent extends ActivityEvent {

	private ActivityCreatedEvent(ActivityId activityId, TenantId tenantId) {
		super( activityId, tenantId );
	}

	public static ActivityCreatedEvent of(ActivityId activityId, TenantId tenantId) {
		return new ActivityCreatedEvent( activityId, tenantId );
	}
}
