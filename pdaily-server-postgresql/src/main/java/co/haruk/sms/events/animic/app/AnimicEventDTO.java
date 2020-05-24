package co.haruk.sms.events.animic.app;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.animic.domain.model.AnimicTypeId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.events.animic.domain.model.AnimicEvent;
import co.haruk.sms.events.animic.domain.model.AnimicEventId;

public final class AnimicEventDTO {
	public String id;
	public String patientId;
	public String typeId;
	public Long ocurrenceDate;

	protected AnimicEventDTO() {
	}

	private AnimicEventDTO(String id, String patientId, String typeId, Long ocurrenceDate) {
		this.id = id;
		this.patientId = patientId;
		this.typeId = typeId;
		this.ocurrenceDate = ocurrenceDate;
	}

	public static AnimicEventDTO of(String id, String patientId, String typeId, Long ocurrenceDate) {
		return new AnimicEventDTO( id, patientId, typeId, ocurrenceDate );
	}

	public static AnimicEventDTO of(AnimicEvent animicEvent) {
		return new AnimicEventDTO(
				animicEvent.id().text(),
				animicEvent.patientId().text(),
				animicEvent.type().text(),
				animicEvent.ocurrenceDate().dateAsLong()
		);
	}

	public AnimicEvent toAnimicEvent() {
		return AnimicEvent.of(
				AnimicEventId.of( id ),
				PatientId.ofNotNull( patientId ),
				AnimicTypeId.ofNotNull( typeId ),
				UTCDateTime.of( ocurrenceDate )
		);
	}
}
