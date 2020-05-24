package co.haruk.sms.business.structure.subsidiary.salesrep.app;

/**
 * @author cristhiank on 25/11/19
 **/
public final class SalesRepRequestDTO {
	public String reference;
	public String subsidiaryId;

	protected SalesRepRequestDTO() {
	}

	private SalesRepRequestDTO(String reference, String subsidiaryId) {
		this.reference = reference;
		this.subsidiaryId = subsidiaryId;
	}

	public static SalesRepRequestDTO of(String reference, String subsidiaryId) {
		return new SalesRepRequestDTO( reference, subsidiaryId );
	}
}
