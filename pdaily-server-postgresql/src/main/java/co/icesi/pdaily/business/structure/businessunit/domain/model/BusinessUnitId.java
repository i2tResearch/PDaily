package co.icesi.pdaily.business.structure.businessunit.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class BusinessUnitId extends Identity {
	protected BusinessUnitId() {

	}

	private BusinessUnitId(String id) {
		super( id );
	}

	private BusinessUnitId(UUID id) {
		super( id );
	}

	public static BusinessUnitId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new BusinessUnitId( id );
	}

	public static BusinessUnitId ofNotNull(String id) {
		return new BusinessUnitId( id );
	}

	public static BusinessUnitId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new BusinessUnitId( id );
	}

	public static BusinessUnitId generateNew() {
		return of( UUID.randomUUID() );
	}
}
