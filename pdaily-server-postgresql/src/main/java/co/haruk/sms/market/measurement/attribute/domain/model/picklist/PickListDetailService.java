package co.haruk.sms.market.measurement.attribute.domain.model.picklist;

import static co.haruk.sms.market.measurement.attribute.domain.model.DataType.isMultipleList;

import java.util.logging.Logger;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.market.measurement.attribute.domain.model.events.PickListDeleteEvent;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.domain.model.processors.MultipleListValueProcessor;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;

public class PickListDetailService {
	private static final Logger log = Logger.getLogger( PickListDetailService.class.getName() );

	@Inject
	UserTransaction transaction;
	@Inject
	AttributeValueRepository valueRepository;
	@Inject
	MarketAttributeRepository repository;

	public void listenPickListDeleteEvent(@ObservesAsync PickListDeleteEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				deletePickListOccurrences( event.pickListId() );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error eliminando la opcion de la lista <%s> del market attribute.",
								event.pickListId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	public void deletePickListOccurrences(PickListDetailId pickListId) {
		final var removed = repository.findPickListBy( pickListId );
		final var answers = valueRepository.findByMarketAttribute( removed.marketAttribute().id() );
		for ( AttributeValue actual : answers ) {
			final var valueAnswer = actual.value();
			if ( valueAnswer.contains( removed.id() ) ) {
				refactorListOcurrencies( removed, actual );
			}
		}
	}

	private void refactorListOcurrencies(PickListDetail removed, AttributeValue currentValue) {
		if ( isMultipleList( removed.marketAttribute().dataType() ) ) {
			final var processor = MultipleListValueProcessor.of( currentValue.value() );
			processor.removeValue( removed.id().text() );
			if ( processor.isEmpty() ) {
				deleteValue( currentValue );
			} else {
				currentValue.setValue( processor.asContent() );
				valueRepository.update( currentValue );
			}
		} else {
			deleteValue( currentValue );
		}
	}

	private void deleteValue(AttributeValue value) {
		log.warning( () -> String.format( "Se eliminara el Attribute Value <%s> por ausencia de respuestas.", value.id() ) );
		valueRepository.delete( value.id() );
	}

}
