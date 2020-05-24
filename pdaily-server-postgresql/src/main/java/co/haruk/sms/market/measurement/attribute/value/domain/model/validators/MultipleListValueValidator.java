package co.haruk.sms.market.measurement.attribute.value.domain.model.validators;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;
import co.haruk.sms.market.measurement.attribute.value.domain.model.processors.MultipleListValueProcessor;

public class MultipleListValueValidator implements ValueValidator {

	MarketAttributeRepository repository;

	public MultipleListValueValidator(MarketAttributeRepository appService) {
		this.repository = appService;
	}

	@Override
	public void validateValue(AttributeContent value, MarketAttributeId attributeId) {
		final var processor = MultipleListValueProcessor.of( value );
		if ( processor.isEmpty() ) {
			throw new IllegalArgumentException( "No se permite almacenar un atributo tipo lista sin valores." );
		}
		for ( String pickId : processor.content() ) {
			final var exists = repository.existsAnyPickListValueWith( PickListDetailId.ofNotNull( pickId.trim() ) );
			if ( !exists ) {
				throw new EntityNotFoundException( pickId );
			}
		}
	}
}
