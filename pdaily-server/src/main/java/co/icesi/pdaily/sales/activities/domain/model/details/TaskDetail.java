package co.icesi.pdaily.sales.activities.domain.model.details;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.*;

import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.task.domain.model.TaskId;

@Entity
@Table(name = "sales_activities_tasks_details")
@NamedQuery(name = TaskDetail.findTaskDetailsAsReadView, query = "SELECT new co.icesi.pdaily.sales.activities.domain.model.view.TaskDetailReadView(t.id.id, t.name.name, a.detail.text)"
		+ " FROM TaskDetail a INNER JOIN Task t ON a.taskId = t.id " +
		" WHERE a.activity.id = :activity")
public class TaskDetail extends PdailyTenantEntity<TaskDetailId> {
	private static final String PREFIX = "TaskDetail.";
	public static final String findTaskDetailsAsReadView = PREFIX + "findTaskDetailsAsReadView";

	@EmbeddedId
	private TaskDetailId id;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "task_id"))
	private TaskId taskId;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "details"))
	private Reference detail;

	protected TaskDetail() {
	}

	private TaskDetail(Activity activity, TaskDetailId id, TaskId taskId, Reference detail) {
		setId( id );
		setTaskId( taskId );
		this.activity = activity;
		setDetail( detail );
	}

	public static TaskDetail of(Activity activity, TaskDetailId id, TaskId taskId, Reference detail) {
		return new TaskDetail( activity, id, taskId, detail );
	}

	public static TaskDetail of(Activity activity, TaskDetailId id, TaskId taskId) {
		return new TaskDetail( activity, id, taskId, null );
	}

	@Override
	public TaskDetailId id() {
		return id;
	}

	@Override
	public void setId(TaskDetailId id) {
		this.id = id;
	}

	public TaskId taskId() {
		return taskId;
	}

	public Reference detail() {
		return this.detail;
	}

	public void setDetail(Reference detail) {
		this.detail = detail;
	}

	public ActivityId activity() {
		return activity.id();
	}

	private void setTaskId(TaskId taskId) {
		this.taskId = requireNonNull( taskId, "La tarea realizada en la actividad es requerida." );
	}

	private void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void removeAssociationWithActivity() {
		setActivity( null );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		TaskDetail that = (TaskDetail) o;
		return Objects.equals( taskId, that.taskId );
	}
}
