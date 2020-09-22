package co.icesi.pdaily.events.levodopa.schedule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.clinical.base.levodopa.LevodopaMedicineTesting;
import co.icesi.pdaily.events.food.schedule.app.FoodScheduleDTO;
import co.icesi.pdaily.events.levodopa.schedule.app.LevodopaScheduleDTO;
import co.icesi.pdaily.events.physical.PhysicalEventTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Levodopa schedule tests")
public class LevodopaScheduleResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.LEVODOPA_SCHEDULE );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/levodopa/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/schedule/levodopa/{0}", LevodopaScheduleTesting.LEVODOPA_SCHEDULE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LevodopaScheduleTesting.LEVODOPA_SCHEDULE_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"medicineId", notNullValue(),
						"medicineLabel", notNullValue(),
						"medicineConcentration", notNullValue(),
						"medicineTypeId", notNullValue(),
						"medicineTypeLabel", notNullValue(),
						"schedule", notNullValue(),
						"dose", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new levodopa schedule")
	void saveLevodopaSchedule() {
		final var dto = LevodopaScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID, "12:00", 2
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/levodopa" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"medicineId", equalToIgnoringCase( dto.medicineId ),
						"schedule", equalToIgnoringCase( dto.schedule ),
						"dose", equalTo( dto.dose )
				);
	}

	@Test
	@DisplayName("Fail saves a new levodopa schedule by bad schedule")
	void failSaveLevodopaSchedule() {
		final var dto = LevodopaScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID, "12-00", 2
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/levodopa" )
				.then()
				.log().body()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail saves a new levodopa schedule by dupplicated schedule")
	void failSaveLevodopaScheduleByDupplicatedSchedule() {
		final var dto = FoodScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, "20:00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/food" )
				.then()
				.log().body()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fail saves a new levodopa schedule by dose")
	void failSaveLevodopaScheduleByDosis() {
		final var dto = LevodopaScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID, "12:00", -1
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/levodopa" )
				.then()
				.log().body()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an levodopa schedule")
	void updatesLevodopaSchedule() {
		final var dto = LevodopaScheduleDTO.of(
				LevodopaScheduleTesting.LEVODOPA_SCHEDULE_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID, "19:00", 5
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/schedule/levodopa" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"schedule", equalTo( dto.schedule )
				);
	}

	@Test
	@DisplayName("Deletes an levodopa schedule")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/schedule/levodopa/{0}", LevodopaScheduleTesting.LEVODOPA_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/levodopa/{0}", LevodopaScheduleTesting.LEVODOPA_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
