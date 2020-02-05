package co.icesi.pdaily.sales.activities.app;

import java.util.List;

import co.icesi.pdaily.business.structure.address.domain.model.Geolocation;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.purpose.domain.model.PurposeId;

public final class ActivityRequestDTO {
	public String id;
	public String salesRepId;
	public String buyerId;
	public String supplierId;
	public String purposeId;
	public Long activityDate;
	public Float latitude;
	public Float longitude;
	public String comment;
	public List<CampaignDetailDTO> campaigns;
	public List<TaskDetailDTO> tasks;

	protected ActivityRequestDTO() {
	}

	private ActivityRequestDTO(
			String id,
			String salesRepId,
			String buyerId,
			String supplierId,
			String purposeId,
			Long activityDate,
			Float latitude,
			Float longitude,
			String comment,
			List<CampaignDetailDTO> campaigns,
			List<TaskDetailDTO> tasks) {
		this.id = id;
		this.salesRepId = salesRepId;
		this.buyerId = buyerId;
		this.supplierId = supplierId;
		this.purposeId = purposeId;
		this.activityDate = activityDate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.comment = comment;
		this.campaigns = campaigns;
		this.tasks = tasks;
	}

	public static ActivityRequestDTO of(
			String id,
			String salesRepId,
			String buyerId,
			String supplierId,
			String purposeId,
			Long date,
			Float latitude,
			Float longitude,
			String comment,
			List<CampaignDetailDTO> campaigns,
			List<TaskDetailDTO> tasks) {
		return new ActivityRequestDTO(
				id, salesRepId, buyerId, supplierId, purposeId, date,
				latitude, longitude, comment, campaigns, tasks
		);
	}

	public static ActivityRequestDTO of(
			String id,
			String salesRepId,
			String buyerId,
			String supplierId,
			String purposeId,
			Long date,
			Float latitude,
			Float longitude,
			String comment) {
		return new ActivityRequestDTO(
				id, salesRepId, buyerId, supplierId, purposeId, date,
				latitude, longitude, comment, null, null
		);
	}

	Activity toActivity() {
		Geolocation geolocation = latitude == null || longitude == null ? Geolocation.of( 0, 0 )
				: Geolocation.of( latitude, longitude );
		Activity activity = Activity.of(
				ActivityId.of( id ),
				SalesRepId.ofNotNull( salesRepId ),
				CustomerId.ofNotNull( buyerId ),
				CustomerId.of( supplierId ),
				PurposeId.of( purposeId ),
				UTCDateTime.of( activityDate ),
				geolocation,
				Reference.of( comment )
		);

		addCampaignDetailsToActivity( activity );
		addTaskDetailsToActivity( activity );

		return activity;
	}

	private void addCampaignDetailsToActivity(Activity activity) {
		if ( campaigns != null ) {
			for ( CampaignDetailDTO campaign : campaigns ) {
				activity.addCampaignDetail( campaign.toCampaignDetail( activity ) );
			}
		}
	}

	private void addTaskDetailsToActivity(Activity activity) {
		if ( tasks != null ) {
			for ( TaskDetailDTO task : tasks ) {
				activity.addTaskDetail( task.toTaskDetail( activity ) );
			}
		}
	}
}
