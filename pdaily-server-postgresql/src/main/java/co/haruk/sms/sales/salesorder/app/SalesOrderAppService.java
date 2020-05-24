package co.haruk.sms.sales.salesorder.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.domain.model.events.EventBus;
import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrder;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderCreatedEvent;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderId;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderUpdateEvent;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderViewBuilder;
import co.haruk.sms.sales.salesorder.infrastructure.persistence.SalesOrderRepository;

@ApplicationScoped
public class SalesOrderAppService {
	@Inject
	SalesOrderRepository repository;
	@Inject
	SalesOrderViewBuilder builder;
	@Inject
	UserTransaction transaction;

	public List<SalesOrderView> findAllBySalesRep(String salesRepId) {
		return repository.findAllBySalesRep( SalesRepId.ofNotNull( salesRepId ) );
	}

	public SalesOrderView findSalesOrderAsReadView(String id) {
		return builder.buildSalesOrder( SalesOrderId.of( id ) );
	}

	public SalesOrderView saveSalesOrder(SalesOrderDTO dto) {
		var changed = dto.toSalesOrder();
		try {
			SalesOrder saved;
			transaction.begin();
			final var isUpdate = changed.isPersistent();
			UTCDateTime updateDate = null;
			if ( changed.isPersistent() ) {
				SalesOrder original = repository.findOrFail( changed.id() );
				updateDate = original.orderDate();
				original.updateFrom( changed );
				saved = repository.update( original );
			} else {
				changed.setId( SalesOrderId.generateNew() );
				saved = repository.create( changed );
			}
			transaction.commit();
			final var event = isUpdate ? SalesOrderUpdateEvent.of( saved.id(), saved.tenantId(), updateDate )
					: SalesOrderCreatedEvent.of( saved.id(), saved.tenantId() );
			EventBus.fireAsyncEvent( event );
			return builder.buildSalesOrder( saved.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	@Transactional
	public void deleteSalesOrder(String id) {
		repository.delete( SalesOrderId.of( id ) );
	}

	public boolean existsOrderForCustomerInRange(CustomerId customerId, SalesRepId salesRepId, UTCDateTime startDate,
			UTCDateTime endDate) {
		return repository.existsOrderForCustomerInRange( customerId, salesRepId, startDate, endDate );
	}

	public SalesOrderDTO findById(String salesOrderId) {
		final var found = repository.findOrFail( SalesOrderId.ofNotNull( salesOrderId ) );
		return SalesOrderDTO.of( found );
	}
}
