package co.icesi.pdaily.events.animic;

import static co.icesi.pdaily.testing.PDailyTesting.nowMillis;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.clinical.base.animic.type.AnimicTypeTesting;
import co.icesi.pdaily.events.animic.app.AnimicEventDTO;
import co.icesi.pdaily.events.physical.PhysicalEventTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Animic events tests")
public class AnimicEventResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ANIMIC_EVENT );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/animic/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/event/animic/{0}", AnimicEventTesting.ANIMIC_EVENT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( AnimicEventTesting.ANIMIC_EVENT_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"typeId", equalToIgnoringCase( AnimicTypeTesting.ANIMIC_TYPE_ID ),
						"typeLabel", notNullValue(),
						"intensity", greaterThan( 0 ),
						"occurenceDate", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new animic event")
	void saveAnimicEvent() {
		final var dto = AnimicEventDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, AnimicTypeTesting.ANIMIC_TYPE_ID,
				nowMillis()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/event/animic" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"ocurrenceDate", equalTo( dto.ocurrenceDate ),
						"typeId", equalToIgnoringCase( dto.typeId )
				);
	}

	@Test
	@DisplayName("Updates an animic event")
	void updatesAnimicEvent() {
		final var dto = AnimicEventDTO.of(
				AnimicEventTesting.ANIMIC_EVENT_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				AnimicTypeTesting.ANIMIC_TYPE_ID, nowMillis()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/event/animic" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"ocurrenceDate", equalTo( dto.ocurrenceDate ),
						"typeId", equalToIgnoringCase( dto.typeId )
				);
	}

	@Test
	@DisplayName("Deletes an animic event")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/event/animic/{0}", AnimicEventTesting.ANIMIC_EVENT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/animic/{0}", AnimicEventTesting.ANIMIC_EVENT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
