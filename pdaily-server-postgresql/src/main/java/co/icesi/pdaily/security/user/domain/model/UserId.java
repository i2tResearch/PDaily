package co.icesi.pdaily.security.user.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 15/11/19
 **/
@Embeddable
public class UserId extends Identity {
	protected UserId() {
	}

	private UserId(String id) {
		super( id );
	}

	private UserId(UUID id) {
		super( id );
	}

	public static UserId ofNotNull(String id) {
		return of( id );
	}

	public static UserId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new UserId( id );
	}

	public static UserId generateNew() {
		return new UserId( UUID.randomUUID() );
	}
}
