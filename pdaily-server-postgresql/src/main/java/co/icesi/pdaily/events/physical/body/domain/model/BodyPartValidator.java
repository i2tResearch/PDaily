package co.icesi.pdaily.events.physical.body.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.events.physical.body.infrastructure.repository.BodyPartRepository;

@Dependent
public class BodyPartValidator {
	@Inject
	BodyPartRepository repository;

	public void validate(BodyPart bodyPart) {
		failIfDuplicatedByName( bodyPart );
	}

	private void failIfDuplicatedByName(BodyPart bodyPart) {
		final Optional<BodyPart> found = repository.findByName( bodyPart.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !bodyPart.isPersistent() || !existent.equals( bodyPart );
			if ( mustFail ) {
				throw new DuplicatedRecordException( bodyPart.name() );
			}
		}
	}
}
