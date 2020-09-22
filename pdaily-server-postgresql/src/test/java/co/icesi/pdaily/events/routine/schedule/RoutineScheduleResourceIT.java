package co.icesi.pdaily.events.routine.schedule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.clinical.base.routine.type.RoutineTypeTesting;
import co.icesi.pdaily.events.physical.PhysicalEventTesting;
import co.icesi.pdaily.events.routine.schedule.app.RoutineScheduleDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Routine schedule tests")
public class RoutineScheduleResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ROUTINE_SCHEDULE );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/routine/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/schedule/routine/{0}", RoutineScheduleTesting.ROUTINE_SCHEDULE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( RoutineScheduleTesting.ROUTINE_SCHEDULE_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"typeId", notNullValue(),
						"typeLabel", notNullValue(),
						"schedule", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new routine schedule")
	void saveRoutineSchedule() {
		final var dto = RoutineScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, RoutineTypeTesting.ROUTINE_TYPE_ID, "12:00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/routine" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"typeId", equalToIgnoringCase( dto.typeId ),
						"schedule", equalToIgnoringCase( dto.schedule )
				);
	}

	@Test
	@DisplayName("Fail saves a new routine schedule by bad schedule")
	void failSaveRoutineSchedule() {
		final var dto = RoutineScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, RoutineTypeTesting.ROUTINE_TYPE_ID, "12-00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/routine" )
				.then()
				.log().body()
				.statusCode( 400 );
	}

	//    @Test
	//    @DisplayName("Fail saves a new routine schedule by dupplicated schedule")
	//    void failSaveRoutineScheduleByDupplicatedSchedule() {
	//        final var dto = FoodScheduleDTO.of(
	//                null, PhysicalEventTesting.PATIENT_ID, "20:00"
	//        );
	//        given().contentType( MediaType.APPLICATION_JSON )
	//                .body( dto )
	//                .log().body()
	//                .post( "/schedule/food" )
	//                .then()
	//                .log().body()
	//                .statusCode( 400 );
	//    }

	@Test
	@DisplayName("Updates an routine schedule")
	void updatesRoutineSchedule() {
		final var dto = RoutineScheduleDTO.of(
				RoutineScheduleTesting.ROUTINE_SCHEDULE_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				RoutineTypeTesting.ROUTINE_TYPE_TO_UPDATE, "19:00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/routine" )
				.then()
				.log().body()
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"typeId", equalToIgnoringCase( dto.typeId ),
						"schedule", equalToIgnoringCase( dto.schedule )
				);
	}

	@Test
	@DisplayName("Deletes an routine schedule")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/schedule/routine/{0}", RoutineScheduleTesting.ROUTINE_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/routine/{0}", RoutineScheduleTesting.ROUTINE_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
