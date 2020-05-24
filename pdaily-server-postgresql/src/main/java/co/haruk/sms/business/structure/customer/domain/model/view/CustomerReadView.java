package co.haruk.sms.business.structure.customer.domain.model.view;

import java.util.UUID;

import co.haruk.sms.business.structure.address.domain.model.view.AddressReadView;
import co.haruk.sms.business.structure.customer.domain.model.CustomerType;
import co.haruk.sms.common.model.ActiveInactiveState;

/**
 * @author cristhiank on 9/12/19
 **/
public final class CustomerReadView {
	public String id;
	public String taxID;
	public String mainEmailAddress;
	public String name;
	public String subsidiaryId;
	public String subsidiaryName;
	public String holdingId;
	public String holdingName;
	public String reference;
	public boolean active;
	public String type;
	public AddressReadView mainAddress;

	protected CustomerReadView() {
	}

	public CustomerReadView(
			UUID id,
			String taxID,
			String mainEmailAddress,
			String name,
			UUID holdingId,
			String holdingName,
			UUID subsidiaryId,
			String subsidiaryName,
			String reference,
			ActiveInactiveState state,
			CustomerType type) {
		this.id = id.toString();
		this.taxID = taxID;
		this.mainEmailAddress = mainEmailAddress;
		this.name = name;
		this.holdingId = holdingId != null ? holdingId.toString() : null;
		this.holdingName = holdingName;
		this.active = state == ActiveInactiveState.ACTIVE;
		this.type = type.name();
		this.subsidiaryId = subsidiaryId.toString();
		this.subsidiaryName = subsidiaryName;
		this.reference = reference;
	}
}
