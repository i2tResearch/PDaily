package co.icesi.pdaily.business.structure.geography.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.geography.domain.model.CountryId;
import co.icesi.pdaily.business.structure.geography.domain.model.State;
import co.icesi.pdaily.business.structure.geography.domain.model.StateId;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author andres2508 on 5/12/19
 **/
public final class StateDTO {
	public String id;
	public String code;
	public String name;
	public String countryId;

	protected StateDTO() {
	}

	private StateDTO(String id, String countryId, String code, String name) {
		this.id = id;
		this.countryId = countryId;
		this.code = code;
		this.name = name;
	}

	public static StateDTO of(String id, String countryId, String code, String name) {
		return new StateDTO( id, countryId, code, name );
	}

	public static StateDTO of(State state) {
		return new StateDTO(
				state.id().text(),
				state.country().text(),
				state.reference().text(),
				state.name().text()
		);
	}

	public State toState() {
		return State.of(
				StateId.of( id ),
				CountryId.ofNotNull( countryId ),
				Reference.of( code ),
				PlainName.of( name )
		);
	}
}
