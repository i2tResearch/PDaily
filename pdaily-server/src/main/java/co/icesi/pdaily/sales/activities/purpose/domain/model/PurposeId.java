package co.icesi.pdaily.sales.activities.purpose.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class PurposeId extends Identity {

	protected PurposeId() {
	}

	private PurposeId(String id) {
		super( id );
	}

	private PurposeId(UUID id) {
		super( id );
	}

	public static PurposeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new PurposeId( id );
	}

	public static PurposeId ofNotNull(String id) {
		return new PurposeId( id );
	}

	public static PurposeId generateNew() {
		return new PurposeId( UUID.randomUUID() );
	}
}
