package co.haruk.sms.market.measurement.attribute.value.domain.model.validators;

import static co.haruk.core.domain.model.guards.Guards.require;

import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;

public class IntegerValueValidator implements ValueValidator {
	private static final String VALID_REGEX = "^[0-9]*$";

	@Override
	public void validateValue(AttributeContent value, MarketAttributeId attributeId) {
		final var changed = value.value();
		require(
				changed.matches( VALID_REGEX ),
				() -> String.format( "La opcion %s no es un valor numerico", changed )
		);
	}
}
