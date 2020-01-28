package co.icesi.pdaily.sales.activities.task.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.sales.activities.task.domain.model.Task;
import co.icesi.pdaily.sales.activities.task.domain.model.TaskId;

public class TaskDTO {
	public String id;
	public String name;

	protected TaskDTO() {
	}

	private TaskDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static TaskDTO of(String id, String name) {
		return new TaskDTO( id, name );
	}

	public static TaskDTO of(Task task) {
		return new TaskDTO( task.id().text(), task.name().text() );
	}

	public Task toTask() {
		return Task.of(
				TaskId.of( id ),
				PlainName.of( name )
		);
	}
}
