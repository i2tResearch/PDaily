package co.haruk.sms.business.structure.geography;

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
import co.haruk.sms.business.structure.geography.app.StateDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author andres2508 on 5/12/19
 **/
@SMSTest
@DisplayName("State tests")
class StateResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.GEOGRAPHY );
	}

	@Test
	@DisplayName("Finds for country")
	void findForCountry() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/state/for-country/{0}", GeographyTesting.COUNTRY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Creates a new state")
	void saveState() {
		final var dto = StateDTO.of(
				null,
				GeographyTesting.COUNTRY_ID,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"countryId", equalToIgnoringCase( dto.countryId ),
						"code", equalToIgnoringCase( dto.code ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code")
	void failsIfDuplicatedCode() {
		final var dto = StateDTO.of(
				null,
				GeographyTesting.COUNTRY_ID,
				"TEST_EXISTENT",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated code ignoring case")
	void failsIfDuplicatedCodeIgnoringCase() {
		final var dto = StateDTO.of(
				null,
				GeographyTesting.COUNTRY_ID,
				" test_existent ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		final var dto = StateDTO.of(
				null,
				GeographyTesting.COUNTRY_ID,
				TestNamesGenerator.generateCode(),
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoreCase() {
		final var dto = StateDTO.of(
				null,
				GeographyTesting.COUNTRY_ID,
				TestNamesGenerator.generateCode(),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates a state")
	void updatesState() {
		final var dto = StateDTO.of(
				GeographyTesting.STATE_TO_UPDATE,
				GeographyTesting.COUNTRY_ID,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"countryId", equalToIgnoringCase( dto.countryId ),
						"code", equalToIgnoringCase( dto.code ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code on existent ignoring case")
	void failsIfDuplicatedCodeOnExistentIgnoringCase() {
		final var dto = StateDTO.of(
				GeographyTesting.STATE_TO_UPDATE,
				GeographyTesting.COUNTRY_ID,
				" test_existent ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistentIgnoreCase() {
		final var dto = StateDTO.of(
				GeographyTesting.STATE_TO_UPDATE,
				GeographyTesting.COUNTRY_ID,
				TestNamesGenerator.generateCode(),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/state" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Finds state by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/state/{0}", GeographyTesting.STATE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( GeographyTesting.STATE_ID ),
						"countryId", notNullValue(),
						"code", notNullValue(),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Deletes state")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/geography/state/{0}", GeographyTesting.STATE_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/state/{0}", GeographyTesting.STATE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails to delete if state has cities")
	void failsDeleteIfStateHasCities() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/geography/state/{0}", GeographyTesting.STATE_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene ciudades asignadas" )
				);
	}
}