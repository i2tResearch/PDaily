package co.haruk.sms.market.measurement.attribute.value.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class SubjectId extends Identity {

	protected SubjectId() {
	}

	private SubjectId(String id) {
		super( id );
	}

	private SubjectId(UUID id) {
		super( id );
	}

	public static SubjectId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new SubjectId( id );
	}

	public static SubjectId ofNotNull(Identity model) {
		return new SubjectId( model.text() );
	}

	public static SubjectId ofNotNull(String id) {
		return new SubjectId( id );
	}

	public static SubjectId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new SubjectId( id );
	}
}
