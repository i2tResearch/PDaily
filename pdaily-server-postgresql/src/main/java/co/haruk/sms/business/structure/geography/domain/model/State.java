package co.haruk.sms.business.structure.geography.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.PdailyEntity;
import co.haruk.sms.common.model.Reference;

/**
 * @author andres2508 on 2/12/19
 **/
@Entity
@Table(name = "bs_geo_states")
@NamedQuery(name = State.findByName, query = "SELECT s FROM State s WHERE UPPER(s.name.name) = UPPER(:name) AND s.country = :country")
@NamedQuery(name = State.findByReference, query = "SELECT s FROM State s WHERE UPPER(s.reference.text) = UPPER(:reference) AND s.country = :country")
@NamedQuery(name = State.countByCountry, query = "SELECT COUNT(s.id) FROM State s WHERE s.country = :country")
@NamedQuery(name = State.findByCountry, query = "SELECT s FROM State s WHERE s.country = :country ORDER BY name.name")
public class State extends PdailyEntity<StateId> {
	private static final String PREFIX = "State.";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByCountry = PREFIX + "findByCountry";
	public static final String countByCountry = PREFIX + "countByCountry";

	@EmbeddedId
	private StateId id;
	@Embedded
	private Reference reference;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "country_id", updatable = false, nullable = false))
	private CountryId country;

	protected State() {
	}

	private State(StateId id, CountryId country, Reference reference, PlainName name) {
		setId( id );
		setCountry( country );
		setReference( reference );
		setName( name );
	}

	public static State of(CountryId country, Reference reference, PlainName name) {
		return of( null, country, reference, name );
	}

	public static State of(StateId id, CountryId country, Reference reference, PlainName name) {
		return new State( id, country, reference, name );
	}

	@Override
	public StateId id() {
		return this.id;
	}

	@Override
	public void setId(StateId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del estado es requerido" );
	}

	public CountryId country() {
		return country;
	}

	private void setCountry(CountryId country) {
		this.country = requireNonNull( country, "El pais del estado es requerido" );
	}

	public City createCity(Reference reference, PlainName cityName) {
		return City.of( this.id, reference, cityName );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public void updateFrom(State changed) {
		setReference( changed.reference );
		setName( changed.name );
	}
}
