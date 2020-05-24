package co.haruk.sms.business.structure.customer.holding.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompany;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;

/**
 * @author cristhiank on 1/12/19
 **/
@ApplicationScoped
public class HoldingCompanyRepository extends JPARepository<HoldingCompany> {

	public Optional<HoldingCompany> findByName(
			SubsidiaryId subsidiaryId,
			PlainName name) {
		requireNonNull( subsidiaryId );
		requireNonNull( name );
		return findSingleWithNamedQuery(
				HoldingCompany.findByName,
				QueryParameter.with( "name", name.text() )
						.and( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public List<HoldingCompany> findForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findWithNamedQuery(
				HoldingCompany.findBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public boolean existsAnyForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		final int count = executeAggregateQuery(
				HoldingCompany.countBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		).intValue();
		return count > 0;
	}
}
