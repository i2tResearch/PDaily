package co.haruk.sms.subscription.account.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.TaxID;
import co.haruk.sms.subscription.account.domain.model.Account;
import co.haruk.sms.subscription.account.domain.model.AccountId;

/**
 * @author cristhiank on 30/10/19
 **/
public final class AccountDTO {
	public String id;
	public String taxID;
	public String name;

	protected AccountDTO() {
	}

	private AccountDTO(String id, String name, String taxID) {
		this.id = id;
		this.name = name;
		this.taxID = taxID;
	}

	static AccountDTO of(Account account) {
		return of( account.id().text(), account.name().text(), account.taxID().text() );
	}

	public static AccountDTO of(String id, String name, String taxID) {
		return new AccountDTO( id, name, taxID );
	}

	Account toSubscription() {
		return Account.of( AccountId.of( id ), PlainName.of( name ), TaxID.of( taxID ) );
	}
}
