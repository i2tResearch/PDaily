package co.haruk.sms.business.structure.subsidiary.salesoffice.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesoffice.domain.model.SalesOffice;
import co.haruk.sms.common.model.Reference;

/**
 * @author andres2508 on 21/11/19
 **/
@ApplicationScoped
public class SalesOfficeRepository extends JPARepository<SalesOffice> {

	public Optional<SalesOffice> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				SalesOffice.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<SalesOffice> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				SalesOffice.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public List<SalesOffice> findForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findWithNamedQuery(
				SalesOffice.findBySubsidiary,
				QueryParameter.with( "subsidiaryId", subsidiaryId ).parameters()
		);
	}

	public boolean existsAnyForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		final var count = executeAggregateQuery(
				SalesOffice.countBySubsidiary,
				QueryParameter.with( "subsidiaryId", subsidiaryId ).parameters()
		).intValue();
		return count > 0;
	}
}
