package co.icesi.pdaily.events.physical.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class PhysicalEventId extends Identity {

	protected PhysicalEventId() {
	}

	private PhysicalEventId(String id) {
		super( id );
	}

	private PhysicalEventId(UUID id) {
		super( id );
	}

	public static PhysicalEventId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new PhysicalEventId( id );
	}

	public static PhysicalEventId ofNotNull(String id) {
		return new PhysicalEventId( id );
	}

	public static PhysicalEventId generateNew() {
		return new PhysicalEventId( UUID.randomUUID() );
	}
}
