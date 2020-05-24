package co.haruk.sms.business.structure.subsidiary.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.customer.holding.infrastructure.persistence.HoldingCompanyRepository;
import co.haruk.sms.business.structure.customer.infrastructure.persistence.CustomerRepository;
import co.haruk.sms.business.structure.subsidiary.infrastructure.persistence.SubsidiaryRepository;
import co.haruk.sms.business.structure.subsidiary.salesoffice.infrastructure.persistence.SalesOfficeRepository;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;

/**
 * @author andres2508 on 19/11/19
 **/
@Dependent
public class SubsidiaryValidator {
	@Inject
	SubsidiaryRepository repository;
	@Inject
	SalesOfficeRepository officeRepository;
	@Inject
	SalesRepRepository salesRepRepository;
	@Inject
	CustomerRepository customerRepository;
	@Inject
	HoldingCompanyRepository holdingCompanyRepository;

	public void validate(Subsidiary subsidiary) {
		failIfDuplicatedName( subsidiary );
		if ( subsidiary.reference() != null ) {
			failIfDuplicatedReference( subsidiary );
		}
	}

	private void failIfDuplicatedReference(Subsidiary subsidiary) {
		Optional<Subsidiary> found = repository.findByReference( subsidiary.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !subsidiary.isPersistent() || !existent.equals( subsidiary );
			if ( mustFail ) {
				throw new DuplicatedRecordException( subsidiary.reference().text() );
			}
		}
	}

	private void failIfDuplicatedName(Subsidiary subsidiary) {
		Optional<Subsidiary> found = repository.findByName( subsidiary.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !subsidiary.isPersistent() || !existent.equals( subsidiary );
			if ( mustFail ) {
				throw new DuplicatedRecordException( subsidiary.name().text() );
			}
		}
	}

	public void checkBeforeDelete(SubsidiaryId subsidiaryId) {
		boolean hasHoldings = holdingCompanyRepository.existsAnyForSubsidiary( subsidiaryId );
		check( !hasHoldings, "No se puede eliminar la filial, tiene grupos empresariales asignados" );
		boolean hasCustomers = customerRepository.existsAnyForSubsidiary( subsidiaryId );
		check( !hasCustomers, "No se puede eliminar la filial, tiene clientes asignados" );
		boolean hasOffices = officeRepository.existsAnyForSubsidiary( subsidiaryId );
		check( !hasOffices, "No se puede eliminar la filial, tiene oficinas asignadas" );
		boolean hasSalesReps = salesRepRepository.existsAnyForSubsidiary( subsidiaryId );
		check( !hasSalesReps, "No se puede eliminar la filial, tiene reps. de ventas asignados" );
	}
}
