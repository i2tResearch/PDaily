package co.haruk.sms.events.routine.schedule.app;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.routines.domain.model.RoutineTypeId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineSchedule;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineScheduleId;

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
