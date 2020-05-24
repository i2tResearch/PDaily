package co.haruk.sms.business.structure.subsidiary.salesoffice.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.subsidiary.salesoffice.infrastructure.persistence.SalesOfficeRepository;

/**
 * @author andres2508 on 21/11/19
 **/
@Dependent
public class SalesOfficeValidator {
	@Inject
	SalesOfficeRepository repository;

	public void validate(SalesOffice office) {
		if ( office.reference() != null ) {
			failIfDuplicatedReference( office );
		}
		failIfDuplicatedName( office );
	}

	private void failIfDuplicatedReference(SalesOffice office) {
		Optional<SalesOffice> found = repository.findByReference( office.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !office.isPersistent() || !existent.equals( office );
			if ( mustFail ) {
				throw new DuplicatedRecordException( office.reference() );
			}
		}
	}

	private void failIfDuplicatedName(SalesOffice office) {
		Optional<SalesOffice> found = repository.findByName( office.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !office.isPersistent() || !existent.equals( office );
			if ( mustFail ) {
				throw new DuplicatedRecordException( office.name() );
			}
		}
	}
}
