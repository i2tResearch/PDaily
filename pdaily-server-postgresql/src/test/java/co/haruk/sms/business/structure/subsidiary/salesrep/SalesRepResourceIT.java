package co.haruk.sms.business.structure.subsidiary.salesrep;

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
import co.haruk.sms.business.structure.businessunit.BusinessUnitTesting;
import co.haruk.sms.business.structure.subsidiary.SubsidiaryTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.app.SalesRepRequestDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 25/11/19
 **/
@SMSTest
@DisplayName("SalesRep tests")
class SalesRepResourceIT implements IDataSetDependent {

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
				.get( "business/sales-rep/{0}", SalesRepTesting.SALES_REP_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesRepTesting.SALES_REP_ID ),
						"fullName", notNullValue(),
						"reference", notNullValue()
				);
	}

	@Test
	@DisplayName("Creates from existent user")
	void createFromUser() {
		var request = SalesRepRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_ASSIGN )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesRepTesting.SALES_REP_TO_ASSIGN ),
						"fullName", notNullValue(),
						"reference", equalToIgnoringCase( request.reference )
				);
	}

	@Test
	@DisplayName("Updates existent sales rep")
	void updatesFromUser() {
		var request = SalesRepRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_UPDATE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesRepTesting.SALES_REP_TO_UPDATE ),
						"fullName", notNullValue(),
						"reference", equalToIgnoringCase( request.reference )
				);
	}

	@Test
	@DisplayName("Creates from existent user with null reference")
	void createFromUserNullReference() {
		var request = SalesRepRequestDTO.of( null, SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_ASSIGN_NULL )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesRepTesting.SALES_REP_TO_ASSIGN_NULL ),
						"fullName", notNullValue(),
						"reference", nullValue()
				);
	}

	@Test
	@DisplayName("Fails if duplicated reference on existent")
	void failsIfDuplicatedReferenceOnExistent() {
		var request = SalesRepRequestDTO.of( "EXISTENT", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_UPDATE )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if user doesn't exist")
	void failsIfUserDoesntExist() {
		var request = SalesRepRequestDTO.of( TestNamesGenerator.generateCode(), SubsidiaryTesting.SUBSIDIARY_ID );
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
		var request = SalesRepRequestDTO.of( "EXISTENT", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_ASSIGN )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated reference ignoring case")
	void failsIfDuplicatedReferenceIgnoreCase() {
		var request = SalesRepRequestDTO.of( " existent ", SubsidiaryTesting.SUBSIDIARY_ID );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( request )
				.post( "business/sales-rep/save-for-user/{0}", SalesRepTesting.SALES_REP_TO_ASSIGN )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes sales rep")
	void deletesSalesRep() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "business/sales-rep/{0}", SalesRepTesting.SALES_REP_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "business/sales-rep/{0}", SalesRepTesting.SALES_REP_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Add business unit to sales rep")
	void addBusinessUnit() {
		given().contentType( MediaType.APPLICATION_JSON )
				.post(
						"business/sales-rep/{0}/business-unit/{1}", SalesRepTesting.SALES_REP_ID,
						BusinessUnitTesting.BUSINESS_UNIT_ID
				)
				.then()
				.statusCode( 200 )
				.log().body();
	}
}
