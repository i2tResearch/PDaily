package co.icesi.pdaily.business.structure.customer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
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
import co.icesi.pdaily.business.structure.address.AddressTesting;
import co.icesi.pdaily.business.structure.customer.app.CustomerRequestDTO;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerType;
import co.icesi.pdaily.business.structure.customer.holding.HoldingTesting;
import co.icesi.pdaily.business.structure.subsidiary.SubsidiaryTesting;
import co.icesi.testing.DataSets;
import co.icesi.testing.SMSTest;

/**
 * @author cristhiank on 9/12/19
 **/
@SMSTest
@DisplayName("Customer tests")
class CustomerResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.CUSTOMER );
	}

	@Test
	@DisplayName("Finds all customers for subsidiary")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/for-subsidiary/{0}", SubsidiaryTesting.SUBSIDIARY_ID )
				.then()
				.statusCode( 200 )
				.body(
						"$.size", greaterThan( 0 )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/{0}", CustomerTesting.CUSTOMER_ID )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DELETE )
				.then()
				.statusCode( 204 );
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DELETE )
				.then()
				.statusCode( 404 );
	}

	@Test
	@DisplayName("Disables customer by id")
	void disablesById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.post( "/business/customer/{0}/disable", CustomerTesting.CUSTOMER_TO_DISABLE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_DISABLE )
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
		given().contentType( MediaType.APPLICATION_JSON )
				.post( "/business/customer/{0}/enable", CustomerTesting.CUSTOMER_TO_ENABLE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/business/customer/{0}", CustomerTesting.CUSTOMER_TO_ENABLE )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( CustomerTesting.CUSTOMER_TO_ENABLE ),
						"active", equalTo( true )
				);
	}
}
