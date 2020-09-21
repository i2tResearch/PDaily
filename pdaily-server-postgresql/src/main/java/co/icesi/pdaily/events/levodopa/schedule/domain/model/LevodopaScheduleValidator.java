package co.icesi.pdaily.events.levodopa.schedule.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.events.levodopa.schedule.infrastructure.persistence.LevodopaScheduleRepository;

@Dependent
public class LevodopaScheduleValidator {
	@Inject
	LevodopaScheduleRepository repository;

	public void validate(LevodopaSchedule schedule) {
		failIfDuplicatedSchedule( schedule );
	}

	private void failIfDuplicatedSchedule(LevodopaSchedule schedule) {
		var found = repository.findBySchedule( schedule.schedule() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !schedule.isPersistent() || !existent.equals( schedule );
			if ( mustFail ) {
				throw new DuplicatedRecordException( schedule.schedule().dateExpression() );
			}
		}
	}
}
