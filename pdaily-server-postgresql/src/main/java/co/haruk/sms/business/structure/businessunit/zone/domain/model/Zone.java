package co.haruk.sms.business.structure.businessunit.zone.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.common.model.Reference;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

/**
 * @author andres2508 on 24/11/19
 **/
@Entity
@Table(name = "bs_zones")
@NamedQuery(name = Zone.findByName, query = "SELECT z FROM Zone z WHERE z.tenant = :company AND z.businessUnitId = :businessUnit AND UPPER(z.name.name) = UPPER(:name)")
@NamedQuery(name = Zone.findByReference, query = "SELECT z FROM Zone z WHERE z.tenant = :company AND z.businessUnitId = :businessUnit AND UPPER(z.reference.text) = UPPER(:reference)")
@NamedQuery(name = Zone.findByBusinessUnit, query = "SELECT z FROM Zone z WHERE z.tenant = :company AND z.businessUnitId = :businessUnit")
@NamedQuery(name = Zone.countByBusinessUnit, query = "SELECT COUNT(z.id) FROM Zone z WHERE z.tenant = :company AND z.businessUnitId = :businessUnit")
public class Zone extends PdailyTenantEntity<ZoneId> {
	private static final String PREFIX = "Zone.";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String countByBusinessUnit = PREFIX + "countByBusinessUnit";
	@EmbeddedId
	private ZoneId id;
	@Embedded
	private Reference reference;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessUnitId;

	protected Zone() {
	}

	private Zone(ZoneId id, Reference reference, PlainName name, BusinessUnitId businessUnitId) {
		setId( id );
		setReference( reference );
		setName( name );
		setBusinessUnitId( businessUnitId );
	}

	public static Zone of(ZoneId identity, Reference reference, PlainName name, BusinessUnitId businessUnitId) {
		return new Zone( identity, reference, name, businessUnitId );
	}

	@Override
	public ZoneId id() {
		return id;
	}

	@Override
	public void setId(ZoneId id) {
		this.id = id;
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre de la zona es requerido" );
	}

	public BusinessUnitId businessUnitId() {
		return businessUnitId;
	}

	private void setBusinessUnitId(BusinessUnitId id) {
		this.businessUnitId = requireNonNull( id, "La unidad de negocio de la zona es requerida" );
	}

	public void updateFrom(Zone changed) {
		setReference( changed.reference );
		setName( changed.name );
	}
}
