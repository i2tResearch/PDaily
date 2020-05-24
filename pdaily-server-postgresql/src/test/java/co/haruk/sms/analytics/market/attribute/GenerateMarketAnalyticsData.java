package co.haruk.sms.analytics.market.attribute;

import static co.haruk.core.testing.TestNamesGenerator.generateName;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_A_OPTION_A;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_A_OPTION_B;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_A_OPTION_C;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_C_OPTION_A;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_C_OPTION_B;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.ATTRIBUTE_C_OPTION_C;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.MARKET_ATTRIBUTE_A;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.MARKET_ATTRIBUTE_B;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.MARKET_ATTRIBUTE_C;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.MARKET_ATTRIBUTE_D;
import static co.haruk.sms.analytics.market.attribute.AttributeDashboardTesting.MARKET_ATTRIBUTE_E;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.business.structure.customer.app.CustomerRequestDTO;
import co.haruk.sms.business.structure.customer.domain.model.CustomerType;
import co.haruk.sms.business.structure.customer.domain.model.view.CustomerReadView;
import co.haruk.sms.business.structure.subsidiary.SubsidiaryTesting;
import co.haruk.sms.market.measurement.attribute.value.app.AttributeValueDTO;
import co.haruk.testing.DataSets;

//@SMSTest
@DisplayName("Generate random data for Dashboard")
public class GenerateMarketAnalyticsData implements IDataSetDependent {
	final List<String> ATTRIBUTE_A_VALUES = List.of(
			ATTRIBUTE_A_OPTION_A,
			ATTRIBUTE_A_OPTION_B,
			ATTRIBUTE_A_OPTION_C
	);
	final List<String> ATTRIBUTE_C_VALUES = List.of(
			ATTRIBUTE_C_OPTION_A,
			ATTRIBUTE_C_OPTION_B,
			ATTRIBUTE_C_OPTION_C
	);

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.MARKET_ATTRIBUTE_DASHBOARD );
	}

	//@Test
	public void generateRegister() {
		int totalCustomers = 200;
		for ( int i = 1; i <= totalCustomers; i++ ) {
			final var dto = CustomerRequestDTO.of(
					null,
					TestNamesGenerator.generateColombianNIT(),
					null,
					TestNamesGenerator.generateEmail(),
					"CLIENT_GENERATED_" + i,
					SubsidiaryTesting.SUBSIDIARY_ID,
					null,
					CustomerType.BUYER.name()
			);
			final var response = given().body( dto )
					.post( "/business/customer" )
					.then().extract().response().as( CustomerReadView.class );
			createAttributesForCustomer( response.id );
		}
	}

	public void createAttributesForCustomer(String customer) {
		saveMarketAttributeA( customer );
		saveMarketAttributeB( customer );
		saveMarketAttributeC( customer );
		saveMarketAttributeD( customer );
		saveMarketAttributeE( customer );
	}

	public void saveMarketAttributeA(String customerId) {
		final var dto = AttributeValueDTO.of(
				null, MARKET_ATTRIBUTE_A,
				ATTRIBUTE_A_VALUES.toString(), customerId
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 );
	}

	public void saveMarketAttributeB(String customerId) {
		final var dto = AttributeValueDTO.of(
				null, MARKET_ATTRIBUTE_B,
				"300", customerId
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 );
	}

	public void saveMarketAttributeC(String customerId) {
		final var dto = AttributeValueDTO.of(
				null, MARKET_ATTRIBUTE_C,
				ATTRIBUTE_C_VALUES.toString(), customerId
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 );
	}

	public void saveMarketAttributeD(String customerId) {
		final boolean randomBoolean = ThreadLocalRandom.current().nextBoolean();
		final var dto = AttributeValueDTO.of(
				null, MARKET_ATTRIBUTE_D,
				String.valueOf( randomBoolean ),
				customerId
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 );
	}

	public void saveMarketAttributeE(String client) {
		final var dto = AttributeValueDTO.of(
				null, MARKET_ATTRIBUTE_E,
				"\"" + generateName() + "\"", client
		);

		given().body( dto )
				.post( "/market/attribute/value" )
				.then()
				.statusCode( 200 );
	}
}
