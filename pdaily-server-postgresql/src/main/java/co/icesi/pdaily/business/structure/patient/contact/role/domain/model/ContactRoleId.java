package co.icesi.pdaily.business.structure.patient.contact.role.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ContactRoleId extends Identity {

	protected ContactRoleId() {
	}

	private ContactRoleId(String id) {
		super( id );
	}

	private ContactRoleId(UUID id) {
		super( id );
	}

	public static ContactRoleId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ContactRoleId( id );
	}

	public static ContactRoleId ofNotNull(String id) {
		return new ContactRoleId( id );
	}

	public static ContactRoleId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ContactRoleId( id );
	}

	public static ContactRoleId generateNew() {
		return of( UUID.randomUUID() );
	}
}
