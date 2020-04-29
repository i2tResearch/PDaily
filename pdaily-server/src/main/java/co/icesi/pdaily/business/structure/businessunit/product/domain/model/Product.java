package co.icesi.pdaily.business.structure.businessunit.product.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model.ProductBrandId;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.icesi.pdaily.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_products")
@NamedQuery(name = Product.findByReference, query = "SELECT f FROM Product f WHERE UPPER(f.reference.text) = UPPER(:reference) AND f.tenant = :company")
@NamedQuery(name = Product.findByName, query = "SELECT f FROM Product f WHERE f.tenant = :company AND UPPER(f.name.name) = UPPER(:name)")
@NamedQuery(name = Product.findByBusinessUnit, query = "SELECT f FROM Product f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
@NamedQuery(name = Product.countByBusinessUnit, query = "SELECT COUNT(f.id) FROM Product f WHERE f.businessUnit = :businessUnit AND f.tenant = :company")
@NamedQuery(name = Product.countByBrand, query = "SELECT COUNT(f.id) FROM Product f WHERE f.brandId = :brandId AND f.tenant = :company")
@NamedQuery(name = Product.countByLine, query = "SELECT COUNT(f.id) FROM Product f WHERE f.lineId = :lineId AND f.tenant = :company")
@NamedQuery(name = Product.countByGroup, query = "SELECT COUNT(f.id) FROM Product f WHERE f.groupId = :groupId AND f.tenant = :company")
@NamedQuery(name = Product.findAllAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.businessunit.product.domain.model.view.ProductReadView(p.id.id, p.name.name, p.reference.text, b.name.name, p.businessUnit.id, r.name.name, p.brandId.id, l.name.name, p.lineId.id, g.name.name, p.groupId.id) "
		+
		"FROM Product p INNER JOIN BusinessUnit b ON b.id = p.businessUnit LEFT JOIN ProductBrand r ON r.id = p.brandId LEFT JOIN ProductLine l ON l.id = p.lineId LEFT JOIN ProductGroup g ON g.id = p.groupId WHERE p.tenant = :company")
@NamedQuery(name = Product.findByIdAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.businessunit.product.domain.model.view.ProductReadView(p.id.id, p.name.name, p.reference.text, b.name.name, p.businessUnit.id, r.name.name, p.brandId.id, l.name.name, p.lineId.id, g.name.name, p.groupId.id) "
		+
		"FROM Product p " +
		"INNER JOIN BusinessUnit b ON b.id = p.businessUnit " +
		"LEFT JOIN ProductBrand r ON r.id = p.brandId " +
		"LEFT JOIN ProductLine l ON l.id = p.lineId " +
		"LEFT JOIN ProductGroup g ON g.id = p.groupId " +
		"WHERE p.tenant = :company AND p.id = :id")
@NamedQuery(name = Product.findByBUnitAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.businessunit.product.domain.model.view.ProductReadView(p.id.id, p.name.name, p.reference.text, b.name.name, p.businessUnit.id, r.name.name, p.brandId.id, l.name.name, p.lineId.id, g.name.name, p.groupId.id) "
		+
		"FROM Product p INNER JOIN BusinessUnit b ON b.id = p.businessUnit LEFT JOIN ProductBrand r ON r.id = p.brandId LEFT JOIN ProductLine l ON l.id = p.lineId LEFT JOIN ProductGroup g ON g.id = p.groupId WHERE p.businessUnit = :businessUnit AND p.tenant = :company")
public class Product extends PdailyTenantEntity<ProductId> {
	private static final String PREFIX = "Product.";
	public static final String findByReference = PREFIX + "findByReference";
	public static final String findByName = PREFIX + "findByName";
	public static final String findByBusinessUnit = PREFIX + "findByBusinessUnit";
	public static final String countByBusinessUnit = PREFIX + "countByBusinessUnit";
	public static final String countByBrand = PREFIX + "countByBrand";
	public static final String countByLine = PREFIX + "countByLine";
	public static final String countByGroup = PREFIX + "countByGroup";
	public static final String findAllAsReadView = PREFIX + "findAllAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";
	public static final String findByBUnitAsReadView = PREFIX + "findByBUnitAsReadView";

	@EmbeddedId
	private ProductId id;
	@Embedded
	private PlainName name;
	@Embedded
	private Reference reference;
	@AttributeOverride(name = "id", column = @Column(name = "business_id"))
	private BusinessUnitId businessUnit;
	@AttributeOverride(name = "id", column = @Column(name = "brand_id"))
	private ProductBrandId brandId;
	@AttributeOverride(name = "id", column = @Column(name = "line_id"))
	private ProductLineId lineId;
	@AttributeOverride(name = "id", column = @Column(name = "group_id"))
	private ProductGroupId groupId;

	protected Product() {
	}

	private Product(
			ProductId id, PlainName name, Reference reference, BusinessUnitId businessUnit,
			ProductBrandId brandId, ProductLineId lineId, ProductGroupId groupId) {
		setId( id );
		setName( name );
		setReference( reference );
		setBusinessUnit( businessUnit );
		setBrandId( brandId );
		setLineId( lineId );
		setGroupId( groupId );
	}

	public static Product of(
			ProductId id, PlainName name, Reference reference, BusinessUnitId businessUnit,
			ProductBrandId brandId, ProductLineId lineId, ProductGroupId groupId) {
		return new Product( id, name, reference, businessUnit, brandId, lineId, groupId );
	}

	@Override
	public ProductId id() {
		return id;
	}

	public void setId(ProductId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del producto es requerido" );
	}

	public Reference reference() {
		return reference;
	}

	private void setReference(Reference reference) {
		this.reference = reference;
	}

	public BusinessUnitId businessUnit() {
		return businessUnit;
	}

	private void setBusinessUnit(BusinessUnitId businessUnit) {
		this.businessUnit = requireNonNull(
				businessUnit,
				"La unidad de negocio es requerida"
		);
	}

	public ProductBrandId brandId() {
		return brandId;
	}

	private void setBrandId(ProductBrandId brandId) {
		this.brandId = brandId;
	}

	public ProductLineId lineId() {
		return lineId;
	}

	private void setLineId(ProductLineId lineId) {
		this.lineId = lineId;
	}

	public ProductGroupId groupId() {
		return groupId;
	}

	private void setGroupId(ProductGroupId groupId) {
		this.groupId = groupId;
	}

	public void updateFrom(Product product) {
		setName( product.name );
		setReference( product.reference );
		setBrandId( product.brandId );
		setLineId( product.lineId );
		setGroupId( product.groupId );
	}
}
