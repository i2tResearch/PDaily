package co.haruk.sms.market.measurement.attribute.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetail;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;

@Dependent
public class MarketAttributeValidator {
	@Inject
	MarketAttributeRepository repository;
	@Inject
	AttributeValueRepository valueRepository;

	public void validate(MarketAttribute marketAttribute) {
		failIfDuplicatedLabel( marketAttribute );
		if ( DataType.isListType( marketAttribute.dataType() ) ) {
			listValidate( marketAttribute );
		}
	}

	public void validateBeforeUpdate(MarketAttribute actual, MarketAttribute toUpdated) {
		final var hasItems = valueRepository.existsForMarketAttribute( actual.id() );
		if ( hasItems && (!actual.dataType().equals( toUpdated.dataType() ) ||
				!actual.subjectType().equals( toUpdated.subjectType() )) ) {
			throw new IllegalStateException(
					"El atributo tiene valores asignados, solo se puede cambiar el nombre"
			);
		}
	}

	private void failIfDuplicatedLabel(MarketAttribute marketAttribute) {
		var found = repository.findByLabel(
				marketAttribute.businessId(),
				marketAttribute.subjectType(), marketAttribute.label()
		);
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !marketAttribute.isPersistent() || !existent.equals( marketAttribute );
			if ( mustFail ) {
				throw new DuplicatedRecordException( marketAttribute.label() );
			}
		}
	}

	private void listValidate(MarketAttribute marketAttribute) {
		for ( PickListDetail detail : marketAttribute.pickListDetails() ) {
			failIfDuplicatedListValue( detail );
		}
	}

	private void failIfDuplicatedListValue(PickListDetail pickList) {
		var found = repository.findByListValue( pickList.listValue(), pickList.marketAttribute().id() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !pickList.isPersistent() || !existent.id().equals( pickList.id() );
			if ( mustFail ) {
				throw new DuplicatedRecordException( pickList.listValue() );
			}
		}
	}
}
