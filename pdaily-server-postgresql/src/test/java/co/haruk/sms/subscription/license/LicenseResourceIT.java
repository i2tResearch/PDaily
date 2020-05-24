package co.haruk.sms.subscription.license;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.subscription.license.app.LicenseDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author andres2508 on 15/11/19
 **/
@SMSTest
@DisplayName("License tests")
class LicenseResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.LICENSE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.when()
				.get( "/subscription/license" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds license by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.when()
				.get( "/subscription/license/{0}", LicenseTesting.LICENSE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( LicenseTesting.LICENSE_ID ),
						"name", equalToIgnoringCase( "EXISTENT" )
				);
	}

	@Test
	@DisplayName("Deletes license")
	void deleteEntity() {
		given().contentType( MediaType.APPLICATION_JSON )
				.when()
				.delete( "/subscription/license/{0}", LicenseTesting.LICENSE_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.when()
				.get( "/subscription/license/{0}", LicenseTesting.LICENSE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Creates license")
	void createsEntity() {
		LicenseDTO dto = LicenseDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.when()
				.post( "/subscription/license" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		LicenseDTO dto = LicenseDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.when()
				.post( "/subscription/license" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		LicenseDTO dto = LicenseDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.when()
				.post( "/subscription/license" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates license")
	void updatesEntity() {
		LicenseDTO dto = LicenseDTO.of( LicenseTesting.LICENSE_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.when()
				.post( "/subscription/license" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"name", equalToIgnoringCase( dto.name )
				);
	}
}