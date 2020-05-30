package co.haruk.sms.events.levodopa.event;

import static co.haruk.testing.SMSTesting.nowMillis;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.clinical.base.levodopa.LevodopaMedicineTesting;
import co.haruk.sms.events.levodopa.event.app.LevodopaEventDTO;
import co.haruk.sms.events.physical.PhysicalEventTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Levodopa events tests")
public class LevodopaEventResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.LEVODOPA_EVENT );
	}

	@Test
	@DisplayName("Finds all by patient")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/levodopa/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
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
				.get( "/event/levodopa/{0}", LevodopaEventTesting.LEVODOPA_EVENT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LevodopaEventTesting.LEVODOPA_EVENT_ID ),
						"patientId", equalToIgnoringCase( PhysicalEventTesting.PATIENT_ID ),
						"date", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new levodopa event")
	void saveLevodopaEvent() {
		final var dto = LevodopaEventDTO.of(
				null, PhysicalEventTesting.PATIENT_ID, LevodopaMedicineTesting.LEVODOPA_MEDICINE_ID,
				nowMillis()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/event/levodopa" )
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
	@DisplayName("Updates an levodopa event")
	void updatesLevodopaEvent() {
		final var dto = LevodopaEventDTO.of(
				LevodopaEventTesting.LEVODOPA_EVENT_TO_UPDATE, PhysicalEventTesting.PATIENT_ID,
				LevodopaMedicineTesting.LEVODOPA_MEDICINE_TO_UPDATE, nowMillis()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/event/levodopa" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"patientId", equalToIgnoringCase( dto.patientId ),
						"date", equalTo( dto.date )
				);
	}

	@Test
	@DisplayName("Deletes an levodopa event")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/event/levodopa/{0}", LevodopaEventTesting.LEVODOPA_EVENT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/levodopa/{0}", LevodopaEventTesting.LEVODOPA_EVENT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
