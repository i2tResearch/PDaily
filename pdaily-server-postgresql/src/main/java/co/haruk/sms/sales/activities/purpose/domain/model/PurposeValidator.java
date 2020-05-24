package co.haruk.sms.sales.activities.purpose.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.activities.purpose.infrastructure.persistence.PurposeRepository;

@Dependent
public class PurposeValidator {
	@Inject
	PurposeRepository repository;
	@Inject
	ActivityRepository activityRepository;

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

	public void checkBeforeDelete(PurposeId purposeId) {
		boolean hasPurposes = activityRepository.existsForPurpose( purposeId );
		check( !hasPurposes, "No se puede eliminar el proposito de visita, tiene actividades asignadas" );
	}
}
