package co.haruk.sms.clinical.base.levodopa.type.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.clinical.base.levodopa.type.domain.model.LevodopaType;
import co.haruk.sms.clinical.base.levodopa.type.domain.model.LevodopaTypeId;

public class LevodopaTypeDTO {
	public String id;
	public String label;

	protected LevodopaTypeDTO() {
	}

	private LevodopaTypeDTO(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public static LevodopaTypeDTO of(String id, String label) {
		return new LevodopaTypeDTO( id, label );
	}

	public static LevodopaTypeDTO of(LevodopaType injuryType) {
		return new LevodopaTypeDTO( injuryType.id().text(), injuryType.label().text() );
	}

	public LevodopaType toLevodopaType() {
		return LevodopaType.of(
				LevodopaTypeId.of( id ),
				PlainName.of( label )
		);
	}
}
