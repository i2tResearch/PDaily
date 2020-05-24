package co.haruk.sms.business.structure.address.domain.model;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 2/12/19
 **/
@Embeddable
public class ReferencedId extends Identity {
	protected ReferencedId() {
	}

	private ReferencedId(String id) {
		super( id );
	}

	public static ReferencedId ofNotNull(String id) {
		return new ReferencedId( id );
	}

	public static ReferencedId ofNotNull(Identity model) {
		return new ReferencedId( model.text() );
	}
}
