package co.icesi.pdaily.business.structure.subsidiary.salesrep.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.icesi.pdaily.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author cristhiank on 25/11/19
 **/
@ApplicationScoped
public class SalesRepRepository extends JPARepository<SalesRep> {

	public List<SalesRepReadView> findAllAsRepView() {
		return findOtherWithNamedQuery( SalesRepReadView.class, SalesRep.findAllAsRepView );
	}

	public SalesRepReadView findOrFailAsRepView(SalesRepId repId) {
		requireNonNull( repId );
		return findOtherSingleWithNamedQuery(
				SalesRepReadView.class,
				SalesRep.findByIdAsRepView,
				QueryParameter.with( "id", repId ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public Optional<SalesRep> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				SalesRep.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public List<SalesRepReadView> findForSubsidiaryAsRepView(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findOtherWithNamedQuery(
				SalesRepReadView.class,
				SalesRep.findBySubsidiaryAsRepView,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public List<SalesRep> findForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		return findWithNamedQuery(
				SalesRep.findBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		);
	}

	public boolean existsAnyForSubsidiary(SubsidiaryId subsidiaryId) {
		requireNonNull( subsidiaryId );
		final var count = executeAggregateQuery(
				SalesRep.countBySubsidiary,
				QueryParameter.with( "subsidiary", subsidiaryId ).parameters()
		).intValue();
		return count > 0;
	}
}
