package co.haruk.sms.events.animic.domain.model.view;

import java.time.Instant;
import java.util.UUID;

public final class AnimicEventReadView {
	public String id;
	public String patientId;
	public String typeId;
	public String typeLabel;
	public int intensity;
	public Long occurenceDate;

	protected AnimicEventReadView() {
	}

	public AnimicEventReadView(
			UUID id,
			UUID patientId,
			UUID typeId,
			String typeLabel,
			int intensity,
			Instant ocurrenceDate) {
		this.id = id.toString();
		this.patientId = patientId.toString();
		this.typeId = typeId.toString();
		this.typeLabel = typeLabel;
		this.intensity = intensity;
		this.occurenceDate = ocurrenceDate.toEpochMilli();
	}
}
