package co.icesi.pdaily.sales.activities.task.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class TaskId extends Identity {

	protected TaskId() {
	}

	private TaskId(String id) {
		super( id );
	}

	private TaskId(UUID id) {
		super( id );
	}

	public static TaskId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new TaskId( id );
	}

	public static TaskId ofNotNull(String id) {
		return new TaskId( id );
	}

	public static TaskId generateNew() {
		return new TaskId( UUID.randomUUID() );
	}
}
