package co.haruk.sms.business.structure.customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.businessunit.BusinessUnitTesting;
import co.haruk.sms.business.structure.businessunit.zone.ZoneTesting;
import co.haruk.sms.business.structure.customer.app.CustomerBusinessViewRequest;
import co.haruk.sms.business.structure.customer.app.CustomerRequestDTO;
import co.haruk.sms.business.structure.customer.domain.model.CustomerType;
import co.haruk.sms.business.structure.customer.domain.model.view.CustomerReadView;
import co.haruk.sms.business.structure.subsidiary.SubsidiaryTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;
import co.haruk.testing.SMSTesting;

/**
 * @author andres2508 on 26/12/19
 **/
@SMSTest
@DisplayName("Customer business view tests")
public class CustomerBusinessViewIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CUSTOMER_BUSINESS_VIEW );
	}

	@Test
	@DisplayName("Finds business views for customer")
	void findsAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/business-view/{0}", CustomerTesting.CUSTOMER_ID_WITH_BUSINESS_VIEW )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);

	}

	@Test
	@DisplayName("Creates a new view")
	void createsBView() {
		final var dto = CustomerBusinessViewRequest.of(
				BusinessUnitTesting.BUSINESS_UNIT_ID,
				SalesRepTesting.SALES_REP_ID,
				ZoneTesting.ZONE_ID
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/business-view/{0}", CustomerTesting.CUSTOMER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"customerId", equalToIgnoringCase( CustomerTesting.CUSTOMER_ID ),
						"businessId", equalToIgnoringCase( dto.businessId ),
						"businessName", notNullValue(),
						"salesRepId", equalToIgnoringCase( dto.salesRepId ),
						"salesRepName", notNullValue(),
						"zoneId", equalToIgnoringCase( dto.zoneId ),
						"zoneName", notNullValue()
				);
	}

	@Test
	@DisplayName("Updates existent view")
	void updatesBView() {
		// los valores String fijos se crean en el dataset previamente
		final var dto = CustomerBusinessViewRequest.of(
				"3BC5A610-4A8A-4F56-88D8-1C01BA142FD2",
				SalesRepTesting.SALES_REP_ID,
				"44833F9F-D452-4753-AE89-2275250589F2"
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/business/customer/business-view/{0}", CustomerTesting.CUSTOMER_ID_WITH_BUSINESS_VIEW )
				.then()
				.statusCode( 200 )
				.body(
						"customerId", equalToIgnoringCase( CustomerTesting.CUSTOMER_ID_WITH_BUSINESS_VIEW ),
						"businessId", equalToIgnoringCase( dto.businessId ),
						"businessName", notNullValue(),
						"salesRepId", equalToIgnoringCase( dto.salesRepId ),
						"salesRepName", notNullValue(),
						"zoneId", equalToIgnoringCase( dto.zoneId ),
						"zoneName", notNullValue()
				);
	}

	@Test
	@DisplayName("Finds single by id")
	void findsBView() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get(
						"/business/customer/business-view/{0}/{1}", CustomerTesting.CUSTOMER_ID_WITH_BUSINESS_VIEW,
						BusinessUnitTesting.BUSINESS_UNIT_ID
				)
				.then()
				.statusCode( 200 )
				.body(
						"customerId", equalToIgnoringCase( CustomerTesting.CUSTOMER_ID_WITH_BUSINESS_VIEW ),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"businessName", notNullValue(),
						"salesRepId", notNullValue(),
						"salesRepName", notNullValue(),
						"zoneId", notNullValue(),
						"zoneName", notNullValue()
				);
	}

	@Test
	@DisplayName("Deletes existent view")
	void deletesBView() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete(
						"/business/customer/business-view/{0}/{1}", CustomerTesting.CUSTOMER_BUSINESS_VIEW_TO_DELETE,
						BusinessUnitTesting.BUSINESS_UNIT_ID
				)
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get(
						"/business/customer/business-view/{0}/{1}", CustomerTesting.CUSTOMER_BUSINESS_VIEW_TO_DELETE,
						BusinessUnitTesting.BUSINESS_UNIT_ID
				)
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Creates a minimal customer with business unit view")
	void saveCustomerWithBusinessUnitView() {
		final var dto = CustomerRequestDTO.of(
				null,
				TestNamesGenerator.generateColombianNIT(),
				null,
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID,
				null,
				CustomerType.BUYER.name(),
				BusinessUnitTesting.BUSINESS_UNIT_ID
		);
		final var readView = SMSTesting.authWithSalesRep( given() )
				.body( dto )
				.post( "/business/customer" )
				.then()
				.statusCode( 200 )
				.extract().response().as( CustomerReadView.class );

		given().contentType( MediaType.APPLICATION_JSON )
				.get(
						"/business/customer/business-view/{0}/{1}", readView.id,
						BusinessUnitTesting.BUSINESS_UNIT_ID
				)
				.then()
				.statusCode( 200 )
				.body(
						"customerId", equalToIgnoringCase( readView.id ),
						"businessId", equalToIgnoringCase( BusinessUnitTesting.BUSINESS_UNIT_ID ),
						"businessName", notNullValue(),
						"salesRepId", notNullValue(),
						"salesRepName", notNullValue(),
						"zoneId", nullValue(),
						"zoneName", nullValue()
				);
	}
}