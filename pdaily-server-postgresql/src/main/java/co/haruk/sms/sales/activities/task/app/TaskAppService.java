package co.haruk.sms.sales.activities.task.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.sales.activities.task.domain.model.Task;
import co.haruk.sms.sales.activities.task.domain.model.TaskId;
import co.haruk.sms.sales.activities.task.domain.model.TaskValidator;
import co.haruk.sms.sales.activities.task.infrastructure.persistence.TaskRepository;

@ApplicationScoped
public class TaskAppService {
	@Inject
	TaskValidator validator;
	@Inject
	TaskRepository repository;

	public List<TaskDTO> findAll() {
		List<Task> all = repository.findAll();
		return StreamUtils.map( all, TaskDTO::of );
	}

	@Transactional
	public TaskDTO saveTask(TaskDTO dto) {
		final Task changed = dto.toTask();
		Task saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( TaskId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return TaskDTO.of( saved );
	}

	@Transactional
	public void deleteTask(String id) {
		TaskId taskId = TaskId.ofNotNull( id );
		validator.checkBeforeDelete( taskId );
		repository.delete( taskId );
	}

	public TaskDTO findById(String id) {
		final var found = repository.findOrFail( TaskId.ofNotNull( id ) );
		return TaskDTO.of( found );
	}
}
