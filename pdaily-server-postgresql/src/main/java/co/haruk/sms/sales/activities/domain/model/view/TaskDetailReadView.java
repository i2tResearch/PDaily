package co.haruk.sms.sales.activities.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class TaskDetailReadView implements Serializable {
	public String id;
	public String taskId;
	public String taskName;
	public String detail;

	protected TaskDetailReadView() {
	}

	public TaskDetailReadView(
			UUID id,
			UUID taskId,
			String taskName,
			String detail) {
		this.id = id.toString();
		this.taskId = taskId.toString();
		this.taskName = taskName;
		this.detail = detail;
	}
}
