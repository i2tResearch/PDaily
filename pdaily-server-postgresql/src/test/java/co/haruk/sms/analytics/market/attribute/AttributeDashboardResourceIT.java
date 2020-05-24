package co.haruk.sms.analytics.market.attribute;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@DisplayName("Attribute dashboard test")
public class AttributeDashboardResourceIT implements IDataSetDependent {

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.MARKET_ATTRIBUTE_DASHBOARD );
	}

	@Test
	@DisplayName("Process market attributes for customers")
	void processMarketAttributesForCustomers() {
		final var attributes = List.of(
				AttributeDashboardTesting.MARKET_ATTRIBUTE_A,
				AttributeDashboardTesting.MARKET_ATTRIBUTE_B,
				AttributeDashboardTesting.MARKET_ATTRIBUTE_C,
				AttributeDashboardTesting.MARKET_ATTRIBUTE_D,
				AttributeDashboardTesting.MARKET_ATTRIBUTE_E
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( attributes )
				.post( "/reports/market/attributes/by-customer" )
				.then()
				.statusCode( 200 )
				.body(
						"metadata.entities.id", hasItems( "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ),
						"metadata.entities.label", hasItems(
								"CLIENT_MARKET_ATTRIBUTE_A", "CLIENT_MARKET_ATTRIBUTE_B",
								"CLIENT_MARKET_ATTRIBUTE_C", "CLIENT_MARKET_ATTRIBUTE_D", "CLIENT_MARKET_ATTRIBUTE_E",
								"CLIENT_MARKET_ATTRIBUTE_F", "CLIENT_MARKET_ATTRIBUTE_G", "CLIENT_MARKET_ATTRIBUTE_H",
								"CLIENT_MARKET_ATTRIBUTE_I", "CLIENT_MARKET_ATTRIBUTE_J"
						),
						"metadata.attributes", hasSize( 5 )
				);
	}

	@Test
	@DisplayName("Process market attributes for contacts")
	void processMarketAttributesForContacts() {
		final var attributes = List.of(
				AttributeDashboardTesting.MARKET_ATTRIBUTE_A_CONTACT,
				AttributeDashboardTesting.MARKET_ATTRIBUTE_B_CONTACT
		);
		given().contentType( MediaType.APPLICATION_JSON )
				.body( attributes )
				.post( "/reports/market/attributes/by-contact" )
				.then()
				.statusCode( 200 )
				.body(
						"metadata.entities", hasSize( 1 ),
						"metadata.entities[0].label", equalTo( "CONTACT_MARKET_ATTRIBUTE" ),
						"metadata.attributes", hasSize( 2 ),
						"metadata.attributes.id", hasItems( "0", "1" ),
						"metadata.attributes.label", hasItems( "Especialidad", "Universidad donde realizo estudios" ),
						"rows", hasSize( 1 ),
						"rows.Sujeto", hasItem( "0" ),
						"rows.0", hasItem( "Oncologia" ),
						"rows.1", hasItem( "Universidad Icesi" )
				);
	}
}
