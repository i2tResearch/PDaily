package co.icesi.pdaily.business.structure.businessunit.productline.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.productline.domain.model.ProductLine;
import co.icesi.pdaily.common.model.Reference;

@ApplicationScoped
public class ProductLineRepository extends JPARepository<ProductLine> {

	public Optional<ProductLine> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				ProductLine.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<ProductLine> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				ProductLine.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public List<ProductLine> findForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		return findWithNamedQuery(
				ProductLine.findByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		);
	}

	public boolean existsForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		final var count = executeAggregateQuery(
				ProductLine.countByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		).intValue();
		return count > 0;
	}
}
