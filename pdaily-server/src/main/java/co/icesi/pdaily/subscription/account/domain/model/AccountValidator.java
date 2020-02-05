package co.icesi.pdaily.subscription.account.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.subscription.account.infrastructure.persistence.AccountRepository;

/**
 * @author cristhiank on 14/11/19
 **/
@Dependent
public class AccountValidator {

	@Inject
	AccountRepository repository;

	public void validate(Account account) {
		failIfDuplicatedTaxID( account );
		failIfDuplicatedName( account );
	}

	private void failIfDuplicatedName(Account account) {
		Optional<Account> found = repository.findByName( account.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !account.isPersistent() || !existent.equals( account );
			if ( mustFail ) {
				throw new DuplicatedRecordException( account.name() );
			}
		}
	}

	private void failIfDuplicatedTaxID(Account account) {
		Optional<Account> found = repository.findByTaxID( account.taxID() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !account.isPersistent() || !existent.equals( account );
			if ( mustFail ) {
				throw new DuplicatedRecordException( account.taxID() );
			}
		}
	}
}
