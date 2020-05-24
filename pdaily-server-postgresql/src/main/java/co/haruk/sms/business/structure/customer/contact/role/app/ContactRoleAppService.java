package co.haruk.sms.business.structure.customer.contact.role.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.customer.contact.role.domain.model.ContactRole;
import co.haruk.sms.business.structure.customer.contact.role.domain.model.ContactRoleId;
import co.haruk.sms.business.structure.customer.contact.role.domain.model.ContactRoleValidator;
import co.haruk.sms.business.structure.customer.contact.role.infrastructure.persistence.ContactRoleRepository;

@ApplicationScoped
public class ContactRoleAppService {
	@Inject
	ContactRoleRepository repository;
	@Inject
	ContactRoleValidator validator;

	@Transactional
	public ContactRoleDTO saveContactRole(ContactRoleDTO dto) {
		final var changed = dto.toContactRole();
		ContactRole saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ContactRoleId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return ContactRoleDTO.of( saved );
	}

	public ContactRoleDTO findOrFail(String id) {
		final var found = repository.findOrFail( ContactRoleId.ofNotNull( id ) );
		return ContactRoleDTO.of( found );
	}

	public List<ContactRoleDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, ContactRoleDTO::of );
	}

	@Transactional
	public void deleteContactRole(String id) {
		final var roleId = ContactRoleId.ofNotNull( id );
		repository.delete( roleId );
	}
}
