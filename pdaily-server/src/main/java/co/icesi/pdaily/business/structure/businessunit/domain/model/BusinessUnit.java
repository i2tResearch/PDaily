package co.icesi.pdaily.business.structure.businessunit.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "bs_business_units")
@NamedQuery(name = BusinessUnit.findByIntReference, query = "SELECT f FROM BusinessUnit f WHERE UPPER(f.reference.text) = UPPER(:reference) AND f.tenant = :company")
@NamedQuery(name = BusinessUnit.findByName, query = "SELECT f FROM BusinessUnit f WHERE UPPER(f.name.name) = UPPER(:name) AND f.tenant = :company")
public class BusinessUnit extends HarukTenantEntity<BusinessUnitId> {
	private static final String PREFIX = "BusinessUnit.";
	public static final String findByIntReference = PREFIX + "findByIntReference";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private BusinessUnitId id;
	@Embedded
	private PlainName name;
	@Embedded
	private Reference reference;

	protected BusinessUnit() {
	}

	private BusinessUnit(
			BusinessUnitId id,
			PlainName name,
			Reference reference) {
		setId( id );
		setName( name );
		setReference( reference );
	}

	public static BusinessUnit of(
			BusinessUnitId id,
			PlainName name,
			Reference reference) {
		return new BusinessUnit( id, name, reference );
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido para una unidad de negocio." );
	}

	@Override
	public void setId(BusinessUnitId businessUnitId) {
		this.id = businessUnitId;
	}

	public void updateFrom(BusinessUnit businessUnit) {
		setName( businessUnit.name() );
		setReference( businessUnit.reference() );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	public BusinessUnitId id() {
		return this.id;
	}
}
