package co.icesi.pdaily.events.routine.schedule.app;

import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.clinical.base.routines.domain.model.RoutineTypeId;
import co.icesi.pdaily.common.model.Schedule;
import co.icesi.pdaily.events.routine.schedule.domain.model.RoutineSchedule;
import co.icesi.pdaily.events.routine.schedule.domain.model.RoutineScheduleId;

public final class RoutineScheduleDTO {
	public String id;
	public String patientId;
	public String typeId;
	public String schedule;

	protected RoutineScheduleDTO() {
	}

	private RoutineScheduleDTO(String id, String patientId, String typeId, String schedule) {
		this.id = id;
		this.patientId = patientId;
		this.typeId = typeId;
		this.schedule = schedule;
	}

	public static RoutineScheduleDTO of(String id, String patientId, String typeId, String schedule) {
		return new RoutineScheduleDTO( id, patientId, typeId, schedule );
	}

	public static RoutineScheduleDTO of(RoutineSchedule schedule) {
		return new RoutineScheduleDTO(
				schedule.id().toString(),
				schedule.patientId().toString(),
				schedule.type().toString(),
				schedule.schedule().dateExpression()
		);
	}

	public RoutineSchedule toRoutineSchedule() {
		return RoutineSchedule.of(
				RoutineScheduleId.of( id ),
				PatientId.ofNotNull( patientId ),
				RoutineTypeId.ofNotNull( typeId ),
				Schedule.of( schedule )
		);
	}
}
