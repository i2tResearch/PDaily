package co.haruk.sms.market.measurement.attribute.value.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;
import co.haruk.sms.market.measurement.attribute.value.domain.model.validators.ValidatorFactory;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;

@Dependent
public class AttributeValueValidator {
	@Inject
	MarketAttributeRepository attributeRepository;
	@Inject
	ValidatorFactory validatorFactory;
	@Inject
	AttributeValueRepository repository;

	public void validate(AttributeValue attributeValue) {
		validateValue( attributeValue.value(), attributeValue.attributeId() );
		failIfDuplicatedAttribute( attributeValue );
	}

	private void validateValue(AttributeContent value, MarketAttributeId attributeId) {
		final var dataType = attributeRepository.findOrFail( attributeId ).dataType();
		final var validator = validatorFactory.validatorFor( dataType );
		if ( validator != null ) {
			validator.validateValue( value, attributeId );
		}
	}

	private void failIfDuplicatedAttribute(AttributeValue attributeValue) {
		var found = repository.findByAttributeAndSubject( attributeValue.attributeId(), attributeValue.subjectId() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !attributeValue.isPersistent() || !existent.equals( attributeValue );
			if ( mustFail ) {
				throw new DuplicatedRecordException( attributeValue.attributeId() );
			}
		}
	}
}
