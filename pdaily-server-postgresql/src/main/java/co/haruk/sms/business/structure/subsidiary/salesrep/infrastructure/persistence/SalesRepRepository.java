package co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRep;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.SalesRepId;
import co.haruk.sms.business.structure.subsidiary.salesrep.domain.model.view.SalesRepReadView;
import co.haruk.sms.common.model.Reference;

/**
 * @author andres2508 on 25/11/19
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

	public List<String[]> businessUnitBySalesRep(SalesRepId salesRepId) {
		Map<String, Object> params = new HashMap<>();
		params.put( "salesRepId", salesRepId == null ? null : "'" + salesRepId.text() + "'" );
		final var query = "SELECT BU.id as id, BU.name as name, BU.reference as reference, " +
				"BU.effective_activity_threshold as effective_threshold " +
				"FROM bs_business_units_sellers as BS " +
				"         INNER JOIN BS_BUSINESS_UNITS BU ON BU.id = BS.business_id " +
				"WHERE BS.sales_rep_id = {{salesRepId}}";

		return executeQuery( query, params ).stream()
				.map( it -> it.split( "\\|" ) ).collect( Collectors.toList() );
	}

	private List<String> executeQuery(String query, Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				query, StdMappers.STRING(), params
		);
	}
}
