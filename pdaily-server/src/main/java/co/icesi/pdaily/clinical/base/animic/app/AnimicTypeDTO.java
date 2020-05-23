package co.icesi.pdaily.clinical.base.animic.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicType;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicTypeId;

public final class AnimicTypeDTO {
	public String id;
	public String label;

	protected AnimicTypeDTO() {
	}

	private AnimicTypeDTO(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public static AnimicTypeDTO of(String id, String label) {
		return new AnimicTypeDTO( id, label );
	}

	public static AnimicTypeDTO of(AnimicType animicType) {
		return new AnimicTypeDTO( animicType.id().text(), animicType.label().text() );
	}

	public AnimicType toAnimicType() {
		return AnimicType.of(
				AnimicTypeId.of( id ),
				PlainName.of( label )
		);
	}
}
