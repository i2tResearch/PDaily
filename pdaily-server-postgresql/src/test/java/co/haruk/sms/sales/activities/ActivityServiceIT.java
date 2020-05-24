package co.haruk.sms.sales.activities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.sales.activities.domain.model.ActivityCreatedEvent;
import co.haruk.sms.sales.activities.domain.model.ActivityId;
import co.haruk.sms.sales.activities.domain.services.ActivityService;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.salesorder.SalesOrderTesting;
import co.haruk.sms.sales.salesorder.app.SalesOrderAppService;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@Tag("cdi")
@DisplayName("Activity Service Test")
public class ActivityServiceIT implements IDataSetDependent, ICDIContainerDependent {

	@Inject
	ActivityService service;
	@Inject
	ActivityRepository repository;
	@Inject
	SalesOrderAppService salesAppService;

	@BeforeAll
	public static void initialize() {
		HarukSession.setCurrentTenant( TenantId.of( AccountTesting.ACCOUNT_ID ) );
	}

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACTIVITIES );
	}

	@Test
	public void testTransaction(UserTransaction transaction) throws Exception {
		final var event = ActivityCreatedEvent.of(
				ActivityId.of( ActivityTesting.ACTIVITY_TO_CREATE_VALIDATION ),
				TenantId.of( AccountTesting.ACCOUNT_ID )
		);
		assertDoesNotThrow( () -> {
			final var found = repository.findOrFail( event.activityId() );
			found.setEffective( true );
			repository.update( found );
			transaction.commit();
		} );
	}

	@Test
	@DisplayName("Create activity validation")
	public void createActivity(UserTransaction transaction) throws Exception {
		final var activity = repository.findOrFail( ActivityId.of( ActivityTesting.ACTIVITY_TO_CREATE_VALIDATION ) );
		service.effectiveActivitiesBy( activity, activity.activityDate() );
		transaction.commit();

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_CREATE_VALIDATION )
				.then()
				.statusCode( 200 )
				.body( "isEffective", equalTo( true ) );
	}

	@Test
	@DisplayName("Update activity validation")
	public void updateActivity(UserTransaction transaction) throws Exception {
		final var activity = repository.findOrFail( ActivityId.of( ActivityTesting.ACTIVITY_TO_UPDATE_VALIDATION ) );
		service.effectiveActivitiesBy( activity, UTCDateTime.of( 883674000000L ) );
		service.effectiveActivitiesBy( activity, activity.activityDate() );
		transaction.commit();

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_CREATE_VALIDATION )
				.then()
				.statusCode( 200 )
				.body( "isEffective", equalTo( false ) );
	}

	@Test
	@DisplayName("Create sales order validation")
	public void createSalesOrder(UserTransaction transaction) throws Exception {
		final var order = salesAppService.findById( SalesOrderTesting.SALES_ORDER_TO_CREATE_VALIDATIONS );
		service.effectiveActivitiesBy( order, UTCDateTime.of( order.orderDate ) );
		transaction.commit();

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_CHANGE_EFFECTIVE )
				.then()
				.statusCode( 200 )
				.body( "isEffective", equalTo( true ) );
	}

	@Test
	@DisplayName("Update sales order validation")
	public void updateSalesOrder(UserTransaction transaction) throws Exception {
		final var order = salesAppService.findById( SalesOrderTesting.SALES_ORDER_TO_ACTIVITY_VALIDATE );
		service.effectiveActivitiesBy( order, UTCDateTime.of( 1136242800000L ) );
		service.effectiveActivitiesBy( order, UTCDateTime.of( order.orderDate ) );
		transaction.commit();

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_ORDER_ACT_EFFECTIVE )
				.then()
				.statusCode( 200 )
				.body( "isEffective", equalTo( true ) );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_ORDER_ACT_NOT_EFFECTIVE )
				.then()
				.statusCode( 200 )
				.body( "isEffective", equalTo( false ) );
	}

}
