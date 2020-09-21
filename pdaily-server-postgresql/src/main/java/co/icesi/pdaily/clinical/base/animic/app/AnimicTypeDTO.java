package co.icesi.pdaily.clinical.base.animic.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicType;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicTypeId;
import co.icesi.pdaily.events.physical.domain.model.Intensity;

public final class AnimicTypeDTO {
	public String id;
	public String label;
	public int intensity;

	protected AnimicTypeDTO() {
	}

	private AnimicTypeDTO(String id, String label, int intensity) {
		this.id = id;
		this.label = label;
	}

	public static AnimicTypeDTO of(String id, String label, int intensity) {
		return new AnimicTypeDTO( id, label, intensity );
	}

	public static AnimicTypeDTO of(AnimicType animicType) {
		return new AnimicTypeDTO( animicType.id().text(), animicType.label().text(), animicType.intensity().intensity() );
	}

	public AnimicType toAnimicType() {
		return AnimicType.of(
				AnimicTypeId.of( id ),
				PlainName.of( label ),
				Intensity.of( intensity )
		);
	}
}
