package co.haruk.sms.analytics.patients.domain.model;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

import co.haruk.core.StreamUtils;
import co.haruk.sms.analytics.domain.model.DashboardQueryParams;
import co.haruk.sms.common.model.UTCDateTime;

@ApplicationScoped
public class PatientQueryManager {
	private final static int DAYS_OF_WEEK = 7;

	private Map<String, Object> buildParams(Long startDate, Long endDate, String patientId) {
		return DashboardQueryParams.of( "patientId", patientId )
				.and( "endDate", UTCDateTime.of( endDate ).toString() )
				.and( "startDate", UTCDateTime.of( startDate ).toString() ).build();
	}

	public JSONArray processQuery(Long startDate, Long endDate, String patientId) {
		final var params = buildParams( startDate, endDate, patientId );
		JSONArray result = new JSONArray();

		final var animicQuery = PatientDashboardQueries.ANIMIC_CONSULT.query()
				.readAllAsStringArray( params );
		final var eventsQuery = PatientDashboardQueries.EVENTS_CONSULT.query()
				.readAllAsStringArray( params );

		final var animicMap = StreamUtils.map( animicQuery, PatientAnimicDTO::of );
		final var eventMap = StreamUtils.map( eventsQuery, PatientEventDTO::of );

		final var animicGroup = StreamUtils.groupBy( animicMap, PatientAnimicDTO::day );
		final var eventGroup = StreamUtils.groupBy( eventMap, PatientEventDTO::day );

		for ( int i = 1; i <= DAYS_OF_WEEK; i++ ) {
			JSONObject builder = new JSONObject();
			builder.put( "dayOfWeek", i );

			final var moods = animicGroup.containsKey( i ) ? animicGroup.get( i ) : List.of();
			final var events = eventGroup.containsKey( i ) ? eventGroup.get( i ) : List.of();

			builder.put( "moods", moods );
			builder.put( "events", events );
			result.add( builder );
		}
		return result;
	}
}
