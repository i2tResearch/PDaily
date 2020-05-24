package co.haruk.sms.sales.activities;

import static co.haruk.testing.SMSTesting.authWithSalesRep;
import static co.haruk.testing.SMSTesting.nowMillis;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.customer.CustomerTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.sms.sales.activities.app.ActivityRequestDTO;
import co.haruk.sms.sales.activities.app.CampaignDetailDTO;
import co.haruk.sms.sales.activities.app.TaskDetailDTO;
import co.haruk.sms.sales.activities.marketing.MarketingCampaignTesting;
import co.haruk.sms.sales.activities.purpose.PurposeTesting;
import co.haruk.sms.sales.activities.task.TaskTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Activity tests")
public class ActivityResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACTIVITIES );
	}

	@Test
	@DisplayName("Filter activities for current sales rep")
	void findAllForSalesRepUser() {
		authWithSalesRep( given().contentType( MediaType.APPLICATION_JSON ) )
				.get( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 1 )
				);
	}

	@Test
	@DisplayName("Filter activities for normal users")
	void findAllForNormalUser() {
		given().get( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 1 )
				);
	}

	@Test
	@DisplayName("Finds all activities")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/for-sales-rep/{0}", SalesRepTesting.SALES_REP_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds activity by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( ActivityTesting.ACTIVITY_ID ),
						"buyerId", equalToIgnoringCase( CustomerTesting.CUSTOMER_BUYER_ID ),
						"comments", notNullValue(),
						"purposeId", notNullValue(),
						"supplierId", equalToIgnoringCase( CustomerTesting.CUSTOMER_SUPPLIER_ID ),
						"tasks.size", greaterThan( 0 ),
						"campaigns.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Save full activity")
	void saveActivity() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				null, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_ID, nowMillis(),
				0F, 0F, "Testing"
		);
		CampaignDetailDTO campaign = CampaignDetailDTO
				.of( null, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID, "Testing Campaing" );
		TaskDetailDTO task = TaskDetailDTO.of( null, TaskTesting.TASK_ID, "Testing task" );
		dto.campaigns = List.of( campaign );
		dto.tasks = List.of( task );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body( ActivityReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Save minimal activity")
	void saveMinimalActivity() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				null, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, null,
				PurposeTesting.PURPOSE_ID, nowMillis(),
				null, null, null
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body( ActivityReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Update activity")
	void AupdateActivity() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				ActivityTesting.ACTIVITY_TO_UPDATE, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_TO_UPDATE, nowMillis(),
				10F, 10F, "Testing update"
		);
		CampaignDetailDTO campaign = CampaignDetailDTO
				.of(
						ActivityTesting.CAMPAIGN_DETAIL_TO_UPDATE, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID,
						"Testing update Campaing"
				);
		TaskDetailDTO task = TaskDetailDTO
				.of( ActivityTesting.TASK_DETAIL_TO_UPDATE, TaskTesting.TASK_ID, "Testing update task" );
		dto.campaigns = List.of( campaign );
		dto.tasks = List.of( task );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body( ActivityReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Add details to activity")
	void removeDetails() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				ActivityTesting.ACTIVITY_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_TO_UPDATE, nowMillis(),
				10F, 10F, "Testing update"
		);
		CampaignDetailDTO campaignA = CampaignDetailDTO
				.of( ActivityTesting.CAMPAIGN_DETAIL, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID, "Testing update Campaing" );
		CampaignDetailDTO campaignB = CampaignDetailDTO
				.of( null, MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_UPDATE, "Testing add Campaing" );
		dto.campaigns = List.of( campaignA, campaignB );

		TaskDetailDTO taskA = TaskDetailDTO.of( ActivityTesting.TASK_DETAIL, TaskTesting.TASK_ID, "Testing update task" );
		TaskDetailDTO taskB = TaskDetailDTO.of( null, TaskTesting.TASK_TO_UPDATE, "Testing add task" );
		dto.tasks = List.of( taskA, taskB );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body( ActivityReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Delete details in activity")
	void deleteDetailsActivity() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				ActivityTesting.ACTIVITY_TO_DELETE_DETAILS, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_TO_UPDATE, nowMillis(),
				10F, 10F, "Testing delete"
		);
		dto.campaigns = List.of();
		dto.tasks = List.of();
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 200 )
				.body( ActivityReadViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Failures to add repeated campaign with different id")
	void failuresAddCampaignRepeat() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				ActivityTesting.ACTIVITY_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_TO_UPDATE, nowMillis(),
				10F, 10F, "Testing update"
		);
		CampaignDetailDTO campaignA = CampaignDetailDTO
				.of( ActivityTesting.CAMPAIGN_DETAIL, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID, "Testing update Campaing" );
		CampaignDetailDTO campaignB = CampaignDetailDTO
				.of( null, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID, "Testing error" );
		dto.campaigns = List.of( campaignA, campaignB );

		TaskDetailDTO taskA = TaskDetailDTO.of( ActivityTesting.TASK_DETAIL, TaskTesting.TASK_ID, "Testing update task" );
		dto.tasks = List.of( taskA );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Failures to add repeated task with different id")
	void failuresAddTaskRepeat() {
		ActivityRequestDTO dto = ActivityRequestDTO.of(
				ActivityTesting.ACTIVITY_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				PurposeTesting.PURPOSE_TO_UPDATE, nowMillis(),
				10F, 10F, "Testing update"
		);
		CampaignDetailDTO campaignA = CampaignDetailDTO
				.of( ActivityTesting.CAMPAIGN_DETAIL, MarketingCampaignTesting.MARKETING_CAMPAIGN_ID, "Testing update Campaing" );
		dto.campaigns = List.of( campaignA );

		TaskDetailDTO taskA = TaskDetailDTO.of( ActivityTesting.TASK_DETAIL, TaskTesting.TASK_ID, "Testing update task" );
		TaskDetailDTO taskB = TaskDetailDTO.of( null, TaskTesting.TASK_ID, "Testing error" );
		dto.tasks = List.of( taskA, taskB );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Delete activity")
	void deleteActivity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/{0}", ActivityTesting.ACTIVITY_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

}
