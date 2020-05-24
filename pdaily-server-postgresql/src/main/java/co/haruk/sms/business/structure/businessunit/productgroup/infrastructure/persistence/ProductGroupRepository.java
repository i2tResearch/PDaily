package co.haruk.sms.business.structure.businessunit.productgroup.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.productgroup.domain.model.ProductGroup;
import co.haruk.sms.common.model.Reference;

@ApplicationScoped
public class ProductGroupRepository extends JPARepository<ProductGroup> {

	public Optional<ProductGroup> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				ProductGroup.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<ProductGroup> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				ProductGroup.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public List<ProductGroup> findForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		return findWithNamedQuery(
				ProductGroup.findByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		);
	}

	public boolean existsForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		final var count = executeAggregateQuery(
				ProductGroup.countByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		).intValue();
		return count > 0;
	}
}
