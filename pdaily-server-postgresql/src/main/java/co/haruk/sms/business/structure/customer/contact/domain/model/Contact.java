package co.haruk.sms.business.structure.customer.contact.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.customer.contact.role.domain.model.ContactRoleId;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.common.model.PhoneNumber;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "bs_contacts")
@NamedQuery(name = Contact.findForCustomerAsReadView, query = "SELECT new co.haruk.sms.business.structure.customer.contact.domain.model.view.ContactReadView(c.id.id, c.name.name, c.email.email, c.landlinePhone.number, c.mobilePhone.number, r.name.name, r.id.id, cu.name.name, cu.id.id, c.isSalesContact) "
		+
		"FROM Contact c INNER JOIN Customer cu ON c.customerId = cu.id LEFT JOIN ContactRole r ON c.roleId = r.id " +
		"WHERE c.tenant = :company AND c.customerId = :customerId")
@NamedQuery(name = Contact.findByIdAsReadView, query = "SELECT new co.haruk.sms.business.structure.customer.contact.domain.model.view.ContactReadView(c.id.id, c.name.name, c.email.email, c.landlinePhone.number, c.mobilePhone.number, r.name.name, r.id.id, cu.name.name, cu.id.id, c.isSalesContact) "
		+
		"FROM Contact c INNER JOIN Customer cu ON c.customerId = cu.id LEFT JOIN ContactRole r ON c.roleId = r.id " +
		"WHERE c.tenant = :company AND c.id = :id")
@NamedQuery(name = Contact.findCustomerSalesContacts, query = "SELECT new co.haruk.sms.business.structure.customer.contact.domain.model.view.ContactReadView(c.id.id, c.name.name, c.email.email, c.landlinePhone.number, c.mobilePhone.number, r.name.name, r.id.id, cu.name.name, cu.id.id, c.isSalesContact) "
		+
		"FROM Contact c INNER JOIN Customer cu ON c.customerId = cu.id LEFT JOIN ContactRole r ON c.roleId = r.id " +
		"WHERE c.tenant = :company AND cu.id = :customerId AND c.isSalesContact = TRUE ")
@NamedQuery(name = Contact.countForCustomer, query = "SELECT COUNT(c.id) FROM Contact c WHERE c.tenant = :company AND c.customerId = :customerId")
@NamedQuery(name = Contact.countForContactRole, query = "SELECT COUNT(c.id) FROM Contact c WHERE c.tenant = :company AND c.roleId = :roleId")
public class Contact extends PdailyTenantEntity<ContactId> {
	private static final String PREFIX = "Contact.";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";
	public static final String findForCustomerAsReadView = PREFIX + "findForCustomerAsReadView";
	public static final String findCustomerSalesContacts = PREFIX + "findCustomerSalesContacts";
	public static final String countForCustomer = PREFIX + "countForCustomer";
	public static final String countForContactRole = PREFIX + "countForContactRole";

	@EmbeddedId
	private ContactId id;
	@Embedded
	private PlainName name;
	@Column(name = "sales_contact")
	private boolean isSalesContact;
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
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
	private CustomerId customerId;

	protected Contact() {
	}

	private Contact(ContactId id, PlainName name, EmailAddress email, PhoneNumber landline,
			PhoneNumber mobile, ContactRoleId roleId, CustomerId customerId, boolean isSalesContact) {
		setId( id );
		setName( name );
		setEmail( email );
		setLandlinePhone( landline );
		setMobilePhone( mobile );
		setContactRoleId( roleId );
		setCustomerId( customerId );
		setSalesContact( isSalesContact );
	}

	public static Contact of(ContactId id, PlainName name, EmailAddress email, PhoneNumber landline,
			PhoneNumber mobile, ContactRoleId roleId, CustomerId customerId, boolean isSalesContact) {
		return new Contact( id, name, email, landline, mobile, roleId, customerId, isSalesContact );
	}

	public boolean isSalesContact() {
		return this.isSalesContact;
	}

	private void setSalesContact(boolean salesContact) {
		this.isSalesContact = salesContact;
	}

	private void setCustomerId(CustomerId customerId) {
		this.customerId = requireNonNull( customerId, "El contacto debe estar asociado a un cliente." );
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
		setSalesContact( contact.isSalesContact() );
	}
}
