package co.icesi.pdaily.clinical.base.routine.type;

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
import co.icesi.pdaily.clinical.base.routines.app.RoutineTypeDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Routine Type tests")
public class RoutineTypeResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ROUTINE_TYPE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/routine/type" )
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
				.get( "/routine/type/{0}", RoutineTypeTesting.ROUTINE_TYPE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( RoutineTypeTesting.ROUTINE_TYPE_ID ),
						"label", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new routine type")
	void saveRoutineType() {
		final var dto = RoutineTypeDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/routine/type" )
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
		final var dto = RoutineTypeDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/routine/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated label ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = RoutineTypeDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/routine/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an routine type")
	void updatesRoutineType() {
		final var dto = RoutineTypeDTO.of( RoutineTypeTesting.ROUTINE_TYPE_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/routine/type" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( RoutineTypeTesting.ROUTINE_TYPE_TO_UPDATE ),
						"label", equalToIgnoringCase( dto.label )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = RoutineTypeDTO.of( RoutineTypeTesting.ROUTINE_TYPE_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/routine/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an routine type")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/routine/type/{0}", RoutineTypeTesting.ROUTINE_TYPE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/routine/type/{0}", RoutineTypeTesting.ROUTINE_TYPE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
