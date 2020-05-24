package co.haruk.sms.business.structure.geography.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.geography.domain.model.Country;
import co.haruk.sms.business.structure.geography.domain.model.CountryId;
import co.haruk.sms.business.structure.geography.domain.model.CountryIsoCode;

/**
 * @author cristhiank on 2/12/19
 **/
public final class CountryDTO {
	public String id;
	public String code;
	public String name;

	protected CountryDTO() {
	}

	private CountryDTO(String id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public static CountryDTO of(String id, String code, String name) {
		return new CountryDTO( id, code, name );
	}

	public static CountryDTO of(Country country) {
		return new CountryDTO( country.id().text(), country.isoCode().text(), country.name().text() );
	}

	public Country toCountry() {
		return Country.of(
				CountryId.of( id ),
				CountryIsoCode.of( code ),
				PlainName.of( name )
		);
	}
}
