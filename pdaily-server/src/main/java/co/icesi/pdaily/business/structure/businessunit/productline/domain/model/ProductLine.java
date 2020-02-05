package co.icesi.pdaily.business.structure.businessunit.productline.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "bs_product_lines")
@NamedQuery(name = ProductLine.findByReference, query = "SELECT f FROM ProductLine f WHERE UPPER(f.reference.text) = UPPER(:reference) AND f.tenant = :company")
@NamedQuery(name = ProductLine.findByName, query = "SELECT f FROM ProductLine f WHERE UPPER(f.name.name) = UPPER(:name) AND f.tenant = :company")
@NamedQuery(name = ProductLine.findByBusinessUnit, query = "SELECT f FROM ProductLine f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
@NamedQuery(name = ProductLine.countByBusinessUnit, query = "SELECT COUNT(f.id) FROM ProductLine f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
public class ProductLine extends HarukTenantEntity<ProductLineId> {
	private static final String PREFIX = "ProductLine.";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String countByBusinessUnit = PREFIX + "countByBusinessUnit";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private ProductLineId id;
	@Embedded
	private PlainName name;
	@Embedded
	private Reference reference;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessUnit;

	protected ProductLine() {
	}

	private ProductLine(
			ProductLineId id,
			PlainName name,
			Reference reference,
			BusinessUnitId businessUnitId) {
		setId( id );
		setName( name );
		setReference( reference );
		setBusinessUnit( businessUnitId );
	}

	public static ProductLine of(
			ProductLineId id,
			PlainName name,
			Reference reference,
			BusinessUnitId businessUnitId) {
		return new ProductLine( id, name, reference, businessUnitId );
	}

	@Override
	public void setId(ProductLineId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre de la linea es requerido" );
	}

	public BusinessUnitId businessUnit() {
		return businessUnit;
	}

	private void setBusinessUnit(BusinessUnitId businessUnit) {
		this.businessUnit = requireNonNull(
				businessUnit,
				"La unidad de negocio de la linea es requerida"
		);
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public void updateFrom(ProductLine productLine) {
		setName( productLine.name );
		setReference( productLine.reference );
	}

	@Override
	public ProductLineId id() {
		return this.id;
	}
}
