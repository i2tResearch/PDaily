package co.haruk.sms.business.structure.customer.contact.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ContactId extends Identity {
	protected ContactId() {

	}

	private ContactId(String id) {
		super( id );
	}

	private ContactId(UUID id) {
		super( id );
	}

	public static ContactId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ContactId( id );
	}

	public static ContactId ofNotNull(String id) {
		return new ContactId( id );
	}

	public static ContactId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ContactId( id );
	}

	public static ContactId generateNew() {
		return of( UUID.randomUUID() );
	}
}
