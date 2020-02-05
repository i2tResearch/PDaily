package co.icesi.pdaily.business.structure.businessunit.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.common.model.Reference;

public class BusinessUnitDTO {
	public String id;
	public String name;
	public String reference;

	protected BusinessUnitDTO() {
	}

	private BusinessUnitDTO(String id, String name, String reference) {
		this.id = id;
		this.name = name;
		this.reference = reference;
	}

	public static BusinessUnitDTO of(String id, String name, String reference) {
		return new BusinessUnitDTO( id, name, reference );
	}

	public static BusinessUnitDTO of(BusinessUnit businessUnit) {
		final String reference = businessUnit.reference() != null ? businessUnit.reference().text() : null;
		return new BusinessUnitDTO(
				businessUnit.id().text(),
				businessUnit.name().text(),
				reference
		);
	}

	public BusinessUnit toBusinessUnit() {
		return BusinessUnit.of(
				BusinessUnitId.of( this.id ),
				PlainName.of( this.name ),
				Reference.of( this.reference )
		);
	}
}
