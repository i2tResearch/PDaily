package co.icesi.pdaily.events.physical.app;

import co.icesi.pdaily.events.physical.body.domain.model.BodyPartId;
import co.icesi.pdaily.events.physical.domain.model.PhysicalEvent;
import co.icesi.pdaily.events.physical.domain.model.detail.BodyPartDetail;
import co.icesi.pdaily.events.physical.domain.model.detail.BodyPartDetailId;

public final class BodyPartDetailDTO {
	public String id;
	public String bodyPartId;

	protected BodyPartDetailDTO() {
	}

	private BodyPartDetailDTO(String id, String bodyPartId) {
		this.id = id;
		this.bodyPartId = bodyPartId;
	}

	public static BodyPartDetailDTO of(String id, String bodyPartId) {
		return new BodyPartDetailDTO( id, bodyPartId );
	}

	public BodyPartDetail toBodyPartDetail(PhysicalEvent physicalEvent) {
		BodyPartDetailId bodyPartDetailId = id == null ? BodyPartDetailId.generateNew() : BodyPartDetailId.ofNotNull( id );
		return BodyPartDetail.of(
				physicalEvent,
				bodyPartDetailId,
				BodyPartId.ofNotNull( bodyPartId )
		);
	}
}
