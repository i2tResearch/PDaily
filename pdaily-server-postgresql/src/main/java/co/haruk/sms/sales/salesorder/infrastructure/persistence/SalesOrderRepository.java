package co.haruk.sms.sales.salesorder.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrder;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderId;
import co.haruk.sms.sales.salesorder.domain.model.details.OrderDetail;
import co.haruk.sms.sales.salesorder.domain.model.view.OrderDetailView;
import co.haruk.sms.sales.salesorder.domain.model.view.SalesOrderView;

@ApplicationScoped
public class SalesOrderRepository extends JPARepository<SalesOrder> {

	public List<OrderDetailView> findOrderDetailsAsReadView(SalesOrderId id) {
		requireNonNull( id );
		return findOtherWithNamedQuery(
				OrderDetailView.class,
				OrderDetail.findOrderDetailsAsReadView,
				QueryParameter.with( "salesOrder", id ).parameters()
		);
	}

	public List<SalesOrderView> findAllBySalesRep(SalesRepId id) {
		requireNonNull( id );
		return findOtherWithNamedQuery(
				SalesOrderView.class,
				SalesOrder.findBySalesRepAsReadView,
				QueryParameter.with( "salesRepId", id ).parameters()
		);
	}

	public SalesOrderView findSalesOrderAsReadView(SalesOrderId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				SalesOrderView.class,
				SalesOrder.findSalesOrderAsReadView,
				QueryParameter.with( "salesOrder", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public boolean existsOrderForCustomerInRange(CustomerId customerId, SalesRepId salesRepId, UTCDateTime startDate,
			UTCDateTime endDate) {
		return executeAggregateQuery(
				SalesOrder.countOrdersForCustomerInRange,
				QueryParameter.with( "customerId", customerId )
						.and( "salesRepId", salesRepId )
						.and( "endDate", endDate.date() )
						.and( "startDate", startDate.date() ).parameters()
		).intValue() > 0;
	}
}
