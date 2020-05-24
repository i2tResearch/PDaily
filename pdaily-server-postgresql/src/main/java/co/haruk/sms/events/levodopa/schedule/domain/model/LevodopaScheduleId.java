package co.haruk.sms.events.levodopa.schedule.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class LevodopaScheduleId extends Identity {

	protected LevodopaScheduleId() {
	}

	private LevodopaScheduleId(String id) {
		super( id );
	}

	private LevodopaScheduleId(UUID id) {
		super( id );
	}

	public static LevodopaScheduleId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new LevodopaScheduleId( id );
	}

	public static LevodopaScheduleId ofNotNull(String id) {
		return new LevodopaScheduleId( id );
	}

	public static LevodopaScheduleId generateNew() {
		return new LevodopaScheduleId( UUID.randomUUID() );
	}
}
