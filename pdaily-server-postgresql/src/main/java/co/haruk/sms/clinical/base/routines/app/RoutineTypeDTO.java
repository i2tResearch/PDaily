package co.haruk.sms.clinical.base.routines.app;

import co.haruk.sms.clinical.base.routines.domain.model.RoutineType;
import co.haruk.sms.clinical.base.routines.domain.model.RoutineTypeId;
import co.haruk.sms.common.model.Reference;

public final class RoutineTypeDTO {
	public String id;
	public String label;

	protected RoutineTypeDTO() {
	}

	private RoutineTypeDTO(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public static RoutineTypeDTO of(String id, String label) {
		return new RoutineTypeDTO( id, label );
	}

	public static RoutineTypeDTO of(RoutineType type) {
		return new RoutineTypeDTO(
				type.id().toString(),
				type.label().text()
		);
	}

	public RoutineType toRoutineType() {
		return RoutineType.of(
				RoutineTypeId.of( id ),
				Reference.of( label )
		);
	}
}
