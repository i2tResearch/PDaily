package co.haruk.sms.clinical.base.animic.type;

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
import co.haruk.sms.clinical.base.animic.app.AnimicTypeDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Animic Type tests")
public class AnimicTypeResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ANIMIC_TYPE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/animic/type" )
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
				.get( "/animic/type/{0}", AnimicTypeTesting.ANIMIC_TYPE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( AnimicTypeTesting.ANIMIC_TYPE_ID ),
						"label", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new animic type")
	void saveAnimicType() {
		final var dto = AnimicTypeDTO.of( null, TestNamesGenerator.generateName(), 10 );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/animic/type" )
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
		final var dto = AnimicTypeDTO.of( null, "EXISTENT", 10 );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/animic/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated label ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = AnimicTypeDTO.of( null, " existent ", 10 );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/animic/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an animic type")
	void updatesAnimicType() {
		final var dto = AnimicTypeDTO.of( AnimicTypeTesting.ANIMIC_TYPE_TO_UPDATE, TestNamesGenerator.generateName(), 5 );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/animic/type" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( AnimicTypeTesting.ANIMIC_TYPE_TO_UPDATE ),
						"label", equalToIgnoringCase( dto.label )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = AnimicTypeDTO.of( AnimicTypeTesting.ANIMIC_TYPE_TO_UPDATE, " existent ", 6 );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/animic/type" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an animic type")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/animic/type/{0}", AnimicTypeTesting.ANIMIC_TYPE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/animic/type/{0}", AnimicTypeTesting.ANIMIC_TYPE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
