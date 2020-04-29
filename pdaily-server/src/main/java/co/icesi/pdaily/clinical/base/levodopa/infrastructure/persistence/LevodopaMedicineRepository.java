package co.icesi.pdaily.clinical.base.levodopa.infrastructure.persistence;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicine;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

@ApplicationScoped
public class LevodopaMedicineRepository extends JPARepository<LevodopaMedicine> {
    public Optional<LevodopaMedicine> findByName(PlainName label) {
        requireNonNull( label );
        return findSingleWithNamedQuery(
                LevodopaMedicine.findByName,
                QueryParameter.with( "name", label.text() ).parameters()
        );
    }
}
