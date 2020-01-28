package co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view;

import java.util.UUID;

import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRep;

/**
 * @author cristhiank on 25/11/19
 **/
public final class SalesRepReadView {
	public final String id;
	public final String fullName;
	public final String reference;
	public final String subsidiaryId;

	public SalesRepReadView(UUID id, String fullName, String reference, UUID subsidiaryId) {
		this( id.toString(), fullName, reference, subsidiaryId.toString() );
	}

	public SalesRepReadView(String id, String fullName, String reference, String subsidiaryId) {
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
}
