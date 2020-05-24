package co.haruk.testing;

import java.time.LocalDate;
import java.time.ZoneOffset;

import io.restassured.specification.RequestSpecification;

/**
 * @author andres2508 on 7/3/20
 **/
public final class SMSTesting {

	private SMSTesting() {
	}

	public static RequestSpecification authWithSalesRep(RequestSpecification request) {
		return request.auth().basic( "sales_rep_user_f", "sales_rep_user_f" );
	}

	public static RequestSpecification authWithNonAdmin(RequestSpecification request) {
		return request.auth().basic( "existent", "existent" );
	}

	public static void authWithAdmin(RequestSpecification request) {
		request.auth().basic( "admin", "admin" );
	}

	public static long nowMillis() {
		return LocalDate.now().atStartOfDay( ZoneOffset.systemDefault() ).toEpochSecond() * 1000;
	}
}
