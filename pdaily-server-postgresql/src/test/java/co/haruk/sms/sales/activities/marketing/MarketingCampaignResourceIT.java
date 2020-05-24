package co.haruk.sms.sales.activities.marketing;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.sales.activities.marketing.app.MarketingCampaignDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Activities marketing campaign tests")
public class MarketingCampaignResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACTIVITIES );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id correctly")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/marketing-campaign/{0}", MarketingCampaignTesting.MARKETING_CAMPAIGN_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( MarketingCampaignTesting.MARKETING_CAMPAIGN_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new marketing campaign")
	void saveMarketingCampaign() {
		final var dto = MarketingCampaignDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsToSaveIfDuplicatedName() {
		final var dto = MarketingCampaignDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = MarketingCampaignDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an marketing campaign")
	void updatesMarketingCampaign() {
		final var dto = MarketingCampaignDTO
				.of( MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = MarketingCampaignDTO.of( MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/activities/marketing-campaign" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an marketing campaign")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/activities/marketing-campaign/{0}", MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/activities/marketing-campaign/{0}", MarketingCampaignTesting.MARKETING_CAMPAIGN_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if campaign has activities assigned")
	void failsIfDeleteWithActivities() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/activities/marketing-campaign/{0}", MarketingCampaignTesting.MARKETING_CAMPAIGN_FOR_DETAIL )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene actividades asignadas" )
				);
	}
}
