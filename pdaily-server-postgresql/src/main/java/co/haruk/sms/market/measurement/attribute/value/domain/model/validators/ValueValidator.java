package co.haruk.sms.market.measurement.attribute.value.domain.model.validators;

import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;

public interface ValueValidator {

	void validateValue(AttributeContent value, MarketAttributeId attributeId);
}
