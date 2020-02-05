package co.icesi.pdaily.business.structure.customer.contact.role.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.customer.contact.role.domain.model.ContactRole;
import co.icesi.pdaily.business.structure.customer.contact.role.domain.model.ContactRoleId;

public class ContactRoleDTO {
	public String id;
	public String name;

	protected ContactRoleDTO() {
	}

	private ContactRoleDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ContactRoleDTO of(String id, String name) {
		return new ContactRoleDTO( id, name );
	}

	public static ContactRoleDTO of(ContactRole role) {
		return new ContactRoleDTO( role.id().text(), role.name().text() );
	}

	public ContactRole toContactRole() {
		return ContactRole.of(
				ContactRoleId.of( this.id ),
				PlainName.of( this.name )
		);
	}
}
