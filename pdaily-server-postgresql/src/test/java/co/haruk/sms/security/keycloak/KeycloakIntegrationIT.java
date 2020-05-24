package co.haruk.sms.security.keycloak;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.security.app.keycloak.KeycloakCredentialDTO;
import co.haruk.sms.security.user.UserTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 15/2/20
 **/
@SMSTest
@DisplayName("Keycloak integration tests")
public class KeycloakIntegrationIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.SALES_REP );
	}

	@Test
	@DisplayName("Find all users")
	void findAll() {
		given().get( "/people/all" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Counts users")
	void countAll() {
		given().get( "/people/count" )
				.then()
				.statusCode( 200 )
				.body(
						"count", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Success with valid credentials")
	void successWithValidCredentials() {
		final var credential = KeycloakCredentialDTO.of( "existent", "PASSWORD" );
		given().body( credential )
				.post( "/people/validate-credentials" )
				.then()
				.statusCode( 200 );
	}

	@Test
	@DisplayName("Fails with invalid credentials")
	void failsWithInvalidCredentials() {
		final var credential = KeycloakCredentialDTO.of( "existent", "password" );
		given().body( credential )
				.post( "/people/validate-credentials" )
				.then()
				.statusCode( 401 );
	}

	@Test
	@DisplayName("Changes password correctly")
	void changesPasswordCorrectly() {
		final var credential = KeycloakCredentialDTO.of( "to_change_pass", "password_changed" );
		given().body( credential )
				.post( "/people/change-password" )
				.then()
				.statusCode( 200 );

		given().body( credential )
				.post( "/people/validate-credentials" )
				.then()
				.statusCode( 200 );
	}

	@Test
	@DisplayName("Find user by username")
	void findsByUserName() {
		final String userName = "existent";
		given().get( "/people/by-username/{0}", userName )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( UserTesting.USER_ID ),
						"name", notNullValue(),
						"lastName", notNullValue(),
						"email", notNullValue(),
						"credential.username", equalToIgnoringCase( userName )
				);
	}

	@Test
	@DisplayName("Finds user groups")
	void findUserGroups() {
		given().get( "/people/companies-for/{0}", UserTesting.USER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds user roles")
	void findUserRoles() {
		given().get( "/security/role/for-user/{0}", UserTesting.USER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}
}
