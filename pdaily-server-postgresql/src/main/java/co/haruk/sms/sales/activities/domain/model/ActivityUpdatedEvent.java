package co.haruk.sms.sales.activities.domain.model;

import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.TenantId;

/**
 * @author andres2508 on 20/2/20
 **/
public final class ActivityUpdatedEvent extends ActivityEvent {

	private final UTCDateTime updateDate;

	private ActivityUpdatedEvent(ActivityId activityId, TenantId tenantId, UTCDateTime updateDate) {
		super( activityId, tenantId );
		this.updateDate = updateDate;
	}

	public static ActivityUpdatedEvent of(ActivityId activityId, TenantId tenantId, UTCDateTime updateDate) {
		return new ActivityUpdatedEvent( activityId, tenantId, updateDate );
	}

	public UTCDateTime updateDate() {
		return updateDate;
	}
}
