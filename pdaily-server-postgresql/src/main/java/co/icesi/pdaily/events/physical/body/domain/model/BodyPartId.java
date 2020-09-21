package co.icesi.pdaily.events.physical.body.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class BodyPartId extends Identity {

	protected BodyPartId() {
	}

	private BodyPartId(String id) {
		super( id );
	}

	private BodyPartId(UUID id) {
		super( id );
	}

	public static BodyPartId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new BodyPartId( id );
	}

	public static BodyPartId ofNotNull(String id) {
		return new BodyPartId( id );
	}

	public static BodyPartId generateNew() {
		return new BodyPartId( UUID.randomUUID() );
	}
}
