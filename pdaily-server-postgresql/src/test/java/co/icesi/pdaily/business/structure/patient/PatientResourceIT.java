package co.icesi.pdaily.business.structure.patient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.patient.app.PatientDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

@PDailyTest
@DisplayName("Patient tests")
public class PatientResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PATIENTS );
	}

	@Test
	@DisplayName("Finds all")
	void findByPatient() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/patient" )
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
				.get( "/business/patient/{0}", PatientTesting.PATIENT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( PatientTesting.PATIENT_ID ),
						"fullName", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new patient")
	void savePatient() {
		final var dto = PatientDTO.of(
				null, "testing add"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.log().body()
				.post( "/business/patient" )
				.then()
				.log().body()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"fullName", equalToIgnoringCase( dto.fullName )
				);
	}

	@Test
	@DisplayName("Updates an patient")
	void updatesPatient() {
		final var dto = PatientDTO.of(
				PatientTesting.PATIENT_TO_UPDATE,
				"Testing update"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/patient" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"fullName", equalToIgnoringCase( dto.fullName )
				);
	}

	@Test
	@DisplayName("Deletes an patient")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/patient/{0}", PatientTesting.PATIENT_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/patient/{0}", PatientTesting.PATIENT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
