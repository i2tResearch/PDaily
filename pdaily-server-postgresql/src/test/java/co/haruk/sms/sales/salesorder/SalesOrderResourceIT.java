package co.haruk.sms.sales.salesorder;

import static co.haruk.testing.SMSTesting.nowMillis;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.businessunit.product.ProductTesting;
import co.haruk.sms.business.structure.contact.ContactTesting;
import co.haruk.sms.business.structure.customer.CustomerTesting;
import co.haruk.sms.business.structure.subsidiary.salesrep.SalesRepTesting;
import co.haruk.sms.sales.salesorder.app.OrderDetailDTO;
import co.haruk.sms.sales.salesorder.app.SalesOrderDTO;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView;
import co.haruk.sms.sales.salesorder.source.OrderSourceTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Sales Order Test")
public class SalesOrderResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.SALES_ORDER );
	}

	@Test
	@DisplayName("Finds all sales orders by sales rep")
	void findAll() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/for-sales-rep/{0}", SalesRepTesting.SALES_REP_ID )
				.then()
				.statusCode( 200 )
				.body(
						"size()", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Finds sales order by id")
	void findById() {
		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/{0}", SalesOrderTesting.SALES_ORDER_ID )
				.then()
				.statusCode( 200 )
				.body(
						"id", equalToIgnoringCase( SalesOrderTesting.SALES_ORDER_ID ),
						"sourceId", equalToIgnoringCase( OrderSourceTesting.ORDER_SOURCE_ID ),
						"salesRepId", equalToIgnoringCase( SalesRepTesting.SALES_REP_ID ),
						"buyerId", equalToIgnoringCase( CustomerTesting.CUSTOMER_BUYER_ID ),
						"creationDate", notNullValue(),
						"orderDate", notNullValue(),
						"orderDetails.size", greaterThan( 0 )
				);
	}

	@Test
	@DisplayName("Save full sales order")
	void saveSalesOrder() {
		SalesOrderDTO dto = SalesOrderDTO.of(
				null, OrderSourceTesting.ORDER_SOURCE_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, nowMillis(), "testing add"
		);

		OrderDetailDTO orderDetail = OrderDetailDTO.of(
				null, ProductTesting.PRODUCT_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				ContactTesting.CONTACT_TO_SALES_ORDER, false, 1
		);
		dto.orderDetails = List.of( orderDetail );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 200 )
				.body( SalesOrderViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Fail if save sale order without details")
	void failSaveWithoutDetail() {
		SalesOrderDTO dto = SalesOrderDTO.of(
				null, OrderSourceTesting.ORDER_SOURCE_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, nowMillis(), null
		);

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 400 );
	}

	@Test
	@DisplayName("Save minimal Sales Order")
	void saveMinimalSalesOrder() {
		SalesOrderDTO dto = SalesOrderDTO.of(
				null, OrderSourceTesting.ORDER_SOURCE_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, nowMillis(), null
		);

		OrderDetailDTO orderDetail = OrderDetailDTO.of(
				null, ProductTesting.PRODUCT_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				null, false, 1
		);
		dto.orderDetails = List.of( orderDetail );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 200 )
				.body( SalesOrderViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Update sales order")
	void updateSalesOrder() {
		SalesOrderDTO dto = SalesOrderDTO.of(
				SalesOrderTesting.SALES_ORDER_TO_UPDATE, OrderSourceTesting.ORDER_SOURCE_TO_UPDATE,
				SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, nowMillis(), "update testing"
		);

		OrderDetailDTO orderDetail = OrderDetailDTO.of(
				SalesOrderTesting.ORDER_DETAIL_TO_UPDATE,
				ProductTesting.PRODUCT_TO_UPDATE,
				CustomerTesting.CUSTOMER_SUPPLIER_ID,
				null, true, 10
		);
		dto.orderDetails = List.of( orderDetail );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 200 )
				.body( SalesOrderViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Add details to sales order")
	void addDetailsToSalesOrder() {
		SalesOrderDTO dto = SalesOrderDTO.of(
				SalesOrderTesting.SALES_ORDER_ID, OrderSourceTesting.ORDER_SOURCE_ID, SalesRepTesting.SALES_REP_ID,
				CustomerTesting.CUSTOMER_BUYER_ID, nowMillis(), "Add details testing"
		);

		OrderDetailDTO orderDetailA = OrderDetailDTO.of(
				SalesOrderTesting.ORDER_DETAIL_ID, ProductTesting.PRODUCT_TO_UPDATE, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				ContactTesting.CONTACT_TO_SALES_ORDER, false, 5
		);
		OrderDetailDTO orderDetailB = OrderDetailDTO.of(
				null, ProductTesting.PRODUCT_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				ContactTesting.CONTACT_TO_SALES_ORDER, true, 9
		);
		dto.orderDetails = List.of( orderDetailA, orderDetailB );

		given().contentType( MediaType.APPLICATION_JSON )
				.body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 200 )
				.body( SalesOrderViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Delete details of sales order")
	void deleteDetailsOfSalesOrder() {
		// GIVEN an existent order
		final var view = given().get( "/sales/order/{0}", SalesOrderTesting.SALES_ORDER_TO_DELETE_DETAILS )
				.then()
				.statusCode( 200 )
				.extract().as( SalesOrderView.class );
		final var dto = SalesOrderDTO.of(
				view.id, view.sourceId,
				view.salesRepId,
				view.buyerId, view.orderDate, view.comments
		);
		// WHEN we change the details
		OrderDetailDTO orderDetail = OrderDetailDTO.of(
				null, ProductTesting.PRODUCT_ID, CustomerTesting.CUSTOMER_SUPPLIER_ID,
				ContactTesting.CONTACT_TO_SALES_ORDER, false, 15
		);
		dto.orderDetails = List.of( orderDetail );
		// THE order state changes
		given().body( dto )
				.post( "/sales/order" )
				.then()
				.statusCode( 200 )
				.body( SalesOrderViewMatcher.of( dto ) );
	}

	@Test
	@DisplayName("Delete sales order")
	void deleteSalesOrder() {
		given().contentType( MediaType.APPLICATION_JSON )
				.delete( "/sales/order/{0}", SalesOrderTesting.SALES_ORDER_TO_DELETE )
				.then()
				.statusCode( 204 );

		given().contentType( MediaType.APPLICATION_JSON )
				.get( "/sales/order/{0}", SalesOrderTesting.SALES_ORDER_TO_DELETE )
				.then()
				.statusCode( 404 );
	}
}
