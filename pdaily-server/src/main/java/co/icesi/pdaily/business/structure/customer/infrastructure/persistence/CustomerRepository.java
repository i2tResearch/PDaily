package co.icesi.pdaily.business.structure.customer.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.ZoneId;
import co.icesi.pdaily.business.structure.customer.domain.model.BusinessViewId;
import co.icesi.pdaily.business.structure.customer.domain.model.Customer;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerBusinessView;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerBusinessReadView;
import co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerReadView;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;

/**
 * @author cristhiank on 9/12/19
 **/
@ApplicationScoped
public class CustomerRepository extends JPARepository<Customer> {

	public List<CustomerReadView> findForSubsidiaryAsReadView(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findOtherWithNamedQuery(
				CustomerReadView.class,
				Customer.findForSubsidiaryAsReadView,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public CustomerReadView findAsReadViewOrFail(CustomerId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				CustomerReadView.class,
				Customer.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public boolean existsAnyForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		final int count = executeAggregateQuery(
				Customer.countForSubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		).intValue();
		return count > 0;
	}

	public boolean existsAnyForZone(ZoneId zoneId) {
		requireNonNull( zoneId );
		final int count = executeAggregateQuery(
				CustomerBusinessView.countForZone,
				QueryParameter.with( "zoneId", zoneId ).parameters()
		).intValue();
		return count > 0;
	}

	public CustomerBusinessReadView findBusinessReadView(BusinessViewId viewId) {
		requireNonNull( viewId );
		return findOtherSingleWithNamedQuery(
				CustomerBusinessReadView.class,
				CustomerBusinessView.findByIdAsReadView,
				QueryParameter.with( "viewId", viewId ).parameters()
		).orElseThrow( () -> new EntityNotFoundException( "No se encontró la vista de negocios para el cliente" ) );
	}

	public List<CustomerBusinessReadView> findAllBusinessReadViewFor(CustomerId customerId) {
		requireNonNull( customerId );
		return findOtherWithNamedQuery(
				CustomerBusinessReadView.class,
				CustomerBusinessView.findForCustomerAsReadView,
				QueryParameter.with( "customerId", customerId ).parameters()
		);
	}

	public void deleteBusinessView(BusinessViewId viewId) {
		requireNonNull( viewId );
		final var found = findOrFail( CustomerBusinessView.class, viewId );
		currentEM().remove( found );
	}
}
