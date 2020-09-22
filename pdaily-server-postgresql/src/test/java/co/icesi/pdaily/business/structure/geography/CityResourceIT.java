package co.icesi.pdaily.business.structure.geography;

import static io.restassured.RestAssured.given;
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
import co.icesi.pdaily.business.structure.geography.app.CityDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

/**
 * @author andres2508 on 5/12/19
 **/
@PDailyTest
@DisplayName("City tests")
class CityResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.GEOGRAPHY );
	}

	@Test
	@DisplayName("Finds for state")
	void findForState() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/city/for-state/{0}", GeographyTesting.STATE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Creates a new city")
	void saveCity() {
		final var dto = CityDTO.of(
				null,
				GeographyTesting.STATE_ID,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"stateId", equalToIgnoringCase( dto.stateId ),
						"code", equalToIgnoringCase( dto.code ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code")
	void failsIfDuplicatedCode() {
		final var dto = CityDTO.of(
				null,
				GeographyTesting.STATE_ID,
				"TEST_EXISTENT",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated code ignoring case")
	void failsIfDuplicatedCodeIgnoringCase() {
		final var dto = CityDTO.of(
				null,
				GeographyTesting.STATE_ID,
				" test_existent ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		final var dto = CityDTO.of(
				null,
				GeographyTesting.STATE_ID,
				TestNamesGenerator.generateCode(),
				"EXISTENT"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoreCase() {
		final var dto = CityDTO.of(
				null,
				GeographyTesting.STATE_ID,
				TestNamesGenerator.generateCode(),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates a city")
	void updatesCity() {
		final var dto = CityDTO.of(
				GeographyTesting.CITY_TO_UPDATE,
				GeographyTesting.STATE_ID,
				TestNamesGenerator.generateCode(),
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"stateId", equalToIgnoringCase( dto.stateId ),
						"code", equalToIgnoringCase( dto.code ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated code on existent ignoring case")
	void failsIfDuplicatedCodeOnExistentIgnoringCase() {
		final var dto = CityDTO.of(
				GeographyTesting.CITY_TO_UPDATE,
				GeographyTesting.STATE_ID,
				" test_existent ",
				TestNamesGenerator.generateName()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistentIgnoreCase() {
		final var dto = CityDTO.of(
				GeographyTesting.CITY_TO_UPDATE,
				GeographyTesting.STATE_ID,
				TestNamesGenerator.generateCode(),
				" existent "
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/structure/geography/city" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Finds city by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/city/{0}", GeographyTesting.CITY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( GeographyTesting.CITY_ID ),
						"stateId", notNullValue(),
						"code", notNullValue(),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Deletes city")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/structure/geography/city/{0}", GeographyTesting.CITY_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/structure/geography/city/{0}", GeographyTesting.CITY_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

}
