package co.icesi.pdaily.business.structure.patient.contact.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.address.domain.model.AddressRequest;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.Contact;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.ContactId;
import co.icesi.pdaily.business.structure.patient.contact.role.domain.model.ContactRoleId;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.common.model.PhoneNumber;

public final class ContactRequestDTO {
	public String id;
	public String name;
	public String email;
	public String landlinePhone;
	public String mobilePhone;
	public String roleId;
	public AddressRequest mainAddress;

	protected ContactRequestDTO() {
	}

	private ContactRequestDTO(
			String id,
			String name,
			String email,
			String landlinePhone,
			String mobilePhone,
			String roleId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.landlinePhone = landlinePhone;
		this.mobilePhone = mobilePhone;
		this.roleId = roleId;
	}

	public static ContactRequestDTO of(
			String id,
			String name,
			String email,
			String landlinePhone,
			String mobilePhone,
			String roleId) {
		return new ContactRequestDTO( id, name, email, landlinePhone, mobilePhone, roleId );
	}

	Contact toContact() {
		return Contact.of(
				ContactId.of( id ),
				PlainName.of( name ),
				EmailAddress.ofNullable( email ),
				PhoneNumber.ofNullable( landlinePhone ),
				PhoneNumber.ofNullable( mobilePhone ),
				ContactRoleId.of( roleId )
		);
	}
}
