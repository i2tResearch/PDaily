package co.haruk.sms.clinical.base.levodopa.type.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class LevodopaTypeId extends Identity {

	protected LevodopaTypeId() {
	}

	private LevodopaTypeId(String id) {
		super( id );
	}

	private LevodopaTypeId(UUID id) {
		super( id );
	}

	public static LevodopaTypeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new LevodopaTypeId( id );
	}

	public static LevodopaTypeId ofNotNull(String id) {
		return new LevodopaTypeId( id );
	}

	public static LevodopaTypeId generateNew() {
		return new LevodopaTypeId( UUID.randomUUID() );
	}
}
