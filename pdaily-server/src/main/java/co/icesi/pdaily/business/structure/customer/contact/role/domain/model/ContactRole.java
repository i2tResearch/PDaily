package co.icesi.pdaily.business.structure.customer.contact.role.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_contact_roles")
@NamedQuery(name = ContactRole.findByName, query = "SELECT f FROM ContactRole f WHERE UPPER(f.name.name) = UPPER(:name) AND f.tenant = :company")
public class ContactRole extends PdailyTenantEntity<ContactRoleId> {
	private static final String PREFIX = "ContactRole.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private ContactRoleId id;
	@Embedded
	private PlainName name;

	protected ContactRole() {
	}

	private ContactRole(ContactRoleId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public PlainName name() {
		return this.name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del rol es requerido" );
	}

	public static ContactRole of(ContactRoleId id, PlainName name) {
		return new ContactRole( id, name );
	}

	@Override
	public ContactRoleId id() {
		return id;
	}

	@Override
	public void setId(ContactRoleId id) {
		this.id = id;
	}

	public void updateFrom(ContactRole contact) {
		setName( contact.name );
	}

}
