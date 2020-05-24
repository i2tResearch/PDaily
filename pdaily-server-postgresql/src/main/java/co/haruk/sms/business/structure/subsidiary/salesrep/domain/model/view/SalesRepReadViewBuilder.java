package co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.businessunit.app.BusinessUnitDTO;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;

@Dependent
public class SalesRepReadViewBuilder {
	@Inject
	SalesRepRepository repository;

	protected SalesRepReadViewBuilder() {
	}

	public SalesRepReadView buildSalesRep(SalesRepId salesRepId) {
		requireNonNull( salesRepId );
		final var build = repository.findOrFailAsRepView( salesRepId );
		build.businessUnits = businessUnisBySalesRep( salesRepId );
		return build;
	}

	private List<BusinessUnitDTO> businessUnisBySalesRep(SalesRepId salesRepId) {
		final List<String[]> queryResult = repository.businessUnitBySalesRep( salesRepId );
		return StreamUtils.map(
				queryResult, it -> BusinessUnitDTO.of(
						it[0], it[1], it[2],
						Integer.parseInt( it[3] )
				)
		);
	}
}
