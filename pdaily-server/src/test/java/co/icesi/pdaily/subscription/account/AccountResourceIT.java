package co.icesi.pdaily.subscription.account;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.icesi.pdaily.subscription.account.app.AccountDTO;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

/**
 * @author cristhiank on 30/10/19
 **/
@SMSTest
@DisplayName("Account tests")
class AccountResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ACCOUNT );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/subscription/account" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThanOrEqualTo( 0 )
				);
	}

	@Test
	@DisplayName("Creates new account")
	void saveNewAccount() {
		AccountDTO dto = AccountDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateColombianNIT()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalTo( dto.name ),
						"taxID", equalToIgnoringCase( dto.taxID )
				);
	}

	@Test
	@DisplayName("Updates account")
	void updatesExistent() {
		AccountDTO dto = AccountDTO.of(
				AccountTesting.ACCOUNT_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateColombianNIT()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( AccountTesting.ACCOUNT_TO_UPDATE ),
						"name", equalTo( dto.name ),
						"taxID", equalToIgnoringCase( dto.taxID )
				);
	}

	@Test
	@DisplayName("Fails if duplicated tax id")
	void failsIfDuplicatedTaxID() {
		AccountDTO dto = AccountDTO.of(
				AccountTesting.ACCOUNT_DUPLICATED_TAXID,
				"TO_UPDATE_DUPLICATED",
				"999999999-9"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 400 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/subscription/account/{0}", AccountTesting.ACCOUNT_DUPLICATED_TAXID )
				.then()
				.statusCode( 200 )
				.body(
						"taxID", equalToIgnoringCase( "999999999-3" )
				);
	}

	@Test
	@DisplayName("Fails if invalid tax id")
	void failsIfInvalidTaxID() {
		AccountDTO dto = AccountDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateCode()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsIfDuplicatedName() {
		AccountDTO dto = AccountDTO.of(
				null,
				"EXISTENT",
				TestNamesGenerator.generateColombianNIT()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsIfDuplicatedNameIgnoringCase() {
		AccountDTO dto = AccountDTO.of(
				null,
				" existent ",
				TestNamesGenerator.generateColombianNIT()
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/subscription/account" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes account")
	void deletesSubscription() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/subscription/account/{0}", AccountTesting.ACCOUNT_TO_DELETE )
				.then()
				.statusCode( 204 );
		// Then doesn't exists
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/subscription/account/{0}", AccountTesting.ACCOUNT_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
