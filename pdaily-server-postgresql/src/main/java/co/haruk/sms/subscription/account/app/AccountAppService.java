package co.haruk.sms.subscription.account.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.security.user.app.UserAppService;
import co.haruk.sms.subscription.account.domain.model.Account;
import co.haruk.sms.subscription.account.domain.model.AccountId;
import co.haruk.sms.subscription.account.domain.model.AccountValidator;
import co.haruk.sms.subscription.account.infrastructure.persistence.AccountRepository;

/**
 * @author andres2508 on 30/10/19
 **/
@ApplicationScoped
public class AccountAppService {
	@Inject
	AccountRepository repository;
	@Inject
	AccountValidator validator;
	@Inject
	UserAppService userAppService;

	public List<AccountDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, AccountDTO::of );
	}

	@Transactional
	public AccountDTO saveAccount(AccountDTO dto) {
		Account changed = dto.toSubscription();
		Account saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			original.setTaxID( changed.taxID() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( AccountId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return AccountDTO.of( saved );
	}

	@Transactional
	public void deleteAccount(String id) {
		repository.delete( AccountId.ofNotNull( id ) );
	}

	public AccountDTO findById(String id) {
		Account found = repository.findOrFail( AccountId.ofNotNull( id ) );
		return AccountDTO.of( found );
	}

	public List<AccountDTO> forCurrentUser() {
		final var accountId = userAppService.findByIdOrFail( HarukSession.currentUser().id ).accountId;
		final var account = findById( accountId );
		return List.of( account );
	}
}
