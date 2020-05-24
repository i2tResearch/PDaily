package co.haruk.sms.security.user.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.security.user.domain.model.User;
import co.haruk.sms.security.user.domain.model.UserName;
import co.haruk.sms.subscription.account.domain.model.AccountId;

/**
 * @author cristhiank on 16/11/19
 **/
@ApplicationScoped
public class UserRepository extends JPARepository<User> {

	public List<User> findAllPaged(int first, int max) {
		return findWithNamedQuery( User.findAll, first, max );
	}

	public Optional<User> findByUserName(UserName username) {
		return findSingleWithNamedQuery(
				User.findByUserName,
				QueryParameter.with( "username", username ).parameters()
		);
	}

	public User findByUserNameOrFail(UserName username) {
		return findSingleWithNamedQuery(
				User.findByUserName,
				QueryParameter.with( "username", username ).parameters()
		).orElseThrow( () -> new EntityNotFoundException( username.text() ) );
	}

	public Optional<User> findByEmail(EmailAddress email) {
		return findSingleWithNamedQuery(
				User.findByEmail,
				QueryParameter.with( "email", email ).parameters()
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

	public int countAll() {
		return executeAggregateQuery( User.countAll ).intValue();
	}

	public List<User> searchPerson(String term, int first, int max) {
		final var param = "%" + requireNonNull( term ) + "%";
		return findWithNamedQuery(
				User.searchByTerm,
				QueryParameter.with( "term", param ).parameters(),
				first,
				max
		);
	}
}
