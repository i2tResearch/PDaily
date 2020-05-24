package co.haruk.sms.business.structure.customer.contact.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.address.domain.model.AddressRequest;
import co.haruk.sms.business.structure.customer.contact.domain.model.Contact;
import co.haruk.sms.business.structure.customer.contact.domain.model.ContactId;
import co.haruk.sms.business.structure.customer.contact.role.domain.model.ContactRoleId;
import co.haruk.sms.business.structure.customer.domain.model.CustomerId;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.common.model.PhoneNumber;

public final class ContactRequestDTO {
	public String id;
	public String name;
	public String email;
	public String landlinePhone;
	public String mobilePhone;
	public String roleId;
	public String customerId;
	public AddressRequest mainAddress;
	public boolean isSalesContact;

	protected ContactRequestDTO() {
	}

	private ContactRequestDTO(
			String id,
			String name,
			String email,
			String landlinePhone,
			String mobilePhone,
			String roleId,
			String customerId,
			boolean isSalesContact) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.landlinePhone = landlinePhone;
		this.mobilePhone = mobilePhone;
		this.roleId = roleId;
		this.customerId = customerId;
		this.isSalesContact = isSalesContact;
	}

	public static ContactRequestDTO of(
			String id,
			String name,
			String email,
			String landlinePhone,
			String mobilePhone,
			String roleId,
			String customerId,
			boolean isSalesContact) {
		return new ContactRequestDTO( id, name, email, landlinePhone, mobilePhone, roleId, customerId, isSalesContact );
	}

	Contact toContact() {
		return Contact.of(
				ContactId.of( id ),
				PlainName.of( name ),
				EmailAddress.ofNullable( email ),
				PhoneNumber.ofNullable( landlinePhone ),
				PhoneNumber.ofNullable( mobilePhone ),
				ContactRoleId.of( roleId ),
				CustomerId.ofNotNull( customerId ),
				isSalesContact
		);
	}
}
