package co.icesi.pdaily.sales.activities.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class TaskDetailReadView implements Serializable {
	public String taskId;
	public String taskName;
	public String detail;

	protected TaskDetailReadView() {
	}

	public TaskDetailReadView(
			UUID taskId,
			String taskName,
			String detail) {
		this.taskId = taskId.toString();
		this.taskName = taskName;
		this.detail = detail;
	}
}
