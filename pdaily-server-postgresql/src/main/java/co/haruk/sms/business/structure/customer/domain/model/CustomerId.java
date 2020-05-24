package co.haruk.sms.business.structure.customer.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 9/12/19
 **/
@Embeddable
public class CustomerId extends Identity {

	protected CustomerId() {
	}

	private CustomerId(String id) {
		super( id );
	}

	private CustomerId(UUID id) {
		super( id );
	}

	public static CustomerId ofNotNull(String id) {
		return new CustomerId( id );
	}

	public static CustomerId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new CustomerId( id );
	}

	public static CustomerId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new CustomerId( id );
	}

	public static CustomerId generateNew() {
		return of( UUID.randomUUID() );
	}
}
