package co.haruk.sms.security.authorization.domain.model;

import javax.persistence.Embeddable;

import co.haruk.sms.common.model.SimpleStringId;

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
