package co.icesi.pdaily.business.structure.patient.contact.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.patient.contact.role.domain.model.ContactRoleId;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.common.model.PhoneNumber;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_contacts")
@NamedQuery(name = Contact.findByIdAsReadView, query = "SELECT new co.icesi.pdaily.business.structure.patient.contact.domain.model.view.ContactReadView(c.id.id, c.name.name, c.email.email, c.landlinePhone.number, c.mobilePhone.number, r.name.name, r.id.id) "
		+
		"FROM Contact c LEFT JOIN ContactRole r ON c.roleId = r.id " +
		"WHERE c.tenant = :company AND c.id = :id")
@NamedQuery(name = Contact.countForContactRole, query = "SELECT COUNT(c.id) FROM Contact c WHERE c.tenant = :company AND c.roleId = :roleId")
public class Contact extends PdailyTenantEntity<ContactId> {
	private static final String PREFIX = "Contact.";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";
	public static final String countForContactRole = PREFIX + "countForContactRole";

	@EmbeddedId
	private ContactId id;
	@Embedded
	private PlainName name;
	@Embedded
	private EmailAddress email;
	@Embedded
	@AttributeOverride(name = "number", column = @Column(name = "landline_phone"))
	private PhoneNumber landlinePhone;
	@Embedded
	@AttributeOverride(name = "number", column = @Column(name = "mobile_phone"))
	private PhoneNumber mobilePhone;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "role_id"))
	private ContactRoleId roleId;

	protected Contact() {
	}

	private Contact(ContactId id, PlainName name, EmailAddress email, PhoneNumber landline,
			PhoneNumber mobile, ContactRoleId roleId) {
		setId( id );
		setName( name );
		setEmail( email );
		setLandlinePhone( landline );
		setMobilePhone( mobile );
		setContactRoleId( roleId );
	}

	public static Contact of(ContactId id, PlainName name, EmailAddress email, PhoneNumber landline,
			PhoneNumber mobile, ContactRoleId roleId) {
		return new Contact( id, name, email, landline, mobile, roleId );
	}

	private void setContactRoleId(ContactRoleId roleId) {
		this.roleId = roleId;
	}

	private void setMobilePhone(PhoneNumber mobile) {
		this.mobilePhone = mobile;
	}

	private void setLandlinePhone(PhoneNumber landline) {
		this.landlinePhone = landline;
	}

	private void setEmail(EmailAddress email) {
		this.email = requireNonNull( email, "El correo del contacto es obligatorio" );
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del contacto es obligatorio." );
	}

	@Override
	public ContactId id() {
		return this.id;
	}

	@Override
	public void setId(ContactId id) {
		this.id = id;
	}

	public void updateFrom(Contact contact) {
		setName( contact.name );
		setEmail( contact.email );
		setLandlinePhone( contact.landlinePhone );
		setMobilePhone( contact.mobilePhone );
		setContactRoleId( contact.roleId );
	}
}
