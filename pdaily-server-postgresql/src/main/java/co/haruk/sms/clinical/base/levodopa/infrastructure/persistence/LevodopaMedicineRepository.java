package co.haruk.sms.clinical.base.levodopa.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicine;

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
