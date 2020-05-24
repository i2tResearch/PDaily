package co.haruk.sms.sales.activities.purpose.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.sales.activities.purpose.domain.model.Purpose;

@ApplicationScoped
public class PurposeRepository extends JPARepository<Purpose> {
	public Optional<Purpose> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Purpose.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
