package co.icesi.pdaily.subscription.license.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 15/11/19
 **/
@Embeddable
public class LicenseId extends Identity {

	protected LicenseId() {
	}

	private LicenseId(String id) {
		super( id );
	}

	private LicenseId(UUID id) {
		super( id );
	}

	public static LicenseId ofNotNull(String id) {
		return new LicenseId( id );
	}

	public static LicenseId generateNew() {
		return new LicenseId( UUID.randomUUID() );
	}

	public static LicenseId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new LicenseId( id );
	}
}
