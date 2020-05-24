package co.haruk.sms.sales.salesorder.source.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.sales.salesorder.source.domain.model.OrderSource;

/**
 * @author andres2508 on 23/12/19
 **/
@ApplicationScoped
public class OrderSourceRepository extends JPARepository<OrderSource> {

	public Optional<OrderSource> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				OrderSource.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
