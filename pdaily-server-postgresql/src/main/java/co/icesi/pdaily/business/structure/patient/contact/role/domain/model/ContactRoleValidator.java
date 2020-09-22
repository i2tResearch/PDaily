package co.icesi.pdaily.business.structure.patient.contact.role.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.patient.contact.role.infrastructure.persistence.ContactRoleRepository;

@Dependent
public class ContactRoleValidator {
	@Inject
	ContactRoleRepository repository;

	public void validate(ContactRole contactRole) {
		failIfDuplicatedName( contactRole );
	}

	private void failIfDuplicatedName(ContactRole contactRole) {
		var found = repository.findByName( contactRole.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !contactRole.isPersistent() || !existent.equals( contactRole );
			if ( mustFail ) {
				throw new DuplicatedRecordException( contactRole.name() );
			}
		}
	}
}
