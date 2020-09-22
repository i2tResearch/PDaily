package co.icesi.pdaily.clinical.base.routines.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class RoutineTypeId extends Identity {
	protected RoutineTypeId() {
	}

	private RoutineTypeId(String id) {
		super( id );
	}

	private RoutineTypeId(UUID id) {
		super( id );
	}

	public static RoutineTypeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new RoutineTypeId( id );
	}

	public static RoutineTypeId ofNotNull(String id) {
		return new RoutineTypeId( id );
	}

	public static RoutineTypeId generateNew() {
		return new RoutineTypeId( UUID.randomUUID() );
	}
}
