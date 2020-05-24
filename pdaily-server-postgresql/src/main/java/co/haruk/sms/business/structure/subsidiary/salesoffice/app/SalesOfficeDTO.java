package co.haruk.sms.business.structure.subsidiary.salesoffice.app;

import java.io.Serializable;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesoffice.domain.model.SalesOffice;
import co.haruk.sms.business.structure.subsidiary.salesoffice.domain.model.SalesOfficeId;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 21/11/19
 **/
public final class SalesOfficeDTO implements Serializable {
	public String id;
	public String reference;
	public String name;
	public String subsidiaryId;

	protected SalesOfficeDTO() {
	}

	private SalesOfficeDTO(String id, String reference, String name, String subsidiaryId) {
		this.id = id;
		this.reference = reference;
		this.name = name;
		this.subsidiaryId = subsidiaryId;
	}

	public static SalesOfficeDTO of(SalesOffice office) {
		final String reference = office.reference() != null ? office.reference().text() : null;
		return of(
				office.id().text(),
				reference,
				office.name().text(),
				office.subsidiaryId().text()
		);
	}

	public static SalesOfficeDTO of(String id, String reference, String name, String subsidiaryId) {
		return new SalesOfficeDTO( id, reference, name, subsidiaryId );
	}

	public SalesOffice toSalesOffice() {
		return SalesOffice.of(
				SalesOfficeId.of( id ),
				Reference.of( reference ),
				PlainName.of( name ),
				SubsidiaryId.ofNotNull( subsidiaryId )
		);
	}
}
