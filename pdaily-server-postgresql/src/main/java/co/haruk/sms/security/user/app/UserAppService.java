package co.haruk.sms.security.user.app;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.security.user.domain.model.IEncryptionProvider;
import co.haruk.sms.security.user.domain.model.User;
import co.haruk.sms.security.user.domain.model.UserId;
import co.haruk.sms.security.user.domain.model.UserName;
import co.haruk.sms.security.user.domain.model.UserValidator;
import co.haruk.sms.security.user.infrastructure.persistence.UserRepository;
import co.haruk.sms.subscription.account.domain.model.AccountId;

/**
 * @author andres2508 on 16/11/19
 **/
@ApplicationScoped
public class UserAppService {
	private static final Logger log = Logger.getLogger( UserAppService.class.getName() );
	@Inject
	UserRepository repository;
	@Inject
	UserValidator validator;
	@Inject
	IEncryptionProvider encryptionProvider;

	public List<UserDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, UserDTO::of );
	}

	public List<UserDTO> findAllPaged(int first, int max) {
		var all = repository.findAllPaged( first, max );
		return StreamUtils.map( all, UserDTO::of );
	}

	public UserDTO findByIdOrFail(String id) {
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
			changed.setPassword( dto.password, encryptionProvider );
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
		user.setPassword( newPassword, encryptionProvider );
		repository.update( user );
	}

	public List<UserDTO> findForAccount(String accountId) {
		final var found = repository.findForAccount( AccountId.ofNotNull( accountId ) );
		return StreamUtils.map( found, UserDTO::of );
	}

	public boolean existsEmail(String email) {
		return repository.existsEmail( EmailAddress.of( email ) );
	}

	public boolean existsUserName(String username) {
		return repository.existsUserName( UserName.of( username ) );
	}

	public int countAll() {
		return repository.countAll();
	}

	public List<UserDTO> searchPerson(String term, int first, int max) {
		final var found = repository.searchPerson( term, first, max );
		return StreamUtils.map( found, UserDTO::of );
	}

	public boolean isValidPassword(String username, String rawPassword) {
		final var found = repository.findByUserNameOrFail( UserName.of( username ) );
		return encryptionProvider.verify( rawPassword, found.password() );
	}

	public UserDTO findByUserNameOrFail(String username) {
		final var found = repository.findByUserNameOrFail( UserName.of( username ) );
		return UserDTO.of( found );
	}

	@Transactional
	public void createDefaultAdmin() {
		if ( !existsUserName( User.ADMIN ) ) {
			final var admin = User.defaultAdmin();
			admin.setPassword( User.ADMIN, encryptionProvider );
			validator.validate( admin );
			repository.create( admin );
			log.warning( "Created default admin" );
		}
	}
}
