package co.haruk.sms.business.structure.customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.address.AddressTesting;
import co.haruk.sms.business.structure.customer.app.CustomerRequestDTO;
import co.haruk.sms.business.structure.customer.domain.model.CustomerType;
import co.haruk.sms.business.structure.customer.holding.HoldingTesting;
import co.haruk.sms.business.structure.subsidiary.SubsidiaryTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;
import co.haruk.testing.SMSTesting;

/**
 * @author andres2508 on 9/12/19
 **/
@SMSTest
@DisplayName("Customer tests")
class CustomerResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CUSTOMER_BUSINESS_VIEW );
	}

	@Test
	@DisplayName("Filters by sales rep")
	void filterBySalesRep() {
		SMSTesting.authWithSalesRep( given() )
				.get( "/business/customer/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 2 )
				);
	}

	@Test
	@DisplayName("Filters by sales rep, list of suppliers")
	void filterBySupplier() {
		SMSTesting.authWithSalesRep( given() )
				.get( "/business/customer/suppliers" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 1 )
				);
	}

	@Test
	@DisplayName("Filters by sales rep, list of buyer")
	void filterByBuyer() {
		SMSTesting.authWithSalesRep( given() )
				.get( "/business/customer/end-buyers" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", equalTo( 1 )
				);
	}

	@Test
	@DisplayName("Finds all customers for subsidiary")
	void findAll() {
		given().get( "/business/customer/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Creates a minimal customer")
	void saveCustomer() {
		final var dto = CustomerRequestDTO.of(
				null,
				TestNamesGenerator.generateColombianNIT(),
				null,
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID,
				null,
				CustomerType.BUYER.name()
		);
		given().body( dto )
				.post( "/business/customer" )
				.then()
				.statusCode( 200 )
				.body( CustomerReadViewMatcher.of( dto ) )
				.body(
						"id", notNullValue(),
						"active", equalTo( true )
				);
	}

	@Test
	@DisplayName("Updates a customer")
	void updateCustomer() {
		final var dto = CustomerRequestDTO.of(
				CustomerTesting.CUSTOMER_TO_UPDATE,
				TestNamesGenerator.generateColombianNIT(),
				HoldingTesting.HOLDING_TO_UPDATE,
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID,
				TestNamesGenerator.generateNumericString( 10 ),
				CustomerType.SUPPLIER.name()
		);
		given().body( dto )
				.post( "/business/customer" )
				.then()
				.statusCode( 200 )
				.body( CustomerReadViewMatcher.of( dto ) )
				.body(
						"active", equalTo( true )
				);
	}

	@Test
	@DisplayName("Creates a full customer")
	void saveFullCustomer() {
		final var dto = CustomerRequestDTO.of(
				null,
				TestNamesGenerator.generateColombianNIT(),
				HoldingTesting.HOLDING_ID,
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID,
				TestNamesGenerator.generateNumericString( 10 ),
				CustomerType.BUYER.name()
		);
		dto.mainAddress = AddressTesting.generateRandom( null );
		given().body( dto )
				.post( "/business/customer" )
				.then()
				.statusCode( 200 )
				.body( CustomerReadViewMatcher.of( dto ) )
				.body(
						"id", notNullValue(),
						"active", equalTo( true )
				);
	}

	@Test
	@DisplayName("Fails to create if address is invalid")
	void failsIfAddressIsInvalid() {
		final var dto = CustomerRequestDTO.of(
				null,
				TestNamesGenerator.generateColombianNIT(),
				HoldingTesting.HOLDING_ID,
				TestNamesGenerator.generateEmail(),
				TestNamesGenerator.generateName(),
				SubsidiaryTesting.SUBSIDIARY_ID,
				TestNamesGenerator.generateNumericString( 10 ),
				CustomerType.BUYER.name()
		);
		dto.mainAddress = AddressTesting.generateRandom( null );
		dto.mainAddress.cityId = null; // MAKES IT INVALID
		given().body( dto )
				.post( "/business/customer" )
				.then()
				.statusCode( 400 )
				.body(
						"messages[0]", containsStringIgnoringCase( "la dirección es invalida" )
				);
	}

	@Test
	@DisplayName("Finds by id")
	void findsById() {
		given().get( "/business/customer/{0}", CustomerTesting.CUSTOMER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( CustomerTesting.CUSTOMER_ID ),
						"name", notNullValue(),
						"taxID", notNullValue(),
						"mainEmailAddress", notNullValue(),
						"holdingId", equalToIgnoringCase( HoldingTesting.HOLDING_ID ),
						"subsidiaryId", equalToIgnoringCase( SubsidiaryTesting.SUBSIDIARY_ID ),
						"holdingName", notNullValue(),
						"reference", notNullValue(),
						"active", equalTo( true )
				);
	}

	@Test
	@DisplayName("Deletes by id")
	void deletesById() {
		given().delete( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Disables customer by id")
	void disablesById() {
		given().post( "/business/customer/{0}/disable", CustomerTesting.CUSTOMER_TO_DISABLE )
				.then()
				.statusCode( 204 );

		given().get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DISABLE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( CustomerTesting.CUSTOMER_TO_DISABLE ),
						"active", equalTo( false )
				);
	}

	@Test
	@DisplayName("Enables customer by id")
	void enablesById() {
		given().post( "/business/customer/{0}/enable", CustomerTesting.CUSTOMER_TO_ENABLE )
				.then()
				.statusCode( 204 );

		given().get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_ENABLE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( CustomerTesting.CUSTOMER_TO_ENABLE ),
						"active", equalTo( true )
				);
	}
}
