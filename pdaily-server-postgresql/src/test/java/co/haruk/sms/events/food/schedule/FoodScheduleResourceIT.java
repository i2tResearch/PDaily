package co.haruk.sms.events.food.schedule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.events.food.schedule.app.FoodScheduleDTO;
import co.haruk.sms.events.physical.PhysicalEventTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Food schedule tests")
public class FoodScheduleResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.FOOD_SCHEDULE );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/food/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/schedule/food/{0}", FoodScheduleTesting.FOOD_SCHEDULE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( FoodScheduleTesting.FOOD_SCHEDULE_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"schedule", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new food schedule")
	void saveFoodSchedule() {
		final var dto = FoodScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, "12:00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/schedule/food" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"schedule", equalToIgnoringCase( dto.schedule )
				);
	}

	@Test
	@DisplayName("Fail saves a new food schedule by bad schedule format")
	void failSaveFoodSchedule() {
		final var dto = FoodScheduleDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, "12-00"
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
	@DisplayName("Fail saves a new food schedule by dupplicated schedule")
	void failSaveFoodScheduleByDupplicatedSchedule() {
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
	@DisplayName("Updates an food schedule")
	void updatesFoodSchedule() {
		final var dto = FoodScheduleDTO.of(
				FoodScheduleTesting.FOOD_SCHEDULE_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				"15:00"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/schedule/food" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"schedule", equalTo( dto.schedule )
				);
	}

	@Test
	@DisplayName("Deletes an food schedule")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/schedule/food/{0}", FoodScheduleTesting.FOOD_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/schedule/food/{0}", FoodScheduleTesting.FOOD_SCHEDULE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
