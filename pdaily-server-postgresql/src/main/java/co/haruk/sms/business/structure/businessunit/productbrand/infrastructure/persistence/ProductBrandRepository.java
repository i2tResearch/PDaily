package co.haruk.sms.business.structure.businessunit.productbrand.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.productbrand.domain.model.ProductBrand;

@ApplicationScoped
public class ProductBrandRepository extends JPARepository<ProductBrand> {

	public Optional<ProductBrand> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				ProductBrand.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public List<ProductBrand> findForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		return findWithNamedQuery(
				ProductBrand.findByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		);
	}

	public boolean existsForBusinessUnit(BusinessUnitId unitId) {
		requireNonNull( unitId );
		final var count = executeAggregateQuery(
				ProductBrand.countByBusinessUnit,
				QueryParameter.with( "businessUnit", unitId ).parameters()
		).intValue();
		return count > 0;
	}
}
