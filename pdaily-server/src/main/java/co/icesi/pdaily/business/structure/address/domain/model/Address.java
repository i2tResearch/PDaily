package co.icesi.pdaily.business.structure.address.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.business.structure.address.infrastructure.persistence.AddressRepository;
import co.icesi.pdaily.business.structure.geography.domain.model.CityId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

/**
 * @author cristhiank on 2/12/19
 **/
@Entity
@Table(name = "bs_addresses")
@NamedQuery(name = Address.findAllByReferenced, query = "SELECT a FROM Address a WHERE a.tenant = :company AND a.referencedId = :referenced")
@NamedQuery(name = Address.findMainByReferenced, query = "SELECT a FROM Address a WHERE a.tenant = :company AND a.referencedId = :referenced AND a.main = TRUE")
@NamedQuery(name = Address.markAllAsSecondary, query = "UPDATE Address a SET a.main = FALSE WHERE a.tenant = :company AND a.referencedId = :referenced AND a.main = TRUE")
@NamedQuery(name = Address.deleteAllForReferenced, query = "DELETE Address a WHERE a.tenant = :company AND a.referencedId = :referenced")
public class Address extends PdailyTenantEntity<AddressId> {
	private static final String PREFIX = "PREFIX";
	public static final String findAllByReferenced = PREFIX + "findAllByReferenced";
	public static final String findMainByReferenced = PREFIX + "findMainByReferenced";
	public static final String markAllAsSecondary = PREFIX + "markAllAsSecondary";
	public static final String deleteAllForReferenced = PREFIX + "deleteAllForReferenced";
	@EmbeddedId
	private AddressId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "referenced_id"))
	private ReferencedId referencedId;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "street_line"))
	private StreetLine line1;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "street_line2"))
	private StreetLine line2;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "city_id"))
	private CityId cityId;
	@Embedded
	private Geolocation geolocation;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "description"))
	private Reference description;
	@Column(name = "is_main")
	private boolean main;

	protected Address() {
	}

	private Address(AddressId id, ReferencedId referenced, Reference description, StreetLine line1, StreetLine line2,
			CityId cityId, Geolocation geolocation, boolean main) {
		setId( id );
		setReferencedId( referenced );
		setDescription( description );
		setLine1( line1 );
		setLine2( line2 );
		setCityId( cityId );
		setGeolocation( geolocation );
		setMain( main );
	}

	public static Address of(AddressId id, ReferencedId referenced, Reference description, StreetLine line1, StreetLine line2,
			CityId cityId, Geolocation geolocation, boolean main) {
		return new Address( id, referenced, description, line1, line2, cityId, geolocation, main );
	}

	@Override
	public AddressId id() {
		return id;
	}

	@Override
	public void setId(AddressId id) {
		this.id = id;
	}

	public StreetLine line1() {
		return line1;
	}

	private void setLine1(StreetLine line1) {
		this.line1 = Guards.requireNonNull( line1, "La linea de dirección es obligatoria" );
	}

	public StreetLine line2() {
		return line2;
	}

	private void setLine2(StreetLine line2) {
		this.line2 = line2;
	}

	public CityId cityId() {
		return cityId;
	}

	private void setCityId(CityId cityId) {
		this.cityId = requireNonNull( cityId, "La ciudad de la dirección es obligatoria" );
	}

	public Geolocation geolocation() {
		return geolocation;
	}

	private void setGeolocation(Geolocation geolocation) {
		this.geolocation = geolocation;
	}

	public ReferencedId referencedId() {
		return referencedId;
	}

	private void setReferencedId(ReferencedId referencedId) {
		this.referencedId = Guards.requireNonNull( referencedId, "Debe indicar el propietario de la dirección" );
	}

	public boolean isMain() {
		return main;
	}

	private void setMain(boolean main) {
		this.main = main;
	}

	public Reference description() {
		return description;
	}

	private void setDescription(Reference description) {
		this.description = description;
	}

	public void updateFrom(Address changes) {
		setLine1( changes.line1 );
		setLine2( changes.line2 );
		setCityId( changes.cityId );
		setGeolocation( changes.geolocation );
		setDescription( changes.description );
	}

	public void setAsMainAddress(AddressRepository repository) {
		repository.markAllAsSecondaryForReferenced( this.referencedId );
		this.setMain( true );
		repository.update( this );
	}
}
