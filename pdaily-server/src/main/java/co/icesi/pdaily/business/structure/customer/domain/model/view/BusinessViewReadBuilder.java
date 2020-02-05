package co.icesi.pdaily.business.structure.customer.domain.model.view;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.icesi.pdaily.business.structure.customer.domain.model.BusinessViewId;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.customer.infrastructure.persistence.CustomerRepository;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;

/**
 * @author cristhiank on 26/12/19
 **/
@Dependent
public class BusinessViewReadBuilder {
	@Inject
	CustomerRepository repository;
	@Inject
	SalesRepRepository salesRepRepository;

	public CustomerBusinessReadView buildFor(BusinessViewId id) {
		final var view = repository.findBusinessReadView( id );
		final var salesRep = salesRepRepository.findOrFailAsRepView( SalesRepId.ofNotNull( view.salesRepId ) );
		view.salesRepName = salesRep.fullName;
		return view;
	}

	public List<CustomerBusinessReadView> findAllFor(CustomerId customerId) {
		final var views = repository.findAllBusinessReadViewFor( customerId );
		for ( CustomerBusinessReadView view : views ) {
			final var salesRep = salesRepRepository.findOrFailAsRepView( SalesRepId.ofNotNull( view.salesRepId ) );
			view.salesRepName = salesRep.fullName;
		}
		return views;
	}
}
