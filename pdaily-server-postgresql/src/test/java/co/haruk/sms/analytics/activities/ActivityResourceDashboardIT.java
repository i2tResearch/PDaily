package co.haruk.sms.analytics.activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.businessunit.BusinessUnitTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Activity dashboard tests")
public class ActivityResourceDashboardIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACTIVITY_DASHBOARD );
	}

	@Test
	@DisplayName("activities purpose count with date")
	void purposeStatisticsDate() {
		given().get(
				"reports/activity/purpose/count/{0}/{1}", 2134800000L,
				UTCDateTime.now().dateAsLong()
		)
				.then()
				.statusCode( 200 )
				.body(
						"label", hasItems( "EXISTENT", "PURPOSE_FOR_ACTIVITY" ),
						"counter", hasItems( 7, 3 )
				);
	}

	@Test
	@DisplayName("activities purpose count with date and sales rep")
	void purposeStatisticsDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/purpose/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"label", hasItems( "EXISTENT" ),
						"counter", hasItems( 4 )
				);
	}

	@Test
	@DisplayName("activities purpose count with date and business unit filter")
	void purposeStatisticsDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.get(
						"reports/activity/purpose/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"label", hasItems( "EXISTENT", "PURPOSE_FOR_ACTIVITY" ),
						"counter", hasItems( 5, 2 )
				);
	}

	@Test
	@DisplayName("activities purpose count with date and business unit filter and Sales Rep")
	void purposeStatisticsDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/purpose/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"label", hasItems( "EXISTENT" ),
						"counter", hasItems( 4 )
				);
	}

	@Test
	@DisplayName("percent effective activities with date")
	void percentEffectiveActivitiesDate() {
		given().get(
				"reports/activity/effective/{0}/{1}", 2134800000L,
				UTCDateTime.now().dateAsLong()
		)
				.then()
				.statusCode( 200 )
				.body(
						"percent", equalTo( 40.0F )
				);
	}

	@Test
	@DisplayName("percent effective activities with date and salesRep")
	void percentEffectiveActivitesDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/effective/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"percent", equalTo( 50.0F )
				);
	}

	@Test
	@DisplayName("percent effective activities with date and businessId")
	void percentEffectiveActivitesDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.get(
						"reports/activity/effective/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"percent", equalTo( 42.857142857142854F )
				);
	}

	@Test
	@DisplayName("percent effective activities with date and business, salesRep")
	void percentEffectiveActivitesDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/effective/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"percent", equalTo( 50.0F )
				);
	}

	@Test
	@DisplayName("count activities with date")
	void countActivitiesDate() {
		given().get(
				"reports/activity/count/{0}/{1}", 2134800000L,
				UTCDateTime.now().dateAsLong()
		)
				.then()
				.statusCode( 200 )
				.body(
						"visits", equalTo( 10 )
				);
	}

	@Test
	@DisplayName("count activities with date and salesRep")
	void countActivitiesDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"visits", equalTo( 4 )
				);
	}

	@Test
	@DisplayName("count activities with date and businessId")
	void countActivitiesDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.get(
						"reports/activity/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"visits", equalTo( 7 )
				);
	}

	@Test
	@DisplayName("count activities with date and business, salesRep")
	void countActivitiesDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"visits", equalTo( 4 )
				);
	}

	@Test
	@DisplayName("customers visited with date")
	void customerVisitedDate() {
		given()
				.get(
						"reports/activity/customer/visited/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"count", equalTo( 3 )
				);
	}

	@Test
	@DisplayName("customers visited with date and salesRep")
	void customerVisitedDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/customer/visited/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"count", equalTo( 2 )
				);
	}

	@Test
	@DisplayName("customers visited with date and businessId")
	void customerVisitedDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.get(
						"reports/activity/customer/visited/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"count", equalTo( 2 )
				);
	}

	@Test
	@DisplayName("customers visited with date and business, salesRep")
	void customerVisitedDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/customer/visited/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"count", equalTo( 2 )
				);
	}

	@Test
	@DisplayName("activities task count with date")
	void taskStatisticsDate() {
		given()
				.get(
						"reports/activity/task/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"task", hasItems( "EXISTENT", "TASK_FOR_DETAIL" ),
						"count", hasItems( 1, 3 )
				);
	}

	@Test
	@DisplayName("activities task count with date and sales rep")
	void taskStatisticsDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/task/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"task", hasItems( "EXISTENT" ),
						"count", hasItems( 2 )
				);
	}

	@Test
	@DisplayName("activities task count with date and business unit filter")
	void taskStatisticsDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )

				.get(
						"reports/activity/task/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"task", hasItems( "EXISTENT", "TASK_FOR_DETAIL" ),
						"count", hasItems( 1, 2 )
				);
	}

	@Test
	@DisplayName("activities task count with date and business unit filter and Sales Rep")
	void taskStatisticsDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/task/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"task", hasItems( "EXISTENT" ),
						"count", hasItems( 2 )
				);
	}

	@Test
	@DisplayName("activities count with date")
	void activitycountDate() {
		given()
				.get(
						"reports/activity/count/by-date/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems(
								"05-01-2000", "06-01-2000", "07-01-2000", "08-01-2000",
								"09-01-2000", "15-01-2000", "16-01-2000", "21-01-2000", "25-01-2000"
						),
						"count", hasItems( 1, 2 )
				);
	}

	@Test
	@DisplayName("activities count with date and sales rep")
	void activitycountDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/count/by-date/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "06-01-2000", "08-01-2000", "15-01-2000", "21-01-2000" ),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("activities count with date and business unit filter")
	void activitycountDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )

				.get(
						"reports/activity/count/by-date/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "06-01-2000", "07-01-2000", "08-01-2000", "15-01-2000", "16-01-2000", "21-01-2000" ),
						"count", hasItems( 1, 2 )
				);
	}

	@Test
	@DisplayName("activities count with date and business unit filter and Sales Rep")
	void activitycountDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/count/by-date/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "06-01-2000", "08-01-2000", "15-01-2000", "21-01-2000" ),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("effective activities count with date")
	void effectiveActivitycountDate() {
		given()
				.get(
						"reports/activity/effective/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems(
								"08-01-2000", "15-01-2000", "16-01-2000", "25-01-2000"
						),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("effective activities count with date and sales rep")
	void effectiveActivitycountDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/effective/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "08-01-2000", "15-01-2000" ),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("effective activities count with date and business unit filter")
	void effectiveActivitycountDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )

				.get(
						"reports/activity/effective/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "08-01-2000", "15-01-2000", "16-01-2000" ),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("effective activities count with date and business unit filter and Sales Rep")
	void effectiveActivitycountDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/effective/count/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"date", hasItems( "08-01-2000", "15-01-2000" ),
						"count", hasItems( 1 )
				);
	}

	@Test
	@DisplayName("activity list with date")
	void activityListByDate() {
		given()
				.get(
						"reports/activity/list/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 11 ),
						"date", notNullValue(),
						"buyer", hasItems( "BUYER_C", "BUYER_B", "BUYER" ),
						"supplier", hasItems( "SUPPLIER" ),
						"sales_rep", hasItems( "SALES_REP_USER SALES_REP_USER", "SALES_REP_USER_DASH SALES_REP_USER_DASH" ),
						"purpose", hasItems( "EXISTENT", "PURPOSE_FOR_ACTIVITY" ),
						"marketing_campaign", hasItems( "EXISTENT", null ),
						"task", hasItems( "EXISTENT", null )

				);
	}

	@Test
	@DisplayName("activity list with date and sales rep")
	void activityListByDateSalesRep() {
		given().queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )

				.get(
						"reports/activity/list/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 4 ),
						"date", notNullValue(),
						"buyer", hasItems( "BUYER_B", "BUYER" ),
						"supplier", hasItems( "SUPPLIER" ),
						"sales_rep", hasItems( "SALES_REP_USER_DASH SALES_REP_USER_DASH" ),
						"purpose", hasItems( "EXISTENT" ),
						"marketing_campaign", hasItems( "EXISTENT", null ),
						"task", hasItems( "EXISTENT", null )

				);
	}

	@Test
	@DisplayName("activity list with date and business unit filter")
	void activityListByDateBusiness() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )

				.get(
						"reports/activity/list/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 8 ),
						"date", notNullValue(),
						"buyer", hasItems( "BUYER_B", "BUYER" ),
						"supplier", hasItems( "SUPPLIER" ),
						"sales_rep", hasItems( "SALES_REP_USER SALES_REP_USER", "SALES_REP_USER_DASH SALES_REP_USER_DASH" ),
						"purpose", hasItems( "EXISTENT", "PURPOSE_FOR_ACTIVITY" ),
						"marketing_campaign", hasItems( "EXISTENT", null ),
						"task", hasItems( "EXISTENT", null )

				);
	}

	@Test
	@DisplayName("activity list with date and business unit filter and Sales Rep")
	void activityListByDateBusinessSalesRep() {
		given().queryParam( "businessId", BusinessUnitTesting.BUSINESS_UNIT_ID )
				.queryParam( "salesRep", SalesRepTesting.SALES_REP_TO_DASHBOARD )
				.get(
						"reports/activity/list/{0}/{1}", 2134800000L,
						UTCDateTime.now().dateAsLong()
				)
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 4 ),
						"date", notNullValue(),
						"buyer", hasItems( "BUYER_B", "BUYER" ),
						"supplier", hasItems( "SUPPLIER" ),
						"sales_rep", hasItems( "SALES_REP_USER_DASH SALES_REP_USER_DASH" ),
						"purpose", hasItems( "EXISTENT" ),
						"marketing_campaign", hasItems( "EXISTENT", null ),
						"task", hasItems( "EXISTENT", null )

				);
	}

}
