package co.haruk.sms.security.authorization;

import static co.haruk.testing.SMSTesting.authWithNonAdmin;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.security.authorization.app.UserPermissionDTO;
import co.haruk.sms.security.user.UserTesting;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author andres2508 on 2/5/20
 **/
@SMSTest
@DisplayName("User permissions tests")
class UserPermissionsIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.AUTHORIZATION );
	}

	@Test
	@DisplayName("Finds user tenants")
	void findUserTenants() {
		given().get( "/security/authz/accounts-for-user/{0}", UserTesting.USER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds user granted activities")
	void findUserGrantedActivities() {
		given().get( "/security/authz/activities-granted-to-user/{0}/{1}", UserTesting.USER_ID, AccountTesting.ACCOUNT_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Creates and checks user permission")
	void createsUserPermissionCorrectly() {
		// GIVEN a permission
		final UserPermissionDTO permissionDTO = UserPermissionDTO.of(
				UserTesting.USER_ID,
				AccountTesting.ACCOUNT_ID,
				AuthorizationTesting.ACTIVITY_ID
		);
		// WHEN we grant it
		given().body( permissionDTO )
				.post( "/security/authz/grant-permission" )
				.then()
				.statusCode( 204 );
		// THEN it is granted
		authWithNonAdmin( given() ).body( permissionDTO )
				.get( "/security/authz/is-authorized-to/{0}", permissionDTO.activityId )
				.then()
				.statusCode( 200 )
				.body(
						"granted", is( true )
				);
	}

	@Test
	@DisplayName("Fails if user has permission")
	void failsIfUserHasPermission() {
		// GIVEN a permission
		final UserPermissionDTO permissionDTO = UserPermissionDTO.of(
				UserTesting.USER_ID,
				AccountTesting.ACCOUNT_ID,
				AuthorizationTesting.ACTIVITY_FOR_PERMISSION
		);
		// WHEN we grant it THEN it fails
		authWithNonAdmin( given() ).body( permissionDTO )
				.post( "/security/authz/grant-permission" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Revokes and checks user permission")
	void revokesUserPermission() {
		// GIVEN an existent permission
		final UserPermissionDTO permissionDTO = UserPermissionDTO.of(
				UserTesting.USER_ID,
				AccountTesting.ACCOUNT_ID,
				AuthorizationTesting.ACTIVITY_FOR_PERMISSION_DELETE
		);
		// WHEN we revoke it
		given().body( permissionDTO )
				.post( "/security/authz/revoke-permission" )
				.then()
				.statusCode( 204 );
		// THEN it is not permitted
		authWithNonAdmin( given() ).body( permissionDTO )
				.get( "/security/authz/is-authorized-to/{0}", permissionDTO.activityId )
				.then()
				.statusCode( 200 )
				.body(
						"granted", is( false )
				);
	}

	@Test
	@DisplayName("Fails if user hasn't permission")
	void failsIfUserHasntPermission() {
		final UserPermissionDTO permissionDTO = UserPermissionDTO.of(
				UserTesting.USER_ID,
				AccountTesting.ACCOUNT_ID,
				AuthorizationTesting.ACTIVITY_ID_INEXISTENT
		);
		// WHEN we revoke it
		given().body( permissionDTO )
				.post( "/security/authz/revoke-permission" )
				.then()
				.statusCode( 400 );
	}
}
