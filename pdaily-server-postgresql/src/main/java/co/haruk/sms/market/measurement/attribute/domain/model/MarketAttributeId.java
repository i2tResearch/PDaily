package co.haruk.sms.market.measurement.attribute.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class MarketAttributeId extends Identity {
	protected MarketAttributeId() {
	}

	private MarketAttributeId(String id) {
		super( id );
	}

	private MarketAttributeId(UUID id) {
		super( id );
	}

	public static MarketAttributeId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new MarketAttributeId( id );
	}

	public static MarketAttributeId ofNotNull(String id) {
		return new MarketAttributeId( id );
	}

	public static MarketAttributeId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new MarketAttributeId( id );
	}

	public static MarketAttributeId generateNew() {
		return of( UUID.randomUUID() );
	}
}
