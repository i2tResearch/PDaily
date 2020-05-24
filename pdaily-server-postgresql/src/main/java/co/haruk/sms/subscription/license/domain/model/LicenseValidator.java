package co.haruk.sms.subscription.license.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.subscription.license.infrastructure.persistence.LicenseRepository;

/**
 * @author andres2508 on 15/11/19
 **/
@Dependent
public class LicenseValidator {
	@Inject
	LicenseRepository repository;

	public void validate(License license) {
		failIfDuplicatedName( license );
	}

	private void failIfDuplicatedName(License license) {
		Optional<License> found = repository.findByName( license.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !license.isPersistent() || !license.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( license.name() );
			}
		}
	}
}
