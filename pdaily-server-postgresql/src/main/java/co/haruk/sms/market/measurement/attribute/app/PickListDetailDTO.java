package co.haruk.sms.market.measurement.attribute.app;

import java.util.UUID;

import co.haruk.sms.common.model.Reference;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetail;
import co.haruk.sms.market.measurement.attribute.domain.model.picklist.PickListDetailId;

public class PickListDetailDTO {
	public String id;
	public String listValue;

	protected PickListDetailDTO() {
	}

	private PickListDetailDTO(String id, String listValue) {
		this.id = id;
		this.listValue = listValue;
	}

	public PickListDetailDTO(UUID id, String listValue) {
		this.id = id.toString();
		this.listValue = listValue;
	}

	public static PickListDetailDTO of(String id, String listValue) {
		return new PickListDetailDTO( id, listValue );
	}

	public static PickListDetailDTO of(PickListDetail pickList) {
		return new PickListDetailDTO(
				pickList.id().text(),
				pickList.listValue().text()
		);
	}

	public PickListDetail toPickListDetail(MarketAttribute marketAttribute) {
		return PickListDetail.of(
				PickListDetailId.of( id ),
				marketAttribute,
				Reference.of( listValue )
		);
	}
}
