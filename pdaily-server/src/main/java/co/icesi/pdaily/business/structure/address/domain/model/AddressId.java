package co.icesi.pdaily.business.structure.address.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 2/12/19
 **/
@Embeddable
public class AddressId extends Identity {

	protected AddressId() {
	}

	private AddressId(String id) {
		super( id );
	}

	private AddressId(UUID id) {
		super( id );
	}

	public static AddressId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new AddressId( id );
	}

	public static AddressId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new AddressId( id );
	}

	public static AddressId ofNotNull(String id) {
		return new AddressId( id );
	}

	public static AddressId generateNew() {
		return of( UUID.randomUUID() );
	}
}
