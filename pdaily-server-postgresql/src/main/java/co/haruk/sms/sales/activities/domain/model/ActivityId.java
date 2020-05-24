package co.haruk.sms.sales.activities.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ActivityId extends Identity {

	protected ActivityId() {
	}

	private ActivityId(String id) {
		super( id );
	}

	private ActivityId(UUID id) {
		super( id );
	}

	public static ActivityId ofNotNull(String id) {
		return new ActivityId( id );
	}

	public static ActivityId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ActivityId( id );
	}

	public static ActivityId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ActivityId( id );
	}

	public static ActivityId generateNew() {
		return of( UUID.randomUUID() );
	}
}
