package co.haruk.sms.analytics.app.rest;

import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.haruk.sms.analytics.domain.model.DashboardQuery;
import co.haruk.sms.analytics.domain.model.DashboardQueryParams;
import co.haruk.sms.common.model.UTCDateTime;

@Path("/reports/activity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityDashboardResource {

	public Map<String, Object> buildParams(Long startDate, Long endDate, String salesRepId, String businessId) {
		return DashboardQueryParams.of( "salesRepId", salesRepId )
				.and( "businessId", businessId )
				.and( "endDate", UTCDateTime.of( endDate ).toString() )
				.and( "startDate", UTCDateTime.of( startDate ).toString() ).build();
	}

	@GET
	@Path("/purpose/count/{startDate}/{endDate}")
	public JsonArray purposeCount(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.PURPOSE_COUNT.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAll( params );
	}

	@GET
	@Path("/effective/{startDate}/{endDate}")
	public JsonObject percentEffectiveActivities(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_EFFECTIVE.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAsSingleRow( params );
	}

	@GET
	@Path("/count/{startDate}/{endDate}")
	public JsonObject countActivitiesBy(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_COUNT.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAsSingleRow( params );
	}

	@GET
	@Path("/count/by-date/{startDate}/{endDate}")
	public JsonArray activityCountByDate(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_COUNT_BY_DATE.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAll( params );
	}

	@GET
	@Path("/task/count/{startDate}/{endDate}")
	public JsonArray taskCount(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_TASK_COUNT.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAll( params );
	}

	@GET
	@Path("/customer/visited/{startDate}/{endDate}")
	public JsonObject countCustomerVisitedBy(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.VISIT_CUSTOMER_COUNT.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAsSingleRow( params );
	}

	@GET
	@Path("/effective/count/{startDate}/{endDate}")
	public JsonArray effectiveActivityCountByDate(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_EFFECTIVE_BY_DATE.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAll( params );
	}

	@GET
	@Path("/list/{startDate}/{endDate}")
	public JsonArray activityListBy(@PathParam("startDate") Long startDate,
			@PathParam("endDate") Long endDate,
			@QueryParam("salesRep") String salesRepId,
			@QueryParam("businessId") String businessId) {
		final DashboardQuery query = ActivityDashboardQueries.ACTIVITY_LIST.query();
		final Map<String, Object> params = buildParams( startDate, endDate, salesRepId, businessId );
		return query.readAll( params );
	}
}
