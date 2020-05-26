package co.haruk.sms.events.routine.schedule.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class RoutineScheduleId extends Identity {
	protected RoutineScheduleId() {
	}

	private RoutineScheduleId(String id) {
		super( id );
	}

	private RoutineScheduleId(UUID id) {
		super( id );
	}

	public static RoutineScheduleId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new RoutineScheduleId( id );
	}

	public static RoutineScheduleId ofNotNull(String id) {
		return new RoutineScheduleId( id );
	}

	public static RoutineScheduleId generateNew() {
		return new RoutineScheduleId( UUID.randomUUID() );
	}
}
