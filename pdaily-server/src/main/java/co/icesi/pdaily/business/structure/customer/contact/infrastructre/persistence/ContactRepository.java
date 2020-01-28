package co.icesi.pdaily.business.structure.customer.contact.infrastructre.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.customer.contact.domain.model.Contact;
import co.icesi.pdaily.business.structure.customer.contact.domain.model.ContactId;
import co.icesi.pdaily.business.structure.customer.contact.domain.model.view.ContactReadView;
import co.icesi.pdaily.business.structure.customer.contact.role.domain.model.ContactRoleId;
import co.icesi.pdaily.business.structure.customer.domain.model.CustomerId;

@ApplicationScoped
public class ContactRepository extends JPARepository<Contact> {

	public List<ContactReadView> findForCustomerAsReadView(CustomerId customerId) {
		requireNonNull( customerId );
		return findOtherWithNamedQuery(
				ContactReadView.class,
				Contact.findForCustomerAsReadView,
				QueryParameter.with( "customerId", customerId ).parameters()
		);
	}

	public ContactReadView findAsReadViewOrFail(ContactId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				ContactReadView.class,
				Contact.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public boolean existsAnyForCustomer(CustomerId customerId) {
		requireNonNull( customerId );
		final int count = executeAggregateQuery(
				Contact.countForCustomer,
				QueryParameter.with( "customerId", customerId ).parameters()
		).intValue();
		return count > 0;
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
