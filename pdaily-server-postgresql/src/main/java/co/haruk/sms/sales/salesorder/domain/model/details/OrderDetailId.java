package co.haruk.sms.sales.salesorder.domain.model.details;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class OrderDetailId extends Identity {
	protected OrderDetailId() {
	}

	private OrderDetailId(String id) {
		super( id );
	}

	private OrderDetailId(UUID id) {
		super( id );
	}

	public static OrderDetailId ofNotNull(String id) {
		return new OrderDetailId( id );
	}

	public static OrderDetailId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new OrderDetailId( id );
	}

	public static OrderDetailId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new OrderDetailId( id );
	}

	public static OrderDetailId generateNew() {
		return of( UUID.randomUUID() );
	}
}
