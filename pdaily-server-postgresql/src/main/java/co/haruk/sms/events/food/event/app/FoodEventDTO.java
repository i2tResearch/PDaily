package co.haruk.sms.events.food.event.app;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.events.food.event.domain.model.FoodEvent;
import co.haruk.sms.events.food.event.domain.model.FoodEventId;

public class FoodEventDTO {
	public String id;
	public String patientId;
	public Long date;

	protected FoodEventDTO() {
	}

	private FoodEventDTO(String id, String patientId, Long date) {
		this.id = id;
		this.patientId = patientId;
		this.date = date;
	}

	public static FoodEventDTO of(String id, String patientId, Long date) {
		return new FoodEventDTO( id, patientId, date );
	}

	public static FoodEventDTO of(FoodEvent event) {
		return new FoodEventDTO(
				event.id().text(),
				event.patientId().text(),
				event.date().dateAsLong()
		);
	}

	public FoodEvent toFoodEvent() {
		return FoodEvent.of(
				FoodEventId.of( id ),
				PatientId.ofNotNull( patientId ),
				UTCDateTime.of( date )
		);
	}
}
