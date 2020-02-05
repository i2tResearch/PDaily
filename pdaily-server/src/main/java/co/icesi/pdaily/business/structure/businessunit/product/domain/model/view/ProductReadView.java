package co.icesi.pdaily.business.structure.businessunit.product.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.product.domain.model.Product;
import co.icesi.pdaily.business.structure.businessunit.product.domain.model.ProductId;
import co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model.ProductBrandId;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.icesi.pdaily.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.icesi.pdaily.common.model.Reference;

public final class ProductReadView implements Serializable {
	public String id;
	public String name;
	public String reference;
	public String businessName;
	public String businessId;
	public String brandName;
	public String brandId;
	public String lineName;
	public String lineId;
	public String groupName;
	public String groupId;

	private ProductReadView(String id, String name, String reference, String businessName, String businessId,
			String brandName, String brandId, String lineName, String lineId,
			String groupName, String groupId) {
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.businessName = businessName;
		this.businessId = businessId;
		this.brandName = brandName;
		this.brandId = brandId;
		this.lineName = lineName;
		this.lineId = lineId;
		this.groupName = groupName;
		this.groupId = groupId;
	}

	public ProductReadView(UUID id, String name, String reference, String businessName, UUID businessId,
			String brandName, UUID brandId, String lineName, UUID lineId,
			String groupName, UUID groupId) {

		final String brand = brandId != null ? brandId.toString() : null;
		final String line = lineId != null ? lineId.toString() : null;
		final String group = groupId != null ? groupId.toString() : null;

		this.id = id.toString();
		this.name = name;
		this.reference = reference;
		this.businessName = businessName;
		this.businessId = businessId.toString();
		this.brandName = brandName;
		this.brandId = brand;
		this.lineName = lineName;
		this.lineId = line;
		this.groupName = groupName;
		this.groupId = group;
	}

	public static ProductReadView of(String id, String name, String reference, String businessName, String businessId,
			String brandName, String brandId, String lineName, String lineId,
			String groupName, String groupId) {
		return new ProductReadView(
				id, name, reference, businessName, businessId, brandName, brandId,
				lineName, lineId, groupName, groupId
		);
	}

	public static ProductReadView of(String id, String name, String reference, String businessId) {
		return new ProductReadView(
				id, name, reference, null, businessId, null,
				null, null, null, null, null
		);
	}

	public Product toProduct() {
		return Product.of(
				ProductId.of( id ),
				PlainName.of( name ),
				Reference.of( reference ),
				BusinessUnitId.ofNotNull( businessId ),
				ProductBrandId.of( brandId ),
				ProductLineId.of( lineId ),
				ProductGroupId.of( groupId )
		);
	}
}
