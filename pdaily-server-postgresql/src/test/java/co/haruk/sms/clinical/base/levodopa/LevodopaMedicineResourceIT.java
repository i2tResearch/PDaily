package co.haruk.sms.clinical.base.levodopa;

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
import co.haruk.sms.clinical.base.levodopa.app.LevodopaMedicineDTO;
import co.haruk.sms.clinical.base.levodopa.type.LevodopaTypeTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Levodopa Medicine tests")
public class LevodopaMedicineResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.LEVODOPA_MEDICINE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/medicine/levodopa" )
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
				.get( "/medicine/levodopa/{0}", LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID ),
						"name", notNullValue(),
						"typeId", notNullValue(),
						"typeLabel", notNullValue(),
						"dose", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new levodopa medicine")
	void saveLevodopaMedicine() {
		final var dto = LevodopaMedicineDTO.of(
				null, TestNamesGenerator.generateName(),
				LevodopaTypeTesting.LEVODOPA_TYPE_ID, 210
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"typeId", equalToIgnoringCase( dto.typeId ),
						"dose", equalTo( dto.dose )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label")
	void failsToSaveIfDuplicatedName() {
		final var dto = LevodopaMedicineDTO.of(
				null, "EXISTENT",
				LevodopaTypeTesting.LEVODOPA_TYPE_ID, 210
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated label ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = LevodopaMedicineDTO.of(
				null, " existent ",
				LevodopaTypeTesting.LEVODOPA_TYPE_ID, 210
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an levodopa medicine")
	void updatesLevodopaMedicine() {
		final var dto = LevodopaMedicineDTO.of(
				LevodopaMedicineTesting.LEVODOPA_MEDICINE_TO_UPDATE,
				TestNamesGenerator.generateName(), LevodopaTypeTesting.LEVODOPA_TYPE_ID, 210
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name ),
						"typeId", equalToIgnoringCase( dto.typeId ),
						"dose", equalTo( dto.dose )
				);
	}

	@Test
	@DisplayName("Fails if duplicated label on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = LevodopaMedicineDTO.of(
				LevodopaMedicineTesting.LEVODOPA_MEDICINE_TO_UPDATE, " existent ",
				LevodopaTypeTesting.LEVODOPA_TYPE_ID, 210
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/medicine/levodopa" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an levodopa medicine")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/medicine/levodopa/{0}", LevodopaMedicineTesting.LEVODOPA_MEDICINE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/medicine/levodopa/{0}", LevodopaMedicineTesting.LEVODOPA_MEDICINE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
