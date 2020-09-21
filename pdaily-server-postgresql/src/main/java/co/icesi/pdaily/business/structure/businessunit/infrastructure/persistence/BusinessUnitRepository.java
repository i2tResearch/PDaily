package co.icesi.pdaily.business.structure.businessunit.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.IDataSourceProvider;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.domain.model.query.QueryEngine;
import co.haruk.core.domain.model.query.StdMappers;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.domain.model.view.BusinessUnitReadView;
import co.icesi.pdaily.common.model.Reference;

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

	private List<String> executeQuery(String query, Map<String, Object> params) {
		return QueryEngine.executeReadQuery(
				IDataSourceProvider.current().getDataSource(),
				query, StdMappers.STRING(), params
		);
	}
}
