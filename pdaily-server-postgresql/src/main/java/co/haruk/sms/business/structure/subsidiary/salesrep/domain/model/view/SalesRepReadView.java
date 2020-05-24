package co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view;

import java.util.List;
import java.util.UUID;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.businessunit.app.BusinessUnitDTO;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 25/11/19
 **/
public final class SalesRepReadView {
	public final String id;
	public final String fullName;
	public final String reference;
	public final String subsidiaryId;
	public List<BusinessUnitDTO> businessUnits;

	public SalesRepReadView(UUID id, String fullName, String reference, UUID subsidiaryId) {
		this( id.toString(), fullName, reference, subsidiaryId.toString() );
	}

	public SalesRepReadView(String id, String fullName, String reference,
			String subsidiaryId) {
		this.id = id;
		this.fullName = fullName;
		this.reference = reference;
		this.subsidiaryId = subsidiaryId;
	}

	public static SalesRepReadView of(String id, String fullName, String reference, String subsidiaryId) {
		return new SalesRepReadView( id, fullName, reference, subsidiaryId );
	}

	public static SalesRepReadView of(SalesRep rep) {
		final String finalRef = rep.reference() != null ? rep.reference().text() : null;
		return of( rep.id().text(), rep.fullName().text(), finalRef, rep.subsidiaryId().text() );
	}

	public SalesRep toSalesRep() {
		return SalesRep.of(
				SalesRepId.ofNotNull( id ),
				SubsidiaryId.of( subsidiaryId ),
				PlainName.of( fullName ),
				Reference.of( reference )
		);
	}
}
