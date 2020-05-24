package co.haruk.sms.market.measurement.attribute.picklist;

import static co.haruk.core.domain.model.text.StringOps.removeSurrounding;
import static co.haruk.sms.market.measurement.attribute.MarketAttributeTesting.MARKET_ATTRIBUTE_TO_MULP_LIST_SERVICE;
import static co.haruk.sms.market.measurement.attribute.MarketAttributeTesting.MARKET_ATTRIBUTE_TO_SING_LIST_SERVICE;
import static co.haruk.sms.market.measurement.attribute.picklist.PickListTesting.MULTIPLE_LIST_OPTION_A;
import static co.haruk.sms.market.measurement.attribute.picklist.PickListTesting.SINGLE_LIST_OPTION_A;
import static co.haruk.sms.market.measurement.attribute.picklist.PickListTesting.SINGLE_LIST_OPTION_B;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.haruk.core.testing.data.IDataSet;
import co.haruk.core.testing.data.IDataSetDependent;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.tenancy.TenantId;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailService;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;
import co.haruk.sms.subscription.account.AccountTesting;
import co.haruk.testing.DataSets;
import co.haruk.testing.SMSTest;

@SMSTest
@Tag("cdi")
@DisplayName("PickList Service Test")
public class PickListServiceIT implements IDataSetDependent, ICDIContainerDependent {
	@Inject
	PickListDetailService service;
	@Inject
	AttributeValueRepository valueRepository;

	@BeforeAll
	public static void initialize() {
		HarukSession.setCurrentTenant( TenantId.of( AccountTesting.ACCOUNT_ID ) );
	}

	@Override
	public List<IDataSet> dataSets() {
		return List.of( DataSets.PICK_LIST_SERVICE );
	}

	@Test
	@DisplayName("Test remove list value in multiple list")
	public void removeMultipleList(UserTransaction transaction) throws Exception {
		final var removed = PickListDetailId.ofNotNull( MULTIPLE_LIST_OPTION_A );
		service.deletePickListOccurrences( removed );
		transaction.commit();

		final var result = valueRepository.findByMarketAttribute(
				MarketAttributeId.ofNotNull( MARKET_ATTRIBUTE_TO_MULP_LIST_SERVICE )
		);

		Assertions.assertEquals( 2, result.size() );
		for ( AttributeValue attributeValue : result ) {
			Assertions.assertFalse( attributeValue.value().contains( removed ) );
		}
	}

	@Test
	@DisplayName("Test remove list value in single list")
	public void removeSingleList(UserTransaction transaction) throws Exception {
		service.deletePickListOccurrences( PickListDetailId.ofNotNull( SINGLE_LIST_OPTION_A ) );
		transaction.commit();

		final var result = valueRepository.findByMarketAttribute(
				MarketAttributeId.ofNotNull( MARKET_ATTRIBUTE_TO_SING_LIST_SERVICE )
		);

		Assertions.assertEquals( 1, result.size() );
		Assertions.assertEquals( SINGLE_LIST_OPTION_B, removeSurrounding( result.get( 0 ).value().value(), "\"" ) );
	}
}
