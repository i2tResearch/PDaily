package co.haruk.sms.business.structure.geography;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
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
import co.haruk.sms.business.structure.geography.app.CountryDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 5/12/19
 **/
@SMSTest
@DisplayName("Country tests")
class CountryResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.GEOGRAPHY );
	}

	@Test
	@DisplayName("Finds all")
	void findAllCountries() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/country" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Creates new country")
	void saveCountry() {
		final var dto = CountryDTO.of(
				null, TestNamesGenerator.generateNumericString( 2 ),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"code", equalTo( dto.code ),
						"name", equalTo( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code")
	void failsIfDuplicatedCode() {
		final var dto = CountryDTO.of(
				null,
				"GE",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated code ignoring case")
	void failsIfDuplicatedCodeIgnoringCase() {
		final var dto = CountryDTO.of(
				null,
				" ge ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		final var dto = CountryDTO.of(
				null,
				TestNamesGenerator.generateNumericString( 2 ),
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		final var dto = CountryDTO.of(
				null,
				TestNamesGenerator.generateNumericString( 2 ),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates country")
	void updatesCountry() {
		final var dto = CountryDTO.of(
				GeographyTesting.COUNTRY_TO_UPDATE,
				TestNamesGenerator.generateNumericString( 2 ),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"code", equalTo( dto.code ),
						"name", equalTo( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code on existent ignoring case")
	void failsIfDuplicatedCodeOnExistentIgnoringCase() {
		final var dto = CountryDTO.of(
				GeographyTesting.COUNTRY_TO_UPDATE,
				" ge ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent ignoring case")
	void failsIfDuplicatedNameOnExistentIgnoringCase() {
		final var dto = CountryDTO.of(
				GeographyTesting.COUNTRY_TO_UPDATE,
				TestNamesGenerator.generateNumericString( 2 ),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/country" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Finds country by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/country/{0}", GeographyTesting.COUNTRY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( GeographyTesting.COUNTRY_ID ),
						"code", notNullValue(),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Deletes country")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/geography/country/{0}", GeographyTesting.COUNTRY_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/country/{0}", GeographyTesting.COUNTRY_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if deleting with states assigned")
	void failsIfDeletingWithStates() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/geography/country/{0}", GeographyTesting.COUNTRY_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "tiene departamentos asignados" )
				);
	}
}