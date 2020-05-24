package co.haruk.sms.business.structure.customer.domain.model.view;

import java.util.UUID;

/**
 * @author cristhiank on 26/12/19
 **/
public final class CustomerBusinessReadView {
	public final String customerId;
	public final String businessId;
	public final String businessName;
	public final String salesRepId;
	public final String zoneId;
	public final String zoneName;
	public String salesRepName;

	public CustomerBusinessReadView(
			UUID customerId,
			UUID salesRepId,
			UUID businessId,
			String businessName,
			UUID zoneId,
			String zoneName) {
		this.customerId = customerId.toString();
		this.businessId = businessId.toString();
		this.salesRepId = salesRepId.toString();
		this.businessName = businessName;
		this.zoneId = zoneId != null ? zoneId.toString() : null;
		this.zoneName = zoneName;
	}
}
