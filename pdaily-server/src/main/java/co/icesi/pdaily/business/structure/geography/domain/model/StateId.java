package co.icesi.pdaily.business.structure.geography.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 2/12/19
 **/
@Embeddable
public class StateId extends Identity {

	protected StateId() {
	}

	private StateId(String id) {
		super( id );
	}

	private StateId(UUID id) {
		super( id );
	}

	public static StateId generateNew() {
		return of( UUID.randomUUID() );
	}

	public static StateId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new StateId( id );
	}

	public static StateId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new StateId( id );
	}

	public static StateId ofNotNull(String id) {
		return new StateId( id );
	}
}
