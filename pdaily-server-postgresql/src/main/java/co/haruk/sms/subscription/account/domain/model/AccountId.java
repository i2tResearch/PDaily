package co.haruk.sms.subscription.account.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

/**
 * @author andres2508 on 30/10/19
 **/
@Embeddable
public class AccountId extends Identity {

	protected AccountId() {
	}

	private AccountId(String id) {
		super( id );
	}

	private AccountId(UUID id) {
		super( id );
	}

	public static AccountId ofNotNull(String id) {
		return new AccountId( id );
	}

	public static AccountId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new AccountId( id );
	}

	public static AccountId generateNew() {
		return of( UUID.randomUUID() );
	}

	public static AccountId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new AccountId( id );
	}
}
