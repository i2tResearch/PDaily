package co.icesi.pdaily.business.structure.geography.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
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
@Table(name = "bs_geo_cities")
@NamedQuery(name = City.findByName, query = "SELECT c FROM City c WHERE UPPER(c.name.name) = UPPER(:name) AND c.state = :state")
@NamedQuery(name = City.findByReference, query = "SELECT c FROM City c WHERE UPPER(c.reference.text) = UPPER(:reference) AND c.state = :state")
@NamedQuery(name = City.countByState, query = "SELECT COUNT(c.id) FROM City c WHERE c.state = :state")
@NamedQuery(name = City.findByState, query = "SELECT c FROM City c WHERE c.state = :state ORDER BY name.name")
@NamedQuery(name = City.findByIdTextView, query = "SELECT new co.icesi.pdaily.business.structure.geography.domain.model.view.CityTextView(c.id.id,c.name.name,s.id.id,s.name.name,co.id.id,co.name.name) FROM City c INNER JOIN State s ON c.state = s.id INNER JOIN Country co ON s.country = co.id WHERE c.id = :cityId")
public class City extends PdailyEntity<CityId> {
	private static final String PREFIX = "City.";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String countByState = PREFIX + "countByState";
	public static final String findByState = PREFIX + "findByState";
	public static final String findByIdTextView = PREFIX + "findByIdTextView";

	@EmbeddedId
	private CityId id;
	@Embedded
	private Reference reference;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "state_id", updatable = false, nullable = false))
	private StateId state;

	protected City() {
	}

	private City(CityId id, StateId state, Reference reference, PlainName name) {
		setId( id );
		setState( state );
		setName( name );
		setReference( reference );
	}

	public static City of(StateId state, Reference reference, PlainName name) {
		return of( null, state, reference, name );
	}

	public static City of(CityId id, StateId state, Reference reference, PlainName name) {
		return new City( id, state, reference, name );
	}

	@Override
	public CityId id() {
		return this.id;
	}

	@Override
	public void setId(CityId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre de la ciudad es requerido" );
	}

	public StateId state() {
		return state;
	}

	private void setState(StateId state) {
		this.state = requireNonNull( state, "El estado de la ciudad es requerido" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public void updateFrom(City changed) {
		setReference( changed.reference );
		setName( changed.name );
	}
}
