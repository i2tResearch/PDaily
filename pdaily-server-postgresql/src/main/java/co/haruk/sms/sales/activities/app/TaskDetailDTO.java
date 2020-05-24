package co.haruk.sms.sales.activities.app;

import co.haruk.sms.common.model.Reference;
import co.haruk.sms.sales.activities.domain.model.Activity;
import co.haruk.sms.sales.activities.domain.model.details.TaskDetail;
import co.haruk.sms.sales.activities.domain.model.details.TaskDetailId;
import co.haruk.sms.sales.activities.task.domain.model.TaskId;

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
