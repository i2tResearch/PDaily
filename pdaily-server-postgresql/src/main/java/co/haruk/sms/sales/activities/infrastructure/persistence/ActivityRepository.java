package co.haruk.sms.sales.activities.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.infrastructure.jpa.SMSJPARepository;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.ActivityId;
import co.haruk.sms.sales.activities.domain.model.details.CampaignDetail;
import co.haruk.sms.sales.activities.domain.model.details.TaskDetail;
import co.haruk.sms.sales.activities.domain.model.view.ActivityReadView;
import co.haruk.sms.sales.activities.domain.model.view.CampaignDetailReadView;
import co.haruk.sms.sales.activities.domain.model.view.TaskDetailReadView;
import co.haruk.sms.sales.activities.marketing.domain.model.MarketingCampaignId;
import co.haruk.sms.sales.activities.purpose.domain.model.PurposeId;
import co.haruk.sms.sales.activities.task.domain.model.TaskId;

@ApplicationScoped
public class ActivityRepository extends SMSJPARepository<Activity> {

	@PostConstruct
	void initialize() {
		ActivityQueryManager.registerQueries( currentEM() );
	}

	public List<CampaignDetailReadView> findCampaignDetailsAsReadView(ActivityId id) {
		requireNonNull( id );
		return findOtherWithNamedQuery(
				CampaignDetailReadView.class,
				CampaignDetail.findCampaignDetailsAsReadView,
				QueryParameter.with( "activity", id ).parameters()
		);
	}

	public List<TaskDetailReadView> findTaskDetailsAsReadView(ActivityId id) {
		requireNonNull( id );
		return findOtherWithNamedQuery(
				TaskDetailReadView.class,
				TaskDetail.findTaskDetailsAsReadView,
				QueryParameter.with( "activity", id ).parameters()
		);
	}

	public List<ActivityReadView> findActivitiesBySalesRepAsReadView(SalesRepId salesRepId) {
		requireNonNull( salesRepId );
		return findOtherWithNamedQuery(
				ActivityReadView.class,
				Activity.findBySalesRepAsReadView,
				QueryParameter.with( "salesRepId", salesRepId ).parameters()
		);
	}

	public boolean existsForPurpose(PurposeId purposeId) {
		requireNonNull( purposeId );
		final var count = executeAggregateQuery(
				Activity.countByPurpose,
				QueryParameter.with( "purpose", purposeId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsForCampaign(MarketingCampaignId campaignId) {
		requireNonNull( campaignId );
		final var count = executeAggregateQuery(
				CampaignDetail.countByCampaign,
				QueryParameter.with( "campaign", campaignId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsForTask(TaskId task) {
		requireNonNull( task );
		final var count = executeAggregateQuery(
				TaskDetail.countByTask,
				QueryParameter.with( "task", task ).parameters()
		).intValue();
		return count > 0;
	}

	public List<Activity> findActivitiesByDateRange(CustomerId customerId, SalesRepId salesRepId, UTCDateTime startDate,
			UTCDateTime endDate) {
		return findWithNamedQuery(
				Activity.findActivitiesByDateRange,
				QueryParameter.with( "customerId", customerId )
						.and( "salesRepId", salesRepId )
						.and( "endDate", endDate.date() )
						.and( "startDate", startDate.date() ).parameters()
		);
	}

	public List<ActivityId> findAllIDs() {
		return findOtherWithNamedQuery( ActivityId.class, ActivityQueryManager.queryForActivitiesIDs() );
	}
}
