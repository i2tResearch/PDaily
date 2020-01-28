package co.icesi.pdaily.subscription.account.user.app;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.security.CryptoService;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.subscription.account.domain.model.AccountId;
import co.icesi.pdaily.subscription.account.user.domain.model.Password;
import co.icesi.pdaily.subscription.account.user.domain.model.User;
import co.icesi.pdaily.subscription.account.user.domain.model.UserId;
import co.icesi.pdaily.subscription.account.user.domain.model.UserName;
import co.icesi.pdaily.subscription.account.user.domain.model.UserValidator;
import co.icesi.pdaily.subscription.account.user.infrastructure.persistence.UserRepository;

/**
 * @author cristhiank on 16/11/19
 **/
@ApplicationScoped
public class UserAppService {
	@Inject
	UserRepository repository;
	@Inject
	UserValidator validator;

	public List<UserDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, UserDTO::of );
	}

	public UserDTO findById(String id) {
		var user = repository.findOrFail( UserId.ofNotNull( id ) );
		return UserDTO.of( user );
	}

	@Transactional
	public UserDTO saveUser(UserDTO dto) {
		final User changed = dto.toUser();
		User saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			requireNonNull( dto.password, "La contraseña es requerida" );
			var encrypted = CryptoService.encrypt( dto.password );
			changed.setPassword( Password.of( encrypted ) );
			changed.setId( UserId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return UserDTO.of( saved );
	}

	@Transactional
	public void deleteUser(String id) {
		final UserId userId = UserId.ofNotNull( id );
		validator.checkBeforeDelete( userId );
		repository.delete( userId );
	}

	@Transactional
	public void resetPassword(String userId, String newPassword) {
		var user = repository.findOrFail( UserId.ofNotNull( userId ) );
		var encrypted = CryptoService.encrypt( newPassword );
		user.setPassword( Password.of( encrypted ) );
		repository.update( user );
	}

	public List<UserDTO> findForAccount(String accountId) {
		List<User> found = repository.findForAccount( AccountId.ofNotNull( accountId ) );
		return StreamUtils.map( found, UserDTO::of );
	}

	public boolean existsEmail(String email) {
		return repository.existsEmail( EmailAddress.of( email ) );
	}

	public boolean existsUserName(String username) {
		return repository.existsUserName( UserName.of( username ) );
	}
}
