package co.haruk.sms.market.measurement.attribute.domain.model.view;

import java.util.List;

import co.haruk.sms.market.measurement.attribute.app.PickListDetailDTO;

public class MarketAttributeReadView {
	public String id;
	public String businessId;
	public String businessName;
	public String label;
	public List<PickListDetailDTO> options;
	public String subjectType;
	public String dataType;

	protected MarketAttributeReadView() {
	}

	private MarketAttributeReadView(String id, String businessId, String businessName, String label,
			List<PickListDetailDTO> options,
			String subjectType, String dataType) {
		this.id = id;
		this.businessId = businessId;
		this.businessName = businessName;
		this.label = label;
		this.options = options;
		this.subjectType = subjectType;
		this.dataType = dataType;
	}

	public static MarketAttributeReadView of(String id, String businessId, String businessName, String label,
			String subjectType, String dataType) {
		return new MarketAttributeReadView( id, businessId, businessName, label, null, subjectType, dataType );
	}
}
