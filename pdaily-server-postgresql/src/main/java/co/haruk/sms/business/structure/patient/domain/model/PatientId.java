package co.haruk.sms.business.structure.patient.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class PatientId extends Identity {

	protected PatientId() {
	}

	private PatientId(String id) {
		super( id );
	}

	private PatientId(UUID id) {
		super( id );
	}

	public static PatientId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new PatientId( id );
	}

	public static PatientId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new PatientId( id );
	}

	public static PatientId ofNotNull(String id) {
		return new PatientId( id );
	}

	public static PatientId generateNew() {
		return of( UUID.randomUUID() );
	}
}
