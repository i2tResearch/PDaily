package co.icesi.pdaily.analytics.patients.domain.model;

import co.icesi.pdaily.analytics.domain.model.DashboardQuery;

public enum PatientDashboardQueries {
	EVENTS_CONSULT( DashboardQuery.of( "EVENTS_CONSULT", "analytics/patients/events_consult_by_date.sql" ) ),
	ANIMIC_CONSULT( DashboardQuery.of( "ANIMIC_CONSULT", "analytics/patients/patient_animic_by_date.sql" ) );

	private final DashboardQuery query;

	public DashboardQuery query() {
		return query;
	}

	PatientDashboardQueries(DashboardQuery query) {
		this.query = query;
	}
}
