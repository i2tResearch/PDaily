package co.haruk.sms.sales.activities.task.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.sales.activities.infrastructure.persistence.ActivityRepository;
import co.haruk.sms.sales.activities.task.infrastructure.persistence.TaskRepository;

@Dependent
public class TaskValidator {
	@Inject
	TaskRepository repository;
	@Inject
	ActivityRepository activityRepository;

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

	public void checkBeforeDelete(TaskId taskId) {
		boolean hasTasks = activityRepository.existsForTask( taskId );
		check( !hasTasks, "No se puede eliminar la tarea, tiene actividades asignadas" );
	}
}
