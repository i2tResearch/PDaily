package co.haruk.sms.business.structure.businessunit.productline.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLine;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.haruk.sms.common.model.Reference;

public final class ProductLineDTO {
	public String id;
	public String name;
	public String reference;
	public String businessId;

	protected ProductLineDTO() {
	}

	private ProductLineDTO(String id, String name, String reference, String businessId) {
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.businessId = businessId;
	}

	public static ProductLineDTO of(String id, String name, String reference, String businessId) {
		return new ProductLineDTO( id, name, reference, businessId );
	}

	public static ProductLineDTO of(ProductLine productLine) {
		final String reference = productLine.reference() != null ? productLine.reference().text() : null;
		return new ProductLineDTO(
				productLine.id().text(),
				productLine.name().text(),
				reference,
				productLine.businessUnit().text()
		);
	}

	public ProductLine toProductLine() {
		return ProductLine.of(
				ProductLineId.of( this.id ),
				PlainName.of( this.name ),
				Reference.of( this.reference ),
				BusinessUnitId.ofNotNull( this.businessId )
		);
	}
}
