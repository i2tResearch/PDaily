package co.icesi.pdaily.business.structure.subsidiary.doctor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.business.structure.subsidiary.SubsidiaryTesting;
import co.icesi.pdaily.business.structure.subsidiary.doctor.app.DoctorRequestDTO;
import co.icesi.pdaily.testing.DataSets;
import co.icesi.pdaily.testing.PDailyTest;

/**
 * @author andres2508 on 25/11/19
 **/
@PDailyTest
@DisplayName("Doctor tests")
class DoctorResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.SALES_REP );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds all for subsidiary")
	void findAllForSubsidiary() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds available users for subsidiary")
	void findAvailableUsersForSubsidiary() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep/available-users-for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep/{0}", DoctorTesting.DOCTOR_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( DoctorTesting.DOCTOR_ID ),
						"fullName", notNullValue(),
						"reference", notNullValue()
				);
	}

	@Test
	@DisplayName("Creates from existent user")
	void createFromUser() {
		var request = DoctorRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_ASSIGN )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( DoctorTesting.DOCTOR_TO_ASSIGN ),
						"fullName", notNullValue(),
						"reference", equalToIgnoringCase( request.reference )
				);
	}

	@Test
	@DisplayName("Updates existent doctor")
	void updatesFromUser() {
		var request = DoctorRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_UPDATE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( DoctorTesting.DOCTOR_TO_UPDATE ),
						"fullName", notNullValue(),
						"reference", equalToIgnoringCase( request.reference )
				);
	}

	@Test
	@DisplayName("Creates from existent user with null reference")
	void createFromUserNullReference() {
		var request = DoctorRequestDTO.of( null, SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_ASSIGN_NULL )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( DoctorTesting.DOCTOR_TO_ASSIGN_NULL ),
						"fullName", notNullValue(),
						"reference", nullValue()
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference on existent")
	void failsIfDuplicatedReferenceOnExistent() {
		var request = DoctorRequestDTO.of( "EXISTENT", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_UPDATE )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if user doesn't exist")
	void failsIfUserDoesntExist() {
		var request = DoctorRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
		var nonExistentUser = "09382EBA-46B9-4576-9284-24FC2702F89F";
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", nonExistentUser )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if duplicated reference")
	void failsIfDuplicatedReference() {
		var request = DoctorRequestDTO.of( "EXISTENT", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_ASSIGN )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoreCase() {
		var request = DoctorRequestDTO.of( " existent ", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", DoctorTesting.DOCTOR_TO_ASSIGN )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes doctor")
	void deletesDoctor() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/sales-rep/{0}", DoctorTesting.DOCTOR_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep/{0}", DoctorTesting.DOCTOR_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
