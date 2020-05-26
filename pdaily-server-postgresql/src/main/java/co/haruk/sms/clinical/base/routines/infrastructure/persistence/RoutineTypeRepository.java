package co.haruk.sms.clinical.base.routines.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.clinical.base.routines.domain.model.RoutineType;
import co.haruk.sms.common.model.Reference;

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
