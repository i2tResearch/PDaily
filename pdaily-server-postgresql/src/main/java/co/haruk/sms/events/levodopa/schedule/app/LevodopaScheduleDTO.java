package co.haruk.sms.events.levodopa.schedule.app;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.events.levodopa.schedule.domain.model.LevodopaSchedule;
import co.haruk.sms.events.levodopa.schedule.domain.model.LevodopaScheduleId;
import co.haruk.sms.events.levodopa.schedule.domain.model.PillsDose;

public final class LevodopaScheduleDTO {
	public String id;
	public String patientId;
	public String medicineId;
	public String schedule;
	public int dose;

	protected LevodopaScheduleDTO() {
	}

	private LevodopaScheduleDTO(String id, String patientId, String medicineId, String schedule, int dose) {
		this.id = id;
		this.patientId = patientId;
		this.medicineId = medicineId;
		this.schedule = schedule;
		this.dose = dose;
	}

	public static LevodopaScheduleDTO of(String id, String patientId, String medicineId, String schedule, int dose) {
		return new LevodopaScheduleDTO( id, patientId, medicineId, schedule, dose );
	}

	public static LevodopaScheduleDTO of(LevodopaSchedule schedule) {
		return new LevodopaScheduleDTO(
				schedule.id().toString(),
				schedule.patientId().toString(),
				schedule.medicineId().toString(),
				schedule.schedule().dateExpression(),
				schedule.dose().dose()
		);
	}

	public LevodopaSchedule toLevodopaSchedule() {
		return LevodopaSchedule.of(
				LevodopaScheduleId.of( id ),
				PatientId.ofNotNull( patientId ),
				LevodopaMedicineId.ofNotNull( medicineId ),
				Schedule.of( schedule ),
				PillsDose.of( dose )
		);
	}
}
