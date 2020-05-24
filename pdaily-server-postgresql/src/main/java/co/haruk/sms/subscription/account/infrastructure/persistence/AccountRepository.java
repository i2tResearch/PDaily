package co.haruk.sms.subscription.account.infrastructure.persistence;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.common.model.TaxID;
import co.haruk.sms.subscription.account.domain.model.Account;

/**
 * @author cristhiank on 30/10/19
 **/
@ApplicationScoped
public class AccountRepository extends JPARepository<Account> {

	public Optional<Account> findByTaxID(TaxID taxID) {
		return findSingleWithNamedQuery( Account.findByTaxID, QueryParameter.with( "taxID", taxID ).parameters() );
	}

	public Optional<Account> findByName(PlainName name) {
		return findSingleWithNamedQuery(
				Account.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
