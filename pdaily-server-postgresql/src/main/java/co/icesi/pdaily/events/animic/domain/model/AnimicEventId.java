package co.icesi.pdaily.events.animic.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class AnimicEventId extends Identity {
	protected AnimicEventId() {
	}

	private AnimicEventId(String id) {
		super( id );
	}

	private AnimicEventId(UUID id) {
		super( id );
	}

	public static AnimicEventId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new AnimicEventId( id );
	}

	public static AnimicEventId ofNotNull(String id) {
		return new AnimicEventId( id );
	}

	public static AnimicEventId generateNew() {
		return new AnimicEventId( UUID.randomUUID() );
	}
}
