package co.icesi.pdaily.business.structure.geography.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.geography.domain.model.City;
import co.icesi.pdaily.business.structure.geography.domain.model.CityId;
import co.icesi.pdaily.business.structure.geography.domain.model.StateId;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author andres2508 on 5/12/19
 **/
public final class CityDTO {
	public String id;
	public String code;
	public String name;
	public String stateId;

	protected CityDTO() {
	}

	private CityDTO(String id, String stateId, String code, String name) {
		this.id = id;
		this.stateId = stateId;
		this.code = code;
		this.name = name;
	}

	public static CityDTO of(String id, String stateId, String code, String name) {
		return new CityDTO( id, stateId, code, name );
	}

	public static CityDTO of(City city) {
		return new CityDTO(
				city.id().text(),
				city.state().text(),
				city.reference().text(),
				city.name().text()
		);
	}

	public City toCity() {
		return City.of(
				CityId.of( id ),
				StateId.ofNotNull( stateId ),
				Reference.of( code ),
				PlainName.of( name )
		);
	}
}
