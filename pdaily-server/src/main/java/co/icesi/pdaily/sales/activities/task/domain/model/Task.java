package co.icesi.pdaily.sales.activities.task.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "sales_activities_tasks")
@NamedQuery(name = Task.findByName, query = "SELECT t FROM Task t WHERE t.tenant = :company AND UPPER(t.name) = UPPER(:name)")
public class Task extends PdailyTenantEntity<TaskId> {
	private static final String PREFIX = "Task.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private TaskId id;
	@Embedded
	private PlainName name;

	protected Task() {
	}

	private Task(TaskId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static Task of(TaskId id, PlainName name) {
		return new Task( id, name );
	}

	@Override
	public TaskId id() {
		return id;
	}

	@Override
	public void setId(TaskId id) {
		this.id = id;
	}

	public PlainName name() {
		return this.name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

}
