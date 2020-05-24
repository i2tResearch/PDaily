package co.haruk.sms.sales.salesorder.source;

import static io.restassured.RestAssured.given;
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
import co.haruk.sms.sales.salesorder.source.app.OrderSourceDTO;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

/**
 * @author cristhiank on 23/12/19
 **/
@SMSTest
@DisplayName("OrderSource tests")
class OrderSourceResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ORDER_SOURCE );
	}

	@Test
	@DisplayName("Finds all")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/source" )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds by id correctly")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/source/{0}", OrderSourceTesting.ORDER_SOURCE_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( OrderSourceTesting.ORDER_SOURCE_ID ),
						"name", notNullValue()
				);
	}

	@Test
	@DisplayName("Saves a new order source")
	void saveSource() {
		final var dto = OrderSourceDTO.of( null, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order/source" )
				.then()
				.statusCode( 200 )
				.body(
						"id", notNullValue(),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name")
	void failsToSaveIfDuplicatedName() {
		final var dto = OrderSourceDTO.of( null, "EXISTENT" );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order/source" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Fails if duplicated name ignoring case")
	void failsToSaveIfDuplicatedNameIgnoreCase() {
		final var dto = OrderSourceDTO.of( null, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order/source" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Updates an order source")
	void updatesSource() {
		final var dto = OrderSourceDTO.of( OrderSourceTesting.ORDER_SOURCE_TO_UPDATE, TestNamesGenerator.generateName() );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order/source" )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( OrderSourceTesting.ORDER_SOURCE_TO_UPDATE ),
						"name", equalToIgnoringCase( dto.name )
				);
	}

	@Test
	@DisplayName("Fails if duplicated name on existent")
	void failsIfDuplicatedNameOnExistent() {
		final var dto = OrderSourceDTO.of( OrderSourceTesting.ORDER_SOURCE_TO_UPDATE, " existent " );
		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order/source" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Deletes an order source")
	void deleteById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/order/source/{0}", OrderSourceTesting.ORDER_SOURCE_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/source/{0}", OrderSourceTesting.ORDER_SOURCE_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}