package co.haruk.sms.events.food.schedule.app;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.events.food.schedule.domain.model.FoodSchedule;
import co.haruk.sms.events.food.schedule.domain.model.FoodScheduleId;

public class FoodScheduleDTO {
	public String id;
	public String patientId;
	public String schedule;

	protected FoodScheduleDTO() {
	}

	private FoodScheduleDTO(String id, String patientId, String schedule) {
		this.id = id;
		this.patientId = patientId;
		this.schedule = schedule;
	}

	public static FoodScheduleDTO of(String id, String patientId, String schedule) {
		return new FoodScheduleDTO( id, patientId, schedule );
	}

	public static FoodScheduleDTO of(FoodSchedule foodSchedule) {
		return new FoodScheduleDTO(
				foodSchedule.id().toString(),
				foodSchedule.patientId().text(),
				foodSchedule.schedule().dateExpression()
		);
	}

	public FoodSchedule toFoodSchedule() {
		return FoodSchedule.of(
				FoodScheduleId.of( id ),
				PatientId.ofNotNull( patientId ),
				Schedule.of( schedule )
		);
	}
}
