package co.icesi.pdaily.subscription.account.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.app.SalesRepAppService;
import co.icesi.pdaily.subscription.account.user.infrastructure.persistence.UserRepository;

/**
 * @author cristhiank on 16/11/19
 **/
@Dependent
public class UserValidator {
	@Inject
	UserRepository repository;
	@Inject
	SalesRepAppService salesRepAppService;

	public void validate(User user) {
		failIfDuplicatedEmail( user );
		failIfDuplicatedUserName( user );
	}

	private void failIfDuplicatedUserName(User user) {
		var found = repository.findByUserName( user.accountId(), user.username() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !existent.isPersistent() || !user.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( user.username() );
			}
		}
	}

	private void failIfDuplicatedEmail(User user) {
		var found = repository.findByEmailAndAccount( user.accountId(), user.email() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !existent.isPersistent() || !user.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( user.email() );
			}
		}
	}

	public void checkBeforeDelete(UserId userId) {
		final var exists = salesRepAppService.existsWithId( userId.text() );
		check( !exists, "No se puede eliminar el usuario, está asignado como rep. de ventas" );
	}
}
