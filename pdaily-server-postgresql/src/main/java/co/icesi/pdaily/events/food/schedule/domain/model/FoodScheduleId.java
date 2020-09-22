package co.icesi.pdaily.events.food.schedule.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class FoodScheduleId extends Identity {
	protected FoodScheduleId() {
	}

	private FoodScheduleId(String id) {
		super( id );
	}

	private FoodScheduleId(UUID id) {
		super( id );
	}

	public static FoodScheduleId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new FoodScheduleId( id );
	}

	public static FoodScheduleId ofNotNull(String id) {
		return new FoodScheduleId( id );
	}

	public static FoodScheduleId generateNew() {
		return new FoodScheduleId( UUID.randomUUID() );
	}
}
