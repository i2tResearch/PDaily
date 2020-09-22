package co.icesi.pdaily.clinical.base.levodopa.type;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.clinical.base.levodopa.type.app.LevodopaTypeDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Levodopa Type tests")
public class LevodopaTypeResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.LEVODOPA_TYPE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/medicine/levodopa/type" )
				.then()
				.statusCode( 200 )
				.log().body()
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id correctly")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/medicine/levodopa/type/{0}", LevodopaTypeTesting.LEVODOPA_TYPE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LevodopaTypeTesting.LEVODOPA_TYPE_ID ),
						"label", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new levodopa type")
	void saveLevodopaType() {
		final var dto = LevodopaTypeDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa/type" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"label", equalToIgnoringCase( dto.label )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label")
	void failsToSaveIfDuplicatedName() {
		final var dto = LevodopaTypeDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated label ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = LevodopaTypeDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an levodopa type")
	void updatesLevodopaType() {
		final var dto = LevodopaTypeDTO.of( LevodopaTypeTesting.LEVODOPA_TYPE_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa/type" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LevodopaTypeTesting.LEVODOPA_TYPE_TO_UPDATE ),
						"label", equalToIgnoringCase( dto.label )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = LevodopaTypeDTO.of( LevodopaTypeTesting.LEVODOPA_TYPE_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an levodopa type")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/medicine/levodopa/type/{0}", LevodopaTypeTesting.LEVODOPA_TYPE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/medicine/levodopa/type/{0}", LevodopaTypeTesting.LEVODOPA_TYPE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
