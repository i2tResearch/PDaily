package co.haruk.sms.events.physical.injury.type.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.events.physical.injury.type.domain.model.InjuryType;
import co.haruk.sms.events.physical.injury.type.domain.model.InjuryTypeId;

public class InjuryTypeDTO {
	public String id;
	public String name;

	protected InjuryTypeDTO() {
	}

	private InjuryTypeDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static InjuryTypeDTO of(String id, String name) {
		return new InjuryTypeDTO( id, name );
	}

	public static InjuryTypeDTO of(InjuryType injuryType) {
		return new InjuryTypeDTO( injuryType.id().text(), injuryType.name().text() );
	}

	public InjuryType toInjuryType() {
		return InjuryType.of(
				InjuryTypeId.of( id ),
				PlainName.of( name )
		);
	}
}
