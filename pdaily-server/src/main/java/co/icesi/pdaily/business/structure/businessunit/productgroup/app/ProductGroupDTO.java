package co.icesi.pdaily.business.structure.businessunit.productgroup.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroup;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.icesi.pdaily.common.model.Reference;

public class ProductGroupDTO {
	public String id;
	public String name;
	public String reference;
	public String businessId;

	protected ProductGroupDTO() {
	}

	private ProductGroupDTO(String id, String name, String reference, String businessId) {
		this.id = id;
		this.name = name;
		this.reference = reference;
		this.businessId = businessId;
	}

	public static ProductGroupDTO of(String id, String name, String reference, String businessId) {
		return new ProductGroupDTO( id, name, reference, businessId );
	}

	public static ProductGroupDTO of(ProductGroup productGroup) {
		final String reference = productGroup.reference() != null ? productGroup.reference().text() : null;
		return new ProductGroupDTO(
				productGroup.id().text(),
				productGroup.getName().text(),
				reference,
				productGroup.getBusinessUnit().text()
		);
	}

	public ProductGroup toProductGroup() {
		return ProductGroup.of(
				ProductGroupId.of( this.id ),
				PlainName.of( this.name ),
				Reference.of( this.reference ),
				BusinessUnitId.ofNotNull( this.businessId )
		);
	}
}
