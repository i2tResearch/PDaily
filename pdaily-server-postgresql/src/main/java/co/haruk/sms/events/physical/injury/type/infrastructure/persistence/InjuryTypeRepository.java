package co.haruk.sms.events.physical.injury.type.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.events.physical.injury.type.domain.model.InjuryType;

@ApplicationScoped
public class InjuryTypeRepository extends JPARepository<InjuryType> {
	public Optional<InjuryType> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				InjuryType.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
