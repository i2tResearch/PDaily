package co.haruk.sms.market.measurement.attribute.app;

import java.util.List;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.guards.Guards;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.market.measurement.attribute.domain.model.DataType;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttributeId;
import co.haruk.sms.market.measurement.attribute.domain.model.SubjectType;

public class MarketAttributeDTO {
	public String id;
	public String businessId;
	public String label;
	public String subjectType;
	public String dataType;
	public List<PickListDetailDTO> pickListDetails;

	protected MarketAttributeDTO() {
	}

	private MarketAttributeDTO(String id, String businessId, String label, String subjectType, String dataType,
			List<PickListDetailDTO> pickListDetails) {
		this.id = id;
		this.businessId = businessId;
		this.label = label;
		this.subjectType = subjectType;
		this.dataType = dataType;
		this.pickListDetails = pickListDetails;
	}

	public static MarketAttributeDTO of(String id, String businessId, String label,
			String subjectType, String dataType, List<PickListDetailDTO> pickListDetails) {
		return new MarketAttributeDTO( id, businessId, label, subjectType, dataType, pickListDetails );
	}

	public static MarketAttributeDTO of(String id, String businessId, String label,
			String subjectType, String dataType) {
		return new MarketAttributeDTO( id, businessId, label, subjectType, dataType, null );
	}

	public static MarketAttributeDTO of(MarketAttribute marketAttribute) {
		List<PickListDetailDTO> detail = null;
		if ( DataType.isListType( marketAttribute.dataType() ) ) {
			detail = StreamUtils.map( marketAttribute.pickListDetails(), PickListDetailDTO::of );
		}
		return new MarketAttributeDTO(
				marketAttribute.id().text(),
				marketAttribute.businessId().text(),
				marketAttribute.label().text(),
				marketAttribute.subjectType().dbKey(),
				marketAttribute.dataType().dbKey(),
				detail
		);
	}

	public MarketAttribute toMarketAttribute() {
		MarketAttribute result = MarketAttribute.of(
				MarketAttributeId.of( id ),
				BusinessUnitId.ofNotNull( businessId ),
				PlainName.of( label ),
				SubjectType.of( subjectType ),
				DataType.of( dataType )
		);
		if ( DataType.isListType( result.dataType() ) ) {
			addPickListDetails( result );
		}
		return result;
	}

	private void addPickListDetails(MarketAttribute attribute) {
		Guards.require(
				pickListDetails != null && !pickListDetails.isEmpty(),
				"Un atributo tipo lista, sin detalles de lista no es permitido."
		);
		for ( PickListDetailDTO detail : pickListDetails ) {
			attribute.addPickListDetail( detail.toPickListDetail( attribute ) );
		}
	}

}
