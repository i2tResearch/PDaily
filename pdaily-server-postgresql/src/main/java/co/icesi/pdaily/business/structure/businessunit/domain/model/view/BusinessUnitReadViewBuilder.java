package co.icesi.pdaily.business.structure.businessunit.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.infrastructure.persistence.BusinessUnitRepository;

@Dependent
public class BusinessUnitReadViewBuilder {

	@Inject
	BusinessUnitRepository repository;

	protected BusinessUnitReadViewBuilder() {
	}

	public BusinessUnitReadView buildBusinessUnit(BusinessUnitId businessId) {
		requireNonNull( businessId );
		final var build = repository.findByIdAsReadView( businessId );
		return build;
	}
}
