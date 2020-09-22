package co.icesi.pdaily.events.food.event.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class FoodEventId extends Identity {

	protected FoodEventId() {
	}

	private FoodEventId(String id) {
		super( id );
	}

	private FoodEventId(UUID id) {
		super( id );
	}

	public static FoodEventId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new FoodEventId( id );
	}

	public static FoodEventId ofNotNull(String id) {
		return new FoodEventId( id );
	}

	public static FoodEventId generateNew() {
		return new FoodEventId( UUID.randomUUID() );
	}
}
