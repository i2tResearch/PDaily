package co.haruk.sms.business.structure.businessunit.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnit;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.domain.model.view.BusinessUnitReadView;
import co.haruk.sms.common.model.Reference;

@ApplicationScoped
public class BusinessUnitRepository extends JPARepository<BusinessUnit> {

	public Optional<BusinessUnit> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				BusinessUnit.findByIntReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<BusinessUnit> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				BusinessUnit.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public BusinessUnitReadView findByIdAsReadView(BusinessUnitId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				BusinessUnitReadView.class,
				BusinessUnit.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( () -> new EntityNotFoundException( "No se encontró la unidad de negocios" ) );
	}

	public List<String[]> sellersByBusinessUnit(BusinessUnitId businessId) {
		Map<String, Object> params = new HashMap<>();
		params.put( "businessId", businessId == null ? null : "'" + businessId.text() + "'" );
		final var query = "SELECT SL.id as id, CONCAT( US.given_name, ' ', " +
				"US.last_name ) as fullName, SL.reference as reference, SL.subsidiary_id as subsidiary " +
				"FROM bs_business_units_sellers as BS " +
				"INNER JOIN BS_SALES_REPS SL ON SL.id = BS.sales_rep_id " +
				"INNER JOIN SECURITY_USERS US ON US.id = SL.id " +
				"WHERE BS.business_id = {{businessId}}";

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
