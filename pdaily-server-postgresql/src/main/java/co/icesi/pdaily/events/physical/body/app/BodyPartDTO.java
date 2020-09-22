package co.icesi.pdaily.events.physical.body.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPart;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPartId;

public class BodyPartDTO {
	public String id;
	public String name;

	protected BodyPartDTO() {
	}

	private BodyPartDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static BodyPartDTO of(String id, String name) {
		return new BodyPartDTO( id, name );
	}

	public static BodyPartDTO of(BodyPart part) {
		return new BodyPartDTO( part.id().text(), part.name().text() );
	}

	public BodyPart toBodyPart() {
		return BodyPart.of(
				BodyPartId.of( id ),
				PlainName.of( name )
		);
	}
}
