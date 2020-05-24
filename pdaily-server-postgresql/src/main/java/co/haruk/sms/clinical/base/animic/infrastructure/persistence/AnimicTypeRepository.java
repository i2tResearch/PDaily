package co.haruk.sms.clinical.base.animic.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.clinical.base.animic.domain.model.AnimicType;

@ApplicationScoped
public class AnimicTypeRepository extends JPARepository<AnimicType> {
	public Optional<AnimicType> findByLabel(PlainName label) {
		requireNonNull( label );
		return findSingleWithNamedQuery(
				AnimicType.findByLabel,
				QueryParameter.with( "label", label.text() ).parameters()
		);
	}
}
