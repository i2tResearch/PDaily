package co.icesi.pdaily.clinical.base.levodopa.type.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.clinical.base.levodopa.type.infrastructure.persistence.LevodopaTypeRepository;

@Dependent
public class LevodopaTypeValidator {
	@Inject
	LevodopaTypeRepository repository;

	public void validate(LevodopaType injuryType) {
		failIfDuplicatedByName( injuryType );
	}

	private void failIfDuplicatedByName(LevodopaType injuryType) {
		final Optional<LevodopaType> found = repository.findByName( injuryType.label() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !injuryType.isPersistent() || !existent.equals( injuryType );
			if ( mustFail ) {
				throw new DuplicatedRecordException( injuryType.label() );
			}
		}
	}
}
