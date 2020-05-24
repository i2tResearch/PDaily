package co.haruk.sms.events.physical.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class BodyPartDetailReadView implements Serializable {
	public String id;
	public String bodyPartId;
	public String bodyPartName;

	protected BodyPartDetailReadView() {
	}

	public BodyPartDetailReadView(UUID id, UUID bodyPartId, String bodyPartName) {
		this.id = id.toString();
		this.bodyPartId = bodyPartId.toString();
		this.bodyPartName = bodyPartName;
	}

}
