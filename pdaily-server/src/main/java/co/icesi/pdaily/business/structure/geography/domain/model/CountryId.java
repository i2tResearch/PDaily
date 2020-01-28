package co.icesi.pdaily.business.structure.geography.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 2/12/19
 **/
@Embeddable
public class CountryId extends Identity {
	protected CountryId() {
	}

	private CountryId(String id) {
		super( id );
	}

	private CountryId(UUID id) {
		super( id );
	}

	public static CountryId generateNew() {
		return of( UUID.randomUUID() );
	}

	public static CountryId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new CountryId( id );
	}

	public static CountryId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new CountryId( id );
	}

	public static CountryId ofNotNull(String id) {
		return new CountryId( id );
	}
}
