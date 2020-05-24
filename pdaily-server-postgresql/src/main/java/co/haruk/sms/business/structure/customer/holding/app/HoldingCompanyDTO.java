package co.haruk.sms.business.structure.customer.holding.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompany;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;

/**
 * @author andres2508 on 1/12/19
 **/
public final class HoldingCompanyDTO {
	public String id;
	public String subsidiaryId;
	public String name;

	protected HoldingCompanyDTO() {
	}

	private HoldingCompanyDTO(String id, String subsidiaryId, String name) {
		this.id = id;
		this.subsidiaryId = subsidiaryId;
		this.name = name;
	}

	public static HoldingCompanyDTO of(String id, String subsidiaryId, String name) {
		return new HoldingCompanyDTO( id, subsidiaryId, name );
	}

	public static HoldingCompanyDTO of(HoldingCompany company) {
		return new HoldingCompanyDTO( company.id().text(), company.subsidiaryId().text(), company.name().text() );
	}

	public HoldingCompany toHoldingCompany() {
		return HoldingCompany.of(
				HoldingCompanyId.of( id ),
				SubsidiaryId.ofNotNull( subsidiaryId ),
				PlainName.of( name )
		);
	}
}
