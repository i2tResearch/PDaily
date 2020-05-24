package co.haruk.sms.security.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
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
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.sms.security.user.app.UserDTO;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.sms.subscription.license.LicenseTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 16/11/19
 **/
@SMSTest
@DisplayName("Users tests")
class UserResourceIT implements IDataSetDependent {
	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.SALES_REP );
	}

	@Test
	@DisplayName("Finds all users")
	void findAll() {
		given().get( "/subscription/user" )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds all users for an account")
	void findAllByAccount() {
		given().get( "/subscription/user/for-account/{0}", AccountTesting.ACCOUNT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Deletes user")
	void deletesUser() {
		given().delete( "/subscription/user/{0}", UserTesting.USER_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().get( "/subscription/user/{0}", UserTesting.USER_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Fails if deleting sales-rep user")
	void failsIfDeletingSalesRepUser() {
		given().delete( "/subscription/user/{0}", SalesRepTesting.SALES_REP_ID )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "asignado como rep. de ventas" )
				);
	}

	@Test
	@DisplayName("Creates user")
	void saveEntity() {
		var dto = UserDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNameNoSpaces(),
				"password",
				AccountTesting.ACCOUNT_ID,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"givenName", equalToIgnoringCase( dto.givenName ),
						"lastName", equalToIgnoringCase( dto.lastName ),
						"email", equalToIgnoringCase( dto.email ),
						"username", equalToIgnoringCase( dto.username ),
						"accountId", equalToIgnoringCase( dto.accountId ),
						"licenseId", equalToIgnoringCase( dto.licenseId )
				);
	}

	@Test
	@DisplayName("Updates user")
	void updateUser() {
		var dto = UserDTO.of(
				UserTesting.USER_TO_UPDATE,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNameNoSpaces(),
				null,
				AccountTesting.ACCOUNT_ID,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"givenName", equalToIgnoringCase( dto.givenName ),
						"lastName", equalToIgnoringCase( dto.lastName ),
						"email", equalToIgnoringCase( dto.email ),
						"username", equalToIgnoringCase( dto.username ),
						"accountId", equalToIgnoringCase( dto.accountId ),
						"licenseId", equalToIgnoringCase( dto.licenseId )
				);
	}

	@Test
	@DisplayName("Assigns license to user")
	void assignLicenseToUser() {
		UserDTO dto = given().get( "/subscription/user/{0}", UserTesting.USER_TO_UPDATE )
				.then()
				.statusCode( 200 )
				.extract()
				.body()
				.as( UserDTO.class );
		dto.licenseId = LicenseTesting.LICENSE_2_ID;
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( dto.id ),
						"givenName", equalToIgnoringCase( dto.givenName ),
						"lastName", equalToIgnoringCase( dto.lastName ),
						"email", equalToIgnoringCase( dto.email ),
						"username", equalToIgnoringCase( dto.username ),
						"accountId", equalToIgnoringCase( dto.accountId ),
						"licenseId", equalToIgnoringCase( dto.licenseId )
				);
	}

	@Test
	@DisplayName("Changes user password")
	void changeUserPassword() {
		final String newPassword = "new_password";
		given().body( newPassword )
				.post( "/subscription/user/reset-password/{0}", UserTesting.USER_TO_UPDATE )
				.then()
				.statusCode( 204 );
	}

	@Test
	@DisplayName("Checks if email exists")
	void checksIfExistsEmail() {
		given().accept( MediaType.TEXT_PLAIN )
				.get( "/subscription/user/exists-email/{0}", "existent@haruk.co" )
				.then()
				.statusCode( 200 )
				.contentType( MediaType.TEXT_PLAIN )
				.body( equalToIgnoringCase( "true" ) );
	}

	@Test
	@DisplayName("Checks if email does not exist")
	void checksIfDoesNotExistsEmail() {
		given().accept( MediaType.TEXT_PLAIN )
				.get( "/subscription/user/exists-email/{0}", "non-existent@non-haruk.co" )
				.then()
				.statusCode( 200 )
				.contentType( MediaType.TEXT_PLAIN )
				.body( equalToIgnoringCase( "false" ) );
	}

	@Test
	@DisplayName("Checks if username exists")
	void checksIfExistsUsername() {
		given().accept( MediaType.TEXT_PLAIN )
				.get( "/subscription/user/exists-username/{0}", "existent" )
				.then()
				.statusCode( 200 )
				.contentType( MediaType.TEXT_PLAIN )
				.body( equalToIgnoringCase( "true" ) );
	}

	@Test
	@DisplayName("Checks if username does not exist")
	void checksIfDoesNotExistsUsername() {
		given().accept( MediaType.TEXT_PLAIN )
				.get( "/subscription/user/exists-username/{0}", "non-existent-user" )
				.then()
				.statusCode( 200 )
				.contentType( MediaType.TEXT_PLAIN )
				.body( equalToIgnoringCase( "false" ) );
	}

	@Test
	@DisplayName("Fails if duplicated email")
	void failsIfDuplicatedEmail() {
		var dto = UserDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				"existent@haruk.co",
				TestNamesGenerator.generateNameNoSpaces(),
				null,
				AccountTesting.ACCOUNT_ID,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated email ignoring case")
	void failsIfDuplicatedEmailIgnoringCase() {
		var dto = UserDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				"EXISTENT@HARUK.CO",
				TestNamesGenerator.generateNameNoSpaces(),
				null,
				AccountTesting.ACCOUNT_ID,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated username")
	void failsIfDuplicatedUsername() {
		var dto = UserDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				"existent",
				null,
				AccountTesting.ACCOUNT_ID,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if empty account id")
	void failsIfEmptyAccountId() {
		var dto = UserDTO.of(
				null,
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateName(),
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateNameNoSpaces(),
				null,
				null,
				LicenseTesting.LICENSE_ID
		);
		given().body( dto )
				.post( "/subscription/user" )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "La cuenta es requerida" )
				);
	}
}
