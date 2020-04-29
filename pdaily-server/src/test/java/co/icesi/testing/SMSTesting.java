package co.icesi.testing;

import io.restassured.specification.RequestSpecification;

/**
 * @author cristhiank on 7/3/20
 **/
public final class SMSTesting {

	private SMSTesting() {
	}

	public static RequestSpecification authWithSalesRep(RequestSpecification request) {
		return request.auth().basic( "sales_rep_user_f", "sales_rep_user_f" );
	}

	public static void authWithAdmin(RequestSpecification request) {
		request.auth().basic( "admin", "admin" );
	}
}
