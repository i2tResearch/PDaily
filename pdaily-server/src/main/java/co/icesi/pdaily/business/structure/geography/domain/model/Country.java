package co.icesi.pdaily.business.structure.geography.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.PdailyEntity;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author cristhiank on 2/12/19
 **/
@Entity
@Table(name = "bs_geo_countries")
@NamedQuery(name = Country.findByCode, query = "SELECT c FROM Country c WHERE c.code = :code")
@NamedQuery(name = Country.findByName, query = "SELECT c FROM Country c WHERE UPPER(c.name.name) = UPPER(:name)")
public class Country extends PdailyEntity<CountryId> {
	private static final String PREFIX = "Country.";
	public static final String findByCode = PREFIX + "findByCode";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private CountryId id;
	@Embedded
	private CountryIsoCode code;
	@Embedded
	private PlainName name;

	protected Country() {
	}

	private Country(CountryId id, CountryIsoCode code, PlainName name) {
		setId( id );
		setCode( code );
		setName( name );
	}

	public static Country of(CountryId id, CountryIsoCode code, PlainName name) {
		return new Country( id, code, name );
	}

	@Override
	public CountryId id() {
		return this.id;
	}

	@Override
	public void setId(CountryId id) {
		this.id = id;
	}

	public CountryIsoCode isoCode() {
		return code;
	}

	private void setCode(CountryIsoCode code) {
		this.code = requireNonNull( code, "El código del pais es requerido" );
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del país es requerido" );
	}

	public void updateFrom(Country changes) {
		setCode( changes.code );
		setName( changes.name );
	}

	public State createState(Reference reference, PlainName stateName) {
		return State.of( this.id, reference, stateName );
	}
}
