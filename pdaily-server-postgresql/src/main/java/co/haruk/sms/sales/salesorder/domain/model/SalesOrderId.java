package co.haruk.sms.sales.salesorder.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class SalesOrderId extends Identity {

	protected SalesOrderId() {
	}

	private SalesOrderId(String id) {
		super( id );
	}

	private SalesOrderId(UUID id) {
		super( id );
	}

	public static SalesOrderId ofNotNull(String id) {
		return new SalesOrderId( id );
	}

	public static SalesOrderId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new SalesOrderId( id );
	}

	public static SalesOrderId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new SalesOrderId( id );
	}

	public static SalesOrderId generateNew() {
		return of( UUID.randomUUID() );
	}
}
