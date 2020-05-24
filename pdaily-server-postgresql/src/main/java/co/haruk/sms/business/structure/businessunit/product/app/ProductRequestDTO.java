package co.haruk.sms.business.structure.businessunit.product.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.product.domain.model.Product;
import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductId;
import co.haruk.sms.business.structure.businessunit.productbrand.domain.model.ProductBrandId;
import co.haruk.sms.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.haruk.sms.common.model.Reference;

public class ProductRequestDTO {
	public String id;
	public String name;
	public String reference;
	public String businessId;
	public String brandId;
	public String lineId;
	public String groupId;

	protected ProductRequestDTO() {
	}

	private ProductRequestDTO(String id, String name, String reference, String businessId,
			String brandId, String lineId, String groupId) {
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.businessId = businessId;
		this.brandId = brandId;
		this.lineId = lineId;
		this.groupId = groupId;
	}

	public static ProductRequestDTO of(String id, String name, String reference, String businessId,
			String brandId, String lineId, String groupId) {
		return new ProductRequestDTO( id, name, reference, businessId, brandId, lineId, groupId );
	}

	public static ProductRequestDTO of(Product product) {
		final String reference = product.reference() != null ? product.reference().text() : null;
		final String brandId = product.brandId() != null ? product.brandId().text() : null;
		final String lineId = product.lineId() != null ? product.lineId().text() : null;
		final String groupId = product.groupId() != null ? product.groupId().text() : null;

		return new ProductRequestDTO(
				product.id().text(),
				product.name().text(),
				reference,
				product.businessUnit().text(),
				brandId,
				lineId,
				groupId
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
