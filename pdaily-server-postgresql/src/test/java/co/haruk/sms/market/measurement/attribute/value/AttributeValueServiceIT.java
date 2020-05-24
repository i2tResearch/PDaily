package co.haruk.sms.market.measurement.attribute.value;

import static io.restassured.RestAssured.given;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.MarketAttributeTesting;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.value.domain.service.AttributeValueService;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@Tag("cdi")
@DisplayName("Attribute Value Service Test")
public class AttributeValueServiceIT implements IDataSetDependent, ICDIContainerDependent {
	@Inject
	AttributeValueService service;

	@BeforeAll
	public static void initialize() {
		HarukSession.setCurrentTenant( TenantId.of( AccountTesting.ACCOUNT_ID ) );
	}

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.ATTRIBUTE_VALUES );
	}

	@Test
	@DisplayName("Mark attribute values as deleted")
	public void markAttributeValuesAsDeleted(UserTransaction transaction) throws Exception {
		service.markValuesAsDeleted( MarketAttributeId.ofNotNull( MarketAttributeTesting.TO_DELETE_VALUES_VALIDATION ) );
		transaction.commit();

		given().get( "/market/attribute/value/{0}", AttributeValueTesting.TO_MARKET_ATTRIBUTE_DELETE_VALIDATION )
				.then()
				.statusCode( 404 );
	}
}
