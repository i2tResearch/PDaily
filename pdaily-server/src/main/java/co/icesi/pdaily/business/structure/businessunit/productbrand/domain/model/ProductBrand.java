package co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model;

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
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_product_brands")
@NamedQuery(name = ProductBrand.findByName, query = "SELECT f FROM ProductBrand f WHERE UPPER(f.name.name) = UPPER(:name) AND f.tenant = :company")
@NamedQuery(name = ProductBrand.findByBusinessUnit, query = "SELECT f FROM ProductBrand f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
@NamedQuery(name = ProductBrand.countByBusinessUnit, query = "SELECT COUNT(f.id) FROM ProductBrand f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
public class ProductBrand extends PdailyTenantEntity<ProductBrandId> {
	private static final String PREFIX = "ProductBrand.";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String countByBusinessUnit = PREFIX + "countByBusinessUnit";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private ProductBrandId id;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessUnit;

	protected ProductBrand() {
	}

	private ProductBrand(ProductBrandId id, PlainName name, BusinessUnitId businessUnit) {
		setId( id );
		setName( name );
		setBusinessUnit( businessUnit );
	}

	public static ProductBrand of(ProductBrandId id, PlainName name, BusinessUnitId businessUnit) {
		return new ProductBrand( id, name, businessUnit );
	}

	@Override
	public ProductBrandId id() {
		return this.id;
	}

	public void setId(ProductBrandId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido " );
	}

	public BusinessUnitId businessUnitId() {
		return businessUnit;
	}

	private void setBusinessUnit(BusinessUnitId businessUnit) {
		this.businessUnit = requireNonNull(
				businessUnit,
				"La unidad de negocio es requerida"
		);
	}

	public void updateFrom(ProductBrand productBrand) {
		setName( productBrand.name );
	}

}
