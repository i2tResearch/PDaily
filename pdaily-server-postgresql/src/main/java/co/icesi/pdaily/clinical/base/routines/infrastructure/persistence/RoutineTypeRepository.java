package co.icesi.pdaily.clinical.base.routines.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.clinical.base.routines.domain.model.RoutineType;
import co.icesi.pdaily.common.model.Reference;

@ApplicationScoped
public class RoutineTypeRepository extends JPARepository<RoutineType> {
	public Optional<RoutineType> findByLabel(Reference label) {
		requireNonNull( label );
		return findSingleWithNamedQuery(
				RoutineType.findByLabel,
				QueryParameter.with( "label", label.text() ).parameters()
		);
	}
}
