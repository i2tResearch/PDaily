package co.icesi.pdaily.subscription.account.user.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.subscription.account.domain.model.AccountId;
import co.icesi.pdaily.subscription.account.user.domain.model.User;
import co.icesi.pdaily.subscription.account.user.domain.model.UserName;

/**
 * @author cristhiank on 16/11/19
 **/
@ApplicationScoped
public class UserRepository extends JPARepository<User> {

	public Optional<User> findByUserName(AccountId accountId, UserName username) {
		return findSingleWithNamedQuery(
				User.findByUserName,
				QueryParameter.with( "username", username ).and( "accountId", accountId ).parameters()
		);
	}

	public Optional<User> findByEmailAndAccount(AccountId accountId, EmailAddress email) {
		return findSingleWithNamedQuery(
				User.findByEmailAndAccount,
				QueryParameter.with( "email", email ).and( "accountId", accountId ).parameters()
		);
	}

	public List<User> findForAccount(AccountId accountId) {
		return findWithNamedQuery(
				User.findByAccount,
				QueryParameter.with( "accountId", accountId ).parameters()
		);
	}

	public boolean existsEmail(EmailAddress email) {
		return executeAggregateQuery(
				User.existsEmail,
				QueryParameter.with( "email", email ).parameters()
		).intValue() > 0;
	}

	public boolean existsUserName(UserName userName) {
		return executeAggregateQuery(
				User.existsUsername,
				QueryParameter.with( "username", userName ).parameters()
		).intValue() > 0;
	}
}
