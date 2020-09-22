package co.icesi.pdaily.business.structure.patient.contact.app;

import static co.haruk.core.domain.model.guards.Guards.require;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.infrastructure.jta.JTAUtils;
import co.icesi.pdaily.business.structure.address.domain.model.AddressService;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.Contact;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.ContactId;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.view.ContactReadView;
import co.icesi.pdaily.business.structure.patient.contact.infrastructre.persistence.ContactRepository;

@ApplicationScoped
public class ContactAppService {
	@Inject
	ContactRepository repository;
	@Inject
	AddressService addressService;
	@Inject
	UserTransaction transaction;

	public ContactReadView saveContact(ContactRequestDTO dto) {
		final var changed = dto.toContact();
		try {
			transaction.begin();
			Contact saved = saveContact( changed );
			saveMainAddressFor( saved, dto );
			transaction.commit();
			return findContactAsReadView( saved.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	private ContactReadView findContactAsReadView(ContactId id) {
		final var found = repository.findAsReadViewOrFail( id );
		found.mainAddress = addressService.findMainAddressFor( id ).orElse( null );
		return found;
	}

	private Contact saveContact(Contact changed) {
		Contact saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			saved = repository.update( original );
		} else {
			changed.setId( ContactId.generateNew() );
			saved = repository.create( changed );
		}
		return saved;
	}

	private void saveMainAddressFor(Contact saved, ContactRequestDTO dto) {
		final boolean hasMainAddress = dto.mainAddress != null;
		if ( hasMainAddress ) {
			Guards.require( saved.isPersistent(), "No se puede crear la dirección principal, el contacto no ha sido grabado" );
			dto.mainAddress.referencedId = saved.id().text();
			addressService.saveMainForReferenced( dto.mainAddress );
		}
	}

	public ContactReadView findById(String id) {
		return findContactAsReadView( ContactId.ofNotNull( id ) );
	}

	@Transactional
	public void deleteById(String id) {
		final ContactId contactId = ContactId.ofNotNull( id );
		repository.delete( contactId );
		addressService.deleteAllForReferenced( contactId );
	}
}
