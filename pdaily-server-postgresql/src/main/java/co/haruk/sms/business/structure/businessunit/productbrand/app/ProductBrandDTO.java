package co.haruk.sms.business.structure.businessunit.productbrand.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.productbrand.domain.model.ProductBrand;
import co.haruk.sms.business.structure.businessunit.productbrand.domain.model.ProductBrandId;

public class ProductBrandDTO {
	public String id;
	public String name;
	public String businessId;

	protected ProductBrandDTO() {
	}

	private ProductBrandDTO(String id, String name, String businessId) {
		this.id = id;
		this.name = name;
		this.businessId = businessId;
	}

	public static ProductBrandDTO of(String id, String name, String businessId) {
		return new ProductBrandDTO( id, name, businessId );
	}

	public static ProductBrandDTO of(ProductBrand productBrand) {
		return new ProductBrandDTO(
				productBrand.id().text(),
				productBrand.name().text(),
				productBrand.businessUnitId().text()
		);
	}

	public ProductBrand toProductBrand() {
		return ProductBrand.of(
				ProductBrandId.of( this.id ),
				PlainName.of( this.name ),
				BusinessUnitId.ofNotNull( this.businessId )
		);
	}
}
