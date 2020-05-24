package co.haruk.sms.sales.activities;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import co.haruk.core.StreamUtils;
import co.haruk.sms.sales.activities.app.ActivityRequestDTO;
import co.haruk.sms.sales.activities.app.CampaignDetailDTO;
import co.haruk.sms.sales.activities.app.TaskDetailDTO;
import co.haruk.sms.sales.activities.domain.model.view.ActivityReadView;
import co.haruk.sms.sales.activities.domain.model.view.CampaignDetailReadView;
import co.haruk.sms.sales.activities.domain.model.view.TaskDetailReadView;

public class ActivityReadViewMatcher extends BaseMatcher<String> {
	private final ActivityRequestDTO request;
	private final List<String> errors = new ArrayList<>();

	private ActivityReadViewMatcher(ActivityRequestDTO dto) {
		request = dto;
	}

	public static ActivityReadViewMatcher of(ActivityRequestDTO dto) {
		return new ActivityReadViewMatcher( dto );
	}

	@Override
	public boolean matches(Object actual) {
		final var readView = JsonbBuilder.create().fromJson( actual.toString(), ActivityReadView.class );
		boolean result = readView.purposeId.equalsIgnoreCase( request.purposeId )
				&& readView.buyerId.equalsIgnoreCase( request.buyerId ) && readView.activityDate.equals( request.activityDate );

		result = request.latitude != null ? result && readView.latitude.equals( request.latitude ) : result;
		result = request.longitude != null ? result && readView.longitude.equals( request.longitude ) : result;
		result = request.id != null ? result && request.id.equalsIgnoreCase( readView.id ) : result;
		result = request.supplierId != null ? result && request.supplierId.equalsIgnoreCase( readView.supplierId )
				: result;
		result = request.comment != null ? result && request.comment.equalsIgnoreCase( readView.comments ) : result;

		addErrorIfFalse( result, "Basic data didn't match" );

		result = result && campaignsValidate( request.campaigns, readView.campaigns );
		addErrorIfFalse( result, "Campaigns data didn't match" );

		result = result && taskValidate( request.tasks, readView.tasks );
		addErrorIfFalse( result, "Tasks data didn't match" );

		return result;
	}

	private boolean campaignsValidate(List<CampaignDetailDTO> campaignsRequest,
			List<CampaignDetailReadView> campaignsReadView) {
		boolean result = true;
		if ( campaignsRequest != null ) {
			result = result && campaignsRequest.size() == campaignsReadView.size();
			for ( CampaignDetailDTO campaign : campaignsRequest ) {
				CampaignDetailReadView campaignRDV = StreamUtils
						.find( campaignsReadView, it -> it.campaignId.equalsIgnoreCase( campaign.campaignId ) ).get();
				result = result && campaign.campaignId.equalsIgnoreCase( campaignRDV.campaignId );
				result = campaign.detail != null ? result && campaign.detail.equalsIgnoreCase( campaignRDV.detail )
						: result;
			}
		} else if ( campaignsReadView != null && !campaignsReadView.isEmpty() ) {
			result = false;
		}
		return result;
	}

	private boolean taskValidate(List<TaskDetailDTO> tasksRequest, List<TaskDetailReadView> tasksReadView) {
		boolean result = true;
		if ( tasksRequest != null ) {
			result = result && tasksRequest.size() == tasksReadView.size();
			for ( TaskDetailDTO taskDetail : tasksRequest ) {
				final var tempReadView = StreamUtils
						.find( tasksReadView, it -> it.taskId.equalsIgnoreCase( taskDetail.taskId ) ).get();
				result = result && taskDetail.taskId.equalsIgnoreCase( tempReadView.taskId );
				result = taskDetail.detail != null ? result && taskDetail.detail.equalsIgnoreCase( tempReadView.detail )
						: result;
			}
		} else if ( tasksReadView != null && !tasksReadView.isEmpty() ) {
			result = false;
		}
		return result;
	}

	private void addErrorIfFalse(boolean result, String message) {
		if ( !result ) {
			errors.add( message );
		}
	}

	@Override
	public void describeTo(Description description) {

	}
}
