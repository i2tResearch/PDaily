package co.haruk.sms.sales.salesorder.source.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author cristhiank on 23/12/19
 **/
@Embeddable
public class OrderSourceId extends Identity {

	protected OrderSourceId() {
	}

	private OrderSourceId(String id) {
		super( id );
	}

	private OrderSourceId(UUID id) {
		super( id );
	}

	public static OrderSourceId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new OrderSourceId( id );
	}

	public static OrderSourceId ofNotNull(String id) {
		return new OrderSourceId( id );
	}

	public static OrderSourceId generateNew() {
		return new OrderSourceId( UUID.randomUUID() );
	}
}
