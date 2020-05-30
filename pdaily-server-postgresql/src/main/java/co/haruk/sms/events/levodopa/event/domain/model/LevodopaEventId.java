package co.haruk.sms.events.levodopa.event.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class LevodopaEventId extends Identity {

	protected LevodopaEventId() {
	}

	private LevodopaEventId(String id) {
		super( id );
	}

	private LevodopaEventId(UUID id) {
		super( id );
	}

	public static LevodopaEventId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new LevodopaEventId( id );
	}

	public static LevodopaEventId ofNotNull(String id) {
		return new LevodopaEventId( id );
	}

	public static LevodopaEventId generateNew() {
		return new LevodopaEventId( UUID.randomUUID() );
	}
}
