package co.haruk.sms.business.structure.subsidiary.salesrep.domain.model;

import static co.haruk.core.domain.model.guards.Guards.checkIsNotNull;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 25/11/19
 **/
@Embeddable
public class SalesRepId extends Identity {
	protected SalesRepId() {
	}

	private SalesRepId(String id) {
		super( id );
	}

	private SalesRepId(UUID id) {
		super( id );
	}

	public static SalesRepId of(Identity model) {
		checkIsNotNull( model, "El identificador del rep. de ventas es requerido." );
		return new SalesRepId( model.uuidValue() );
	}

	public static SalesRepId ofNotNull(String id) {
		return new SalesRepId( id );
	}
}
