package co.icesi.pdaily.clinical.base.animic.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class AnimicTypeId extends Identity {
	protected AnimicTypeId() {
	}

	private AnimicTypeId(String id) {
		super( id );
	}

	private AnimicTypeId(UUID id) {
		super( id );
	}

	public static AnimicTypeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new AnimicTypeId( id );
	}

	public static AnimicTypeId ofNotNull(String id) {
		return new AnimicTypeId( id );
	}

	public static AnimicTypeId generateNew() {
		return new AnimicTypeId( UUID.randomUUID() );
	}
}
