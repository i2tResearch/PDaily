package co.haruk.sms.sales.salesorder.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;
import co.haruk.sms.sales.salesorder.domain.model.SalesOrderId;
import co.haruk.sms.sales.salesorder.infrastructure.persistence.SalesOrderRepository;

@Dependent
public class SalesOrderViewBuilder {
	@Inject
	SalesOrderRepository repository;
	@Inject
	SalesRepRepository salesRepository;

	public SalesOrderView buildSalesOrder(SalesOrderId id) {
		requireNonNull( id );
		SalesOrderView found = repository.findSalesOrderAsReadView( id );
		SalesRepReadView salesRep = salesRepository.findOrFailAsRepView( SalesRepId.ofNotNull( found.salesRepId ) );
		List<OrderDetailView> orderDetails = repository.findOrderDetailsAsReadView( id );

		found.salesRepName = salesRep.fullName;
		found.orderDetails = orderDetails;

		return found;
	}

}
