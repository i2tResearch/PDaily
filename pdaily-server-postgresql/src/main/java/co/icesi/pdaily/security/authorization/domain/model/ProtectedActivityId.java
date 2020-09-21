package co.icesi.pdaily.security.authorization.domain.model;

import javax.persistence.Embeddable;

import co.icesi.pdaily.common.model.SimpleStringId;

/**
 * @author andres2508 on 1/5/20
 **/
@Embeddable
public class ProtectedActivityId extends SimpleStringId {

	protected ProtectedActivityId() {
	}

	protected ProtectedActivityId(String text) {
		super( text );
	}

	public static ProtectedActivityId of(String text) {
		return new ProtectedActivityId( text );
	}
}
