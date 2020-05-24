package co.haruk.sms.market.measurement.attribute.value.domain.model.validators;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;

public class SingleListValueValidator implements ValueValidator {
	MarketAttributeRepository repository;

	public SingleListValueValidator(MarketAttributeRepository appService) {
		this.repository = appService;
	}

	@Override
	public void validateValue(AttributeContent value, MarketAttributeId attributeId) {
		final PickListDetailId pickListDetailId = PickListDetailId.ofNotNull( value.asSingleString() );
		final boolean exists = repository.existsAnyPickListValueWith( pickListDetailId );
		if ( !exists ) {
			throw new EntityNotFoundException( value.asSingleString() );
		}
	}
}
