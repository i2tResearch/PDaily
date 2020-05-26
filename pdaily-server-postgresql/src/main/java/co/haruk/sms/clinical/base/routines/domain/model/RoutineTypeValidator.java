package co.haruk.sms.clinical.base.routines.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.clinical.base.routines.infrastructure.persistence.RoutineTypeRepository;

@Dependent
public class RoutineTypeValidator {
	@Inject
	RoutineTypeRepository repository;

	public void validate(RoutineType routineType) {
		failIfDuplicatedByName( routineType );
	}

	private void failIfDuplicatedByName(RoutineType routineType) {
		final Optional<RoutineType> found = repository.findByLabel( routineType.label() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !routineType.isPersistent() || !existent.equals( routineType );
			if ( mustFail ) {
				throw new DuplicatedRecordException( routineType.label() );
			}
		}
	}
}
