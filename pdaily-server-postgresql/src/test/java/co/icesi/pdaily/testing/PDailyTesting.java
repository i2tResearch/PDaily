package co.icesi.pdaily.testing;

import java.time.LocalDate;
import java.time.ZoneOffset;

import io.restassured.specification.RequestSpecification;

/**
 * @author andres2508 on 7/3/20
 **/
public final class PDailyTesting {

	private PDailyTesting() {
	}

	public static RequestSpecification authWithDoctor(RequestSpecification request) {
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
