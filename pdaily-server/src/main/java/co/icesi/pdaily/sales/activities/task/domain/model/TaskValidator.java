package co.icesi.pdaily.sales.activities.task.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.sales.activities.task.infrastructure.persistence.TaskRepository;

@Dependent
public class TaskValidator {
	@Inject
	TaskRepository repository;

	public void validate(Task task) {
		failIfDuplicatedByName( task );
	}

	private void failIfDuplicatedByName(Task task) {
		final Optional<Task> found = repository.findByName( task.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !task.isPersistent() || !existent.equals( task );
			if ( mustFail ) {
				throw new DuplicatedRecordException( task.name() );
			}
		}
	}
}
