package co.haruk.sms.market.measurement.attribute.app;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.events.EventBus;
import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeValidator;
import co.haruk.sms.market.measurement.attribute.domain.model.events.MarketAttributeDeleteEvent;
import co.haruk.sms.market.measurement.attribute.domain.model.events.PickListDeleteEvent;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;
import co.haruk.sms.market.measurement.attribute.domain.model.view.MarketAttributeReadBuilder;
import co.haruk.sms.market.measurement.attribute.domain.model.view.MarketAttributeReadView;
import co.haruk.sms.market.measurement.attribute.infrastructure.persistence.MarketAttributeRepository;

@ApplicationScoped
public class MarketAttributeAppService {
	@Inject
	MarketAttributeRepository repository;
	@Inject
	MarketAttributeValidator validator;
	@Inject
	MarketAttributeReadBuilder builder;
	@Inject
	UserTransaction transaction;

	public MarketAttributeReadView saveMarketAttribute(MarketAttributeDTO dto) {
		final var changed = dto.toMarketAttribute();
		List<PickListDetailId> pickListRemoved = new ArrayList<>();
		try {
			MarketAttribute saved;
			transaction.begin();
			if ( changed.isPersistent() ) {
				var original = repository.findOrFail( changed.id() );
				validator.validateBeforeUpdate( original, changed );
				pickListRemoved = original.updateFrom( changed );
				validator.validate( original );
				saved = repository.update( original );
			} else {
				changed.setId( MarketAttributeId.generateNew() );
				validator.validate( changed );
				saved = repository.create( changed );
			}
			transaction.commit();
			sendRemovedPickListEvents( pickListRemoved );
			return builder.buildFor( saved );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	private void sendRemovedPickListEvents(List<PickListDetailId> pickListRemoved) {
		for ( PickListDetailId listId : pickListRemoved ) {
			EventBus.fireAsyncEvent(
					PickListDeleteEvent.of( listId, HarukSession.currentTenant() )
			);
		}
	}

	public List<MarketAttributeReadView> saveMultipleMarketAttributes(List<MarketAttributeDTO> listDTO) {
		ArrayList<MarketAttributeReadView> result = new ArrayList<>();
		for ( MarketAttributeDTO dto : listDTO ) {
			result.add( saveMarketAttribute( dto ) );
		}
		return result;
	}

	public List<MarketAttributeReadView> findForBusiness(String businessUnit) {
		final var all = repository.findForBusinessUnit( BusinessUnitId.ofNotNull( businessUnit ) );
		return StreamUtils.map( all, builder::buildFor );
	}

	public MarketAttributeReadView findOrFail(String id) {
		final var found = repository.findOrFail( MarketAttributeId.ofNotNull( id ) );
		return builder.buildFor( found );
	}

	public void deleteMarketAttribute(String id) {
		try {
			transaction.begin();
			final var marketAttribute = repository.findOrFail( MarketAttributeId.ofNotNull( id ) );
			repository.delete( marketAttribute.id() );
			transaction.commit();
			EventBus.fireAsyncEvent(
					MarketAttributeDeleteEvent.of( marketAttribute.id(), marketAttribute.tenantId() )
			);
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}
}
