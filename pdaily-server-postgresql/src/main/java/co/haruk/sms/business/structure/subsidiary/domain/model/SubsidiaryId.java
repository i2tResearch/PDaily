package co.haruk.sms.business.structure.subsidiary.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 19/11/19
 **/
@Embeddable
public class SubsidiaryId extends Identity {

	protected SubsidiaryId() {
	}

	private SubsidiaryId(String id) {
		super( id );
	}

	private SubsidiaryId(UUID id) {
		super( id );
	}

	public static SubsidiaryId ofNotNull(String id) {
		return new SubsidiaryId( id );
	}

	public static SubsidiaryId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new SubsidiaryId( id );
	}

	public static SubsidiaryId generateNew() {
		return of( UUID.randomUUID() );
	}

	public static SubsidiaryId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new SubsidiaryId( id );
	}
}
