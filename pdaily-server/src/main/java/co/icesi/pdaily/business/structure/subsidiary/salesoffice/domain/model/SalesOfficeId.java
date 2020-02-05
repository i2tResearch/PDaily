package co.icesi.pdaily.business.structure.subsidiary.salesoffice.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 21/11/19
 **/
@Embeddable
public class SalesOfficeId extends Identity {
	protected SalesOfficeId() {
	}

	private SalesOfficeId(UUID id) {
		super( id );
	}

	private SalesOfficeId(String id) {
		super( id );
	}

	public static SalesOfficeId ofNotNull(String id) {
		return new SalesOfficeId( id );
	}

	public static SalesOfficeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new SalesOfficeId( id );
	}

	public static SalesOfficeId generateNew() {
		return new SalesOfficeId( UUID.randomUUID() );
	}
}
