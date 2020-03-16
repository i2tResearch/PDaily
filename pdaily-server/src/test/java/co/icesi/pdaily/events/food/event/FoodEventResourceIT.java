package co.icesi.pdaily.events.food.event;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.events.food.event.app.FoodEventDTO;
import co.icesi.pdaily.events.physical.PhysicalEventTesting;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

@SMSTest
@DisplayName("Food events tests")
public class FoodEventResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.FOOD_EVENTS );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/food/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/event/food/{0}", FoodEventTesting.FOOD_EVENT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( FoodEventTesting.FOOD_EVENT_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"date", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new food event")
	void saveFoodEvent() {
		final var dto = FoodEventDTO.of(
				null, PhysicalEventTesting.PATIENT_ID,
				UTCDateTime.actualDate().dateAsLong()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/event/food" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"date", equalTo( dto.date )
				);
	}

	@Test
	@DisplayName("Updates an food event")
	void updatesFoodEvent() {
		final var dto = FoodEventDTO.of(
				FoodEventTesting.FOOD_EVENT_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				UTCDateTime.actualDate().dateAsLong()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/event/food" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"date", equalTo( dto.date )
				);
	}

	@Test
	@DisplayName("Deletes an food event")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/event/food/{0}", FoodEventTesting.FOOD_EVENT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/food/{0}", FoodEventTesting.FOOD_EVENT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
