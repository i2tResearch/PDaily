package co.haruk.sms.market.measurement.attribute.value.domain.service;

import java.util.logging.Logger;

import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.events.MarketAttributeDeleteEvent;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;

public class AttributeValueService {
	private static final Logger log = Logger.getLogger( AttributeValueService.class.getName() );

	@Inject
	UserTransaction transaction;
	@Inject
	AttributeValueRepository repository;

	public void listenMarketAttributeDelete(@ObservesAsync MarketAttributeDeleteEvent event) {
		HarukSession.runInTenant( event.tenantId(), () -> {
			try {
				transaction.begin();
				markValuesAsDeleted( event.attributeId() );
				transaction.commit();
			} catch (Exception ex) {
				log.severe(
						String.format(
								"Error marcando como eliminado el valor: %s.",
								event.attributeId().text()
						)
				);
				log.severe( ex.getMessage() );
				JTAUtils.rollback( transaction );
			}
		} );
	}

	public void markValuesAsDeleted(MarketAttributeId attributeId) {
		final var list = repository.findByMarketAttribute( attributeId );
		for ( AttributeValue actual : list ) {
			actual.markAsDeleted();
			repository.update( actual );
		}
	}
}
