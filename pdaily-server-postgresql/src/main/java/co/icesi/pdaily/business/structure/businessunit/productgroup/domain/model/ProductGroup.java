package co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model;

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
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_product_groups")
@NamedQuery(name = ProductGroup.findByReference, query = "SELECT f FROM ProductGroup f WHERE UPPER(f.reference.text) = UPPER(:reference) AND f.tenant = :company")
@NamedQuery(name = ProductGroup.findByName, query = "SELECT f FROM ProductGroup f WHERE f.tenant = :company AND UPPER(f.name.name) = UPPER(:name)")
@NamedQuery(name = ProductGroup.findByBusinessUnit, query = "SELECT f FROM ProductGroup f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
@NamedQuery(name = ProductGroup.countByBusinessUnit, query = "SELECT COUNT(f.id) FROM ProductGroup f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
public class ProductGroup extends PdailyTenantEntity<ProductGroupId> {
	private static final String PREFIX = "ProductGroup.";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String countByBusinessUnit = PREFIX + "countByBusinessUnit";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private ProductGroupId id;
	@Embedded
	private PlainName name;
	@Embedded
	private Reference reference;
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessUnit;

	protected ProductGroup() {
	}

	private ProductGroup(
			ProductGroupId id, PlainName name, Reference reference,
			BusinessUnitId businessUnit) {
		setId( id );
		setName( name );
		setReference( reference );
		setBusinessUnit( businessUnit );
	}

	public static ProductGroup of(
			ProductGroupId id, PlainName name, Reference reference,
			BusinessUnitId businessUnit) {
		return new ProductGroup( id, name, reference, businessUnit );
	}

	public PlainName getName() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del grupo de productos es requerido" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	public ProductGroupId id() {
		return this.id;
	}

	@Override
	public void setId(ProductGroupId id) {
		this.id = id;
	}

	public BusinessUnitId getBusinessUnit() {
		return this.businessUnit;
	}

	private void setBusinessUnit(BusinessUnitId businessUnit) {
		this.businessUnit = requireNonNull(
				businessUnit,
				"La unidad de negocio es requerida"
		);
	}

	public void updateFrom(ProductGroup productGroup) {
		setName( productGroup.name );
		setReference( productGroup.reference );
	}
}
