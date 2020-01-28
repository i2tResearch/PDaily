package co.icesi.pdaily.sales.activities.app;

import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.details.TaskDetail;
import co.icesi.pdaily.sales.activities.domain.model.details.TaskDetailId;
import co.icesi.pdaily.sales.activities.task.domain.model.TaskId;

public final class TaskDetailDTO {
	public String id;
	public String taskId;
	public String detail;

	protected TaskDetailDTO() {
	}

	private TaskDetailDTO(String id, String taskId, String detail) {
		this.id = id;
		this.taskId = taskId;
		this.detail = detail;
	}

	public static TaskDetailDTO of(String id, String taskId, String detail) {
		return new TaskDetailDTO( id, taskId, detail );
	}

	public TaskDetail toTaskDetail(Activity activity) {
		TaskDetailId taskDetailId = id == null ? TaskDetailId.generateNew() : TaskDetailId.ofNotNull( id );
		return TaskDetail.of(
				activity,
				taskDetailId,
				TaskId.ofNotNull( taskId ),
				Reference.of( detail )
		);
	}
}
