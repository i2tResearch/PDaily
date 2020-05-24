package co.haruk.sms.market.measurement.attribute.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.businessunit.app.BusinessUnitAppService;
import co.haruk.sms.market.measurement.attribute.app.PickListDetailDTO;
import co.haruk.sms.market.measurement.attribute.domain.model.MarketAttribute;

@Dependent
public class MarketAttributeReadBuilder {
	@Inject
	BusinessUnitAppService businessAppService;

	public MarketAttributeReadView buildFor(MarketAttribute attribute) {
		requireNonNull( attribute );
		final var businessUnit = businessAppService.findOrFail( attribute.businessId().text() );
		final var result = MarketAttributeReadView.of(
				attribute.id().text(),
				attribute.businessId().text(),
				businessUnit.name,
				attribute.label().text(),
				attribute.subjectType().dbKey(),
				attribute.dataType().dbKey()
		);
		if ( !attribute.pickListDetails().isEmpty() ) {
			result.options = StreamUtils.map( attribute.pickListDetails(), PickListDetailDTO::of );
		}
		return result;
	}
}
