package co.haruk.sms.events.physical.domain.model.view;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class PhysicalEventReadView {
	public String id;
	public String injuryTypeId;
	public String injuryTypeName;
	public Long initialDate;
	public Long finalDate;
	public int intensity;
	public List<BodyPartDetailReadView> bodyDetails;

	protected PhysicalEventReadView() {
	}

	public PhysicalEventReadView(
			UUID id,
			UUID injuryTypeId,
			String injuryTypeName,
			Instant initialDate,
			Instant finalDate,
			int intensity) {
		this.id = id.toString();
		this.intensity = intensity;
		this.injuryTypeId = injuryTypeId.toString();
		this.injuryTypeName = injuryTypeName;
		this.initialDate = initialDate.toEpochMilli();
		this.finalDate = finalDate.toEpochMilli();
	}

}
