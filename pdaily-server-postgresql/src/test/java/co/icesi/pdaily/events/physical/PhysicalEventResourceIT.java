package co.icesi.pdaily.events.physical;

import static co.icesi.pdaily.testing.PDailyTesting.nowMillis;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.events.physical.app.BodyPartDetailDTO;
import co.icesi.pdaily.events.physical.app.PhysicalEventDTO;
import co.icesi.pdaily.events.physical.body.BodyPartTesting;
import co.icesi.pdaily.events.physical.injury.InjuryTypeTesting;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Physical Event tests")
public class PhysicalEventResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PHYSICAL_EVENTS );
	}

	@Test
	@DisplayName("Finds all physical events by patient")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/physical/for-patient/{0}", PhysicalEventTesting.PATIENT_ID )
				.then()
				.statusCode( 200 )
				.log().body()
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Find physical event by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/physical/{0}", PhysicalEventTesting.PHYSICAL_EVENT_ID )
				.then()
				.statusCode( 200 )
				.log().body()
				.body(
						"id", equalToIgnoringCase( PhysicalEventTesting.PHYSICAL_EVENT_ID ),
						"injuryTypeId", equalToIgnoringCase( InjuryTypeTesting.INJURY_TYPE_ID ),
						"intensity", notNullValue(),
						"injuryTypeName", notNullValue(),
						"initialDate", notNullValue(),
						"finalDate", notNullValue(),
						"bodyDetails.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Save full physical event")
	void saveActivity() {
		PhysicalEventDTO dto = PhysicalEventDTO.of(
				null, PhysicalEventTesting.PATIENT_ID,
				5, InjuryTypeTesting.INJURY_TYPE_ID, nowMillis(),
				nowMillis()
		);
		BodyPartDetailDTO bodyPart = BodyPartDetailDTO
				.of( null, BodyPartTesting.BODY_PART_ID );
		dto.bodyDetails = List.of( bodyPart );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().all()
				.post( "/event/physical" )
				.then()
				.statusCode( 200 )
				.log().body()
				.body( PhysicalEventViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Update physical event")
	void AupdateActivity() {
		PhysicalEventDTO dto = PhysicalEventDTO.of(
				PhysicalEventTesting.PHYSICAL_EVENT_TO_UPDATE, PhysicalEventTesting.PATIENT_ID, 10,
				InjuryTypeTesting.INJURY_TYPE_ID, nowMillis(),
				nowMillis()
		);
		BodyPartDetailDTO bodyPart = BodyPartDetailDTO
				.of( PhysicalEventTesting.BODY_DETAIL_TO_UPDATE, BodyPartTesting.BODY_PART_ID );
		dto.bodyDetails = List.of( bodyPart );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/event/physical" )
				.then()
				.statusCode( 200 )
				.body( PhysicalEventViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Add details to physical event")
	void removeDetails() {
		PhysicalEventDTO dto = PhysicalEventDTO.of(
				PhysicalEventTesting.PHYSICAL_EVENT_TO_UPDATE, PhysicalEventTesting.PATIENT_ID, 10,
				InjuryTypeTesting.INJURY_TYPE_ID, nowMillis(),
				nowMillis()
		);
		BodyPartDetailDTO bodyPart = BodyPartDetailDTO
				.of( PhysicalEventTesting.BODY_DETAIL_TO_UPDATE, BodyPartTesting.BODY_PART_ID );
		BodyPartDetailDTO bodyPartA = BodyPartDetailDTO
				.of( null, BodyPartTesting.BODY_PART_TO_UPDATE );
		dto.bodyDetails = List.of( bodyPart, bodyPartA );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/event/physical" )
				.then()
				.statusCode( 200 )
				.body( PhysicalEventViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Delete physical event")
	void deleteActivity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/event/physical/{0}", PhysicalEventTesting.PHYSICAL_EVENT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/event/physical/{0}", PhysicalEventTesting.PHYSICAL_EVENT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
