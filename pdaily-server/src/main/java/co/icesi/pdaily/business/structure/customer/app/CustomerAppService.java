package co.icesi.pdaily.business.structure.customer.app;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.icesi.pdaily.business.structure.address.domain.model.AddressService;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.ZoneId;
import co.icesi.pdaily.business.structure.customer.domain.model.BusinessViewId;
import co.icesi.pdaily.business.structure.customer.domain.model.Customer;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerBusinessView;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;
import co.icesi.pdaily.business.structure.customer.domain.model.view.BusinessViewReadBuilder;
import co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerBusinessReadView;
import co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerReadView;
import co.icesi.pdaily.business.structure.customer.infrastructure.persistence.CustomerRepository;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;

/**
 * @author cristhiank on 9/12/19
 **/
@ApplicationScoped
public class CustomerAppService {
	@Inject
	AddressService addressService;
	@Inject
	CustomerRepository repository;
	@Inject
	UserTransaction transaction;
	@Inject
	BusinessViewReadBuilder businessViewReadBuilder;

	public List<CustomerReadView> findForSubsidiary(String subsidiaryId) {
		return repository.findForSubsidiaryAsReadView( SubsidiaryId.ofNotNull( subsidiaryId ) );
	}

	public CustomerReadView saveCustomer(CustomerRequestDTO dto) {
		final var changed = dto.toCustomer();
		try {
			transaction.begin();
			Customer saved = saveCustomer( changed );
			saveMainAddressFor( saved, dto );
			transaction.commit();
			return findCustomerAsReadView( saved.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	private Customer saveCustomer(Customer changed) {
		Customer saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			saved = repository.update( original );
		} else {
			changed.setId( CustomerId.generateNew() );
			saved = repository.create( changed );
		}
		return saved;
	}

	private void saveMainAddressFor(Customer saved, CustomerRequestDTO dto) {
		final boolean hasMainAddress = dto.mainAddress != null;
		if ( hasMainAddress ) {
			require( saved.isPersistent(), "No se puede crear la dirección principal, el cliente no ha sido grabado" );
			dto.mainAddress.referencedId = saved.id().text();
			addressService.saveMainForReferenced( dto.mainAddress );
		} else if ( saved.isPersistent() ) {
			addressService.deleteMainIfExistsFor( saved.id() );
		}
	}

	private CustomerReadView findCustomerAsReadView(CustomerId customerId) {
		final var found = repository.findAsReadViewOrFail( customerId );
		found.mainAddress = addressService.findMainAddressFor( customerId ).orElse( null );
		return found;
	}

	public CustomerReadView findById(String id) {
		return findCustomerAsReadView( CustomerId.ofNotNull( id ) );
	}

	@Transactional
	public void deleteById(String id) {
		final CustomerId customerId = CustomerId.ofNotNull( id );
		repository.delete( customerId );
		addressService.deleteAllForReferenced( customerId );
	}

	@Transactional
	public void disableCustomer(String id) {
		final var found = repository.findOrFail( CustomerId.ofNotNull( id ) );
		found.disable();
		repository.update( found );
	}

	@Transactional
	public void enableCustomer(String id) {
		final var found = repository.findOrFail( CustomerId.ofNotNull( id ) );
		found.enable();
		repository.update( found );
	}

	public CustomerBusinessReadView saveBusinessView(String customerId, CustomerBusinessViewRequest viewRequest) {
		final var businessUnitId = BusinessUnitId.ofNotNull( viewRequest.businessId );
		final var salesRepId = SalesRepId.ofNotNull( viewRequest.salesRepId );
		final var zoneId = ZoneId.of( viewRequest.zoneId );
		try {
			transaction.begin();
			final var customer = repository.findOrFail( CustomerId.ofNotNull( customerId ) );
			CustomerBusinessView savedView;
			if ( customer.hasViewForBusiness( businessUnitId ) ) {
				savedView = customer.viewForBusiness( businessUnitId );
				savedView.setSalesRepId( salesRepId );
				savedView.setZoneId( zoneId );
			} else {
				savedView = customer.createViewForBusiness( businessUnitId, salesRepId );
				savedView.setZoneId( zoneId );
			}
			repository.update( customer );
			transaction.commit();
			return businessViewReadBuilder.buildFor( savedView.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	public List<CustomerBusinessReadView> findCustomerBusinessViews(String customerId) {
		return businessViewReadBuilder.findAllFor( CustomerId.ofNotNull( customerId ) );
	}

	@Transactional
	public void deleteBusinessView(String customerId, String businessId) {
		final var viewId = BusinessViewId.of(
				CustomerId.ofNotNull( customerId ),
				BusinessUnitId.ofNotNull( businessId )
		);
		repository.deleteBusinessView( viewId );
	}

	public CustomerBusinessReadView findCustomerBusinessView(String customerId, String businessId) {
		final var viewId = BusinessViewId.of(
				CustomerId.ofNotNull( customerId ),
				BusinessUnitId.ofNotNull( businessId )
		);
		return businessViewReadBuilder.buildFor( viewId );
	}
}
