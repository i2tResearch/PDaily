package co.haruk.sms.market.measurement.attribute.value.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class AttributeValueId extends Identity {

	protected AttributeValueId() {
	}

	private AttributeValueId(String id) {
		super( id );
	}

	private AttributeValueId(UUID id) {
		super( id );
	}

	public static AttributeValueId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new AttributeValueId( id );
	}

	public static AttributeValueId ofNotNull(String id) {
		return new AttributeValueId( id );
	}

	public static AttributeValueId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new AttributeValueId( id );
	}

	public static AttributeValueId generateNew() {
		return of( UUID.randomUUID() );
	}
}
