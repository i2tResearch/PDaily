package co.icesi.pdaily.business.structure.customer.holding.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.customer.holding.infrastructure.persistence.HoldingCompanyRepository;

/**
 * @author cristhiank on 1/12/19
 **/
@Dependent
public class HoldingCompanyValidator {
	@Inject
	HoldingCompanyRepository repository;

	public void validate(HoldingCompany company) {
		failIfDuplicatedName( company );
	}

	private void failIfDuplicatedName(HoldingCompany company) {
		final Optional<HoldingCompany> found = repository.findByName( company.subsidiaryId(), company.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !company.isPersistent() || !existent.equals( company );
			if ( mustFail ) {
				throw new DuplicatedRecordException( company.name() );
			}
		}
	}
}
