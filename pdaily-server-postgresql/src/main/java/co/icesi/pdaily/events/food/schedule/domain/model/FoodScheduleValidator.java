package co.icesi.pdaily.events.food.schedule.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.events.food.schedule.infrastructure.persistence.FoodScheduleRepository;

@Dependent
public class FoodScheduleValidator {
	@Inject
	FoodScheduleRepository repository;

	public void validate(FoodSchedule schedule) {
		failIfDuplicatedSchedule( schedule );
	}

	private void failIfDuplicatedSchedule(FoodSchedule schedule) {
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
