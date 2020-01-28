package co.icesi.pdaily.business.structure.customer.holding.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 1/12/19
 **/
@Embeddable
public class HoldingCompanyId extends Identity {
	protected HoldingCompanyId() {
	}

	private HoldingCompanyId(UUID id) {
		super( id );
	}

	private HoldingCompanyId(String id) {
		super( id );
	}

	public static HoldingCompanyId generateNew() {
		return of( UUID.randomUUID() );
	}

	public static HoldingCompanyId of(UUID id) {
		return new HoldingCompanyId( id );
	}

	public static HoldingCompanyId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new HoldingCompanyId( id );
	}

	public static HoldingCompanyId ofNotNull(String id) {
		return new HoldingCompanyId( id );
	}
}
