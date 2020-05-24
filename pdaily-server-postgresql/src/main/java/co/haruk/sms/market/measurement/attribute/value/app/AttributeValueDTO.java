package co.haruk.sms.market.measurement.attribute.value.app;

import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValueId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.SubjectId;

public class AttributeValueDTO {
	public String id;
	public String attributeId;
	public String value;
	public String subjectId;

	protected AttributeValueDTO() {
	}

	private AttributeValueDTO(String id, String attributeId, String value, String subjectId) {
		this.id = id;
		this.attributeId = attributeId;
		this.value = value;
		this.subjectId = subjectId;
	}

	public static AttributeValueDTO of(String id, String attributeId, String value, String subjectId) {
		return new AttributeValueDTO( id, attributeId, value, subjectId );
	}

	public static AttributeValueDTO of(AttributeValue attributeValue) {
		return new AttributeValueDTO(
				attributeValue.id().text(),
				attributeValue.attributeId().text(),
				attributeValue.value().value(),
				attributeValue.subjectId().text()
		);
	}

	public AttributeValue toAttributeValue() {
		return AttributeValue.of(
				AttributeValueId.of( id ),
				MarketAttributeId.ofNotNull( attributeId ),
				AttributeContent.of( value ),
				SubjectId.ofNotNull( subjectId )
		);
	}
}
