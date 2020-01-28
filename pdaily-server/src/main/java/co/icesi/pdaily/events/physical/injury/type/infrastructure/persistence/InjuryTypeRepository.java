package co.icesi.pdaily.events.physical.injury.type.infrastructure.persistence;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryType;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

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
