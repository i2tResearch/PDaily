package co.icesi.pdaily.security.authorization.domain.model;

import javax.persistence.Embeddable;

import co.icesi.pdaily.common.model.SimpleStringId;

/**
 * @author andres2508 on 1/5/20
 **/
@Embeddable
public class AuthorizationGroupId extends SimpleStringId {

	protected AuthorizationGroupId() {
	}

	protected AuthorizationGroupId(String text) {
		super( text );
	}

	public static AuthorizationGroupId of(String text) {
		return new AuthorizationGroupId( text );
	}
}
