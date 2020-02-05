package co.icesi.pdaily.events.physical.injury.type.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.events.physical.injury.type.infrastructure.persistence.InjuryTypeRepository;

@Dependent
public class InjuryTypeValidator {
	@Inject
	InjuryTypeRepository repository;

	public void validate(InjuryType injuryType) {
		failIfDuplicatedByName( injuryType );
	}

	private void failIfDuplicatedByName(InjuryType injuryType) {
		final Optional<InjuryType> found = repository.findByName( injuryType.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !injuryType.isPersistent() || !existent.equals( injuryType );
			if ( mustFail ) {
				throw new DuplicatedRecordException( injuryType.name() );
			}
		}
	}
}
