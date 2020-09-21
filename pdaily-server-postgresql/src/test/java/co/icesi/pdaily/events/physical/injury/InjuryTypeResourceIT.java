package co.icesi.pdaily.events.physical.injury;

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
import co.icesi.pdaily.events.physical.injury.type.app.InjuryTypeDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Injury Type tests")
public class InjuryTypeResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.INJURY_TYPES );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/events/physical/injury" )
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
				.get( "/events/physical/injury/{0}", InjuryTypeTesting.INJURY_TYPE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( InjuryTypeTesting.INJURY_TYPE_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new injury type")
	void saveBodyPart() {
		final var dto = InjuryTypeDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/events/physical/injury" )
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
		final var dto = InjuryTypeDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/events/physical/injury" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = InjuryTypeDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/events/physical/injury" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an injury type")
	void updatesBodyPart() {
		final var dto = InjuryTypeDTO.of( InjuryTypeTesting.INJURY_TYPE_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/events/physical/injury" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( InjuryTypeTesting.INJURY_TYPE_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = InjuryTypeDTO.of( InjuryTypeTesting.INJURY_TYPE_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/events/physical/injury" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an injury type")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/events/physical/injury/{0}", InjuryTypeTesting.INJURY_TYPE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/events/physical/injury/{0}", InjuryTypeTesting.INJURY_TYPE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
