package co.icesi.pdaily.sales.activities.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.domain.model.details.CampaignDetail;
import co.icesi.pdaily.sales.activities.domain.model.details.TaskDetail;
import co.icesi.pdaily.sales.activities.domain.model.view.ActivityReadView;
import co.icesi.pdaily.sales.activities.domain.model.view.CampaignDetailReadView;
import co.icesi.pdaily.sales.activities.domain.model.view.TaskDetailReadView;

@ApplicationScoped
public class ActivityRepository extends JPARepository<Activity> {

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
}
