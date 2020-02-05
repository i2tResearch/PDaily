package co.icesi.pdaily.events.physical.domain.model.detail;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class BodyPartDetailId extends Identity {
	protected BodyPartDetailId() {
	}

	private BodyPartDetailId(String id) {
		super( id );
	}

	private BodyPartDetailId(UUID id) {
		super( id );
	}

	public static BodyPartDetailId ofNotNull(String id) {
		return new BodyPartDetailId( id );
	}

	public static BodyPartDetailId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new BodyPartDetailId( id );
	}

	public static BodyPartDetailId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new BodyPartDetailId( id );
	}

	public static BodyPartDetailId generateNew() {
		return of( UUID.randomUUID() );
	}
}
