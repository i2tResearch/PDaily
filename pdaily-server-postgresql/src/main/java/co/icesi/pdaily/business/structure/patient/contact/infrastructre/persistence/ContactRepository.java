package co.icesi.pdaily.business.structure.patient.contact.infrastructre.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.Contact;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.ContactId;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.view.ContactReadView;
import co.icesi.pdaily.business.structure.patient.contact.role.domain.model.ContactRoleId;

@ApplicationScoped
public class ContactRepository extends JPARepository<Contact> {

	public ContactReadView findAsReadViewOrFail(ContactId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				ContactReadView.class,
				Contact.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public boolean existsAnyForContactRole(ContactRoleId roleId) {
		requireNonNull( roleId );
		final int count = executeAggregateQuery(
				Contact.countForContactRole,
				QueryParameter.with( "roleId", roleId ).parameters()
		).intValue();
		return count > 0;
	}
}
