package co.icesi.testing;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import co.icesi.pdaily.subscription.account.AccountTesting;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

/**
 * @author cristhiank on 14/11/19
 **/
public class SMSTestingExtension implements BeforeAllCallback {
	@Override
	public void beforeAll(ExtensionContext extensionContext) {
		System.out.println( "Executing SMS test extension" );
		initRestAssured();
	}

	private void initRestAssured() {
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.addHeader( "haruk-tenant", AccountTesting.ACCOUNT_ID ).build();
	}
}
