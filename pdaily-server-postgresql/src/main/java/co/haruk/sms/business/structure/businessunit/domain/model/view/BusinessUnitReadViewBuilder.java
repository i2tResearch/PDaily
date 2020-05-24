package co.haruk.sms.business.structure.businessunit.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.infrastructure.persistence.BusinessUnitRepository;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;

@Dependent
public class BusinessUnitReadViewBuilder {

	@Inject
	BusinessUnitRepository repository;

	protected BusinessUnitReadViewBuilder() {
	}

	public BusinessUnitReadView buildBusinessUnit(BusinessUnitId businessId) {
		requireNonNull( businessId );
		final var build = repository.findByIdAsReadView( businessId );
		build.sellers = sellersByBusiness( businessId );
		return build;
	}

	private List<SalesRepReadView> sellersByBusiness(BusinessUnitId businessId) {
		final List<String[]> queryResult = repository.sellersByBusinessUnit( businessId );
		return StreamUtils.map( queryResult, it -> SalesRepReadView.of( it[0], it[1], it[2], it[3] ) );
	}
}
