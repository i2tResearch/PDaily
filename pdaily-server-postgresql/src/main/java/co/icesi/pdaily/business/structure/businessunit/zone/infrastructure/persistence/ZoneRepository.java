package co.icesi.pdaily.business.structure.businessunit.zone.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.Zone;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author andres2508 on 24/11/19
 **/
@ApplicationScoped
public class ZoneRepository extends JPARepository<Zone> {

	public Optional<Zone> findByReference(
			BusinessUnitId businessUnitId,
			Reference reference) {
		requireNonNull( businessUnitId );
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				Zone.findByReference,
				QueryParameter.with( "reference", reference.text() )
						.and( "businessUnit", businessUnitId ).parameters()
		);
	}

	public Optional<Zone> findByName(
			BusinessUnitId businessUnitId,
			PlainName name) {
		requireNonNull( businessUnitId );
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Zone.findByName,
				QueryParameter.with( "name", name.text() )
						.and( "businessUnit", businessUnitId ).parameters()
		);
	}

	public List<Zone> findForBusinessUnit(BusinessUnitId businessUnitId) {
		requireNonNull( businessUnitId );
		return findWithNamedQuery(
				Zone.findByBusinessUnit,
				QueryParameter.with( "businessUnit", businessUnitId ).parameters()
		);
	}

	public boolean existsAnyForBusinessUnit(BusinessUnitId businessUnitId) {
		requireNonNull( businessUnitId );
		final var count = executeAggregateQuery(
				Zone.countByBusinessUnit,
				QueryParameter.with( "businessUnit", businessUnitId ).parameters()
		).intValue();
		return count > 0;
	}
}
