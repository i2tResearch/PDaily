package co.haruk.sms.market.measurement.attribute.value.domain.model.validators;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.sms.market.measurement.attribute.domain.model.DataType;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;

@Dependent
public class ValidatorFactory {

	@Inject
	MarketAttributeRepository repository;

	protected ValidatorFactory() {
	}

	public ValueValidator validatorFor(DataType dataType) {
		if ( dataType == DataType.NUMBER ) {
			return new IntegerValueValidator();
		} else if ( dataType == DataType.LIST_MULTIPLE_ANS ) {
			return new MultipleListValueValidator( repository );
		} else if ( dataType == DataType.LIST_SINGLE_ANS ) {
			return new SingleListValueValidator( repository );
		} else if ( dataType == DataType.BOOLEAN ) {
			return new BooleanValueValidator();
		}
		return null;
	}
}
