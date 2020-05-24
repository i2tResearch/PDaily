package co.haruk.sms.sales.activities.domain.model.details;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class TaskDetailId extends Identity {

	protected TaskDetailId() {
	}

	private TaskDetailId(String id) {
		super( id );
	}

	private TaskDetailId(UUID id) {
		super( id );
	}

	public static TaskDetailId ofNotNull(String id) {
		return new TaskDetailId( id );
	}

	public static TaskDetailId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new TaskDetailId( id );
	}

	public static TaskDetailId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new TaskDetailId( id );
	}

	public static TaskDetailId generateNew() {
		return of( UUID.randomUUID() );
	}
}
