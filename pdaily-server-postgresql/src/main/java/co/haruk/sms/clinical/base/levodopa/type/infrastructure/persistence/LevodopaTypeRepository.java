package co.haruk.sms.clinical.base.levodopa.type.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.clinical.base.levodopa.type.domain.model.LevodopaType;

@ApplicationScoped
public class LevodopaTypeRepository extends JPARepository<LevodopaType> {
	public Optional<LevodopaType> findByName(PlainName label) {
		requireNonNull( label );
		return findSingleWithNamedQuery(
				LevodopaType.findByName,
				QueryParameter.with( "label", label.text() ).parameters()
		);
	}
}
