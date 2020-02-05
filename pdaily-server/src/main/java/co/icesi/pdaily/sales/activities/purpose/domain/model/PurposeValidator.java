package co.icesi.pdaily.sales.activities.purpose.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.sales.activities.purpose.infrastructure.persistence.PurposeRepository;

@Dependent
public class PurposeValidator {
	@Inject
	PurposeRepository repository;

	public void validate(Purpose purpose) {
		failIfDuplicatedByName( purpose );
	}

	private void failIfDuplicatedByName(Purpose purpose) {
		final Optional<Purpose> found = repository.findByName( purpose.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !purpose.isPersistent() || !existent.equals( purpose );
			if ( mustFail ) {
				throw new DuplicatedRecordException( purpose.name() );
			}
		}
	}
}
