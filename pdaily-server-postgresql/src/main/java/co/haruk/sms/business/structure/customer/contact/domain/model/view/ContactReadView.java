package co.haruk.sms.business.structure.customer.contact.domain.model.view;

import java.util.UUID;

import co.haruk.sms.business.structure.address.domain.model.view.AddressReadView;

public final class ContactReadView {
	public String id;
	public String name;
	public String email;
	public String landlinePhone;
	public String mobilePhone;
	public String roleName;
	public String roleId;
	public String customerName;
	public String customerId;
	public AddressReadView mainAddress;
	public boolean isSalesContact;

	protected ContactReadView() {
	}

	public ContactReadView(
			UUID id,
			String name,
			String email,
			String landlinePhone,
			String mobilePhone,
			String roleName,
			UUID roleId,
			String customerName,
			UUID customerId,
			boolean isSalesContact) {
		this.id = id.toString();
		this.name = name;
		this.email = email;
		this.landlinePhone = landlinePhone;
		this.mobilePhone = mobilePhone;
		this.roleName = roleName;
		this.roleId = roleId != null ? roleId.toString() : null;
		this.customerName = customerName;
		this.customerId = customerId.toString();
		this.isSalesContact = isSalesContact;
	}
}