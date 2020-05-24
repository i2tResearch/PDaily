package co.haruk.sms.analytics.app.rest;

import co.haruk.sms.analytics.domain.model.DashboardQuery;

/**
 * TODO: Eliminar en version 5. Deben venir del front
 * 
 * @author cristhiank on 23/5/20
 **/
public enum ActivityDashboardQueries {
	PURPOSE_COUNT( DashboardQuery.of( "PURPOSE_COUNT", "analytics/activities/purpose_count.sql" ) ),
	ACTIVITY_COUNT( DashboardQuery.of( "ACTIVITY_COUNT", "analytics/activities/activity_count.sql" ) ),
	VISIT_CUSTOMER_COUNT( DashboardQuery.of( "VISIT_CUSTOMER_COUNT", "analytics/activities/visit_customer_count.sql" ) ),
	ACTIVITY_LIST( DashboardQuery.of( "ACTIVITY_LIST", "analytics/activities/activity_list.sql" ) ),
	ACTIVITY_TASK_COUNT( DashboardQuery.of( "ACTIVITY_TASK_COUNT", "analytics/activities/activity_task_count.sql" ) ),
	ACTIVITY_COUNT_BY_DATE(
			DashboardQuery.of(
					"ACTIVITY_COUNT_BY_DATE",
					"analytics/activities/activity_count_by_date.sql"
			)
	),
	ACTIVITY_EFFECTIVE_BY_DATE(
			DashboardQuery.of(
					"ACTIVITY_EFFECTIVE_BY_DATE",
					"analytics/activities/activity_effective_by_date.sql"
			)
	),
	ACTIVITY_EFFECTIVE( DashboardQuery.of( "ACTIVITY_EFFECTIVE", "analytics/activities/activity_effective.sql" ) );

	private final DashboardQuery query;

	public DashboardQuery query() {
		return query;
	}

	ActivityDashboardQueries(DashboardQuery query) {
		this.query = query;
	}
}
