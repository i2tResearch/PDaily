package co.haruk.sms.business.structure.customer.app;

/**
 * @author andres2508 on 26/12/19
 **/
public final class CustomerBusinessViewRequest {
	public String businessId;
	public String salesRepId;
	public String zoneId;

	protected CustomerBusinessViewRequest() {
	}

	private CustomerBusinessViewRequest(String businessId, String salesRepId, String zoneId) {
		this.businessId = businessId;
		this.salesRepId = salesRepId;
		this.zoneId = zoneId;
	}

	public static CustomerBusinessViewRequest of(String businessId, String salesRepId, String zoneId) {
		return new CustomerBusinessViewRequest( businessId, salesRepId, zoneId );
	}
}
