package co.haruk.sms.business.structure.geography.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 2/12/19
 **/
@Embeddable
public class CityId extends Identity {
	protected CityId() {
	}

	private CityId(String id) {
		super( id );
	}

	private CityId(UUID id) {
		super( id );
	}

	public static CityId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new CityId( id );
	}

	public static CityId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new CityId( id );
	}

	public static CityId ofNotNull(String id) {
		return new CityId( id );
	}

	public static CityId generateNew() {
		return of( UUID.randomUUID() );
	}
}
