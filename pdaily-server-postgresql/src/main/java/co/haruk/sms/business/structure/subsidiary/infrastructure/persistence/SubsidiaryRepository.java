package co.haruk.sms.business.structure.subsidiary.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.subsidiary.domain.model.Subsidiary;
import co.haruk.sms.common.model.Reference;

/**
 * @author andres2508 on 19/11/19
 **/
@ApplicationScoped
public class SubsidiaryRepository extends JPARepository<Subsidiary> {

	public Optional<Subsidiary> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Subsidiary.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}

	public Optional<Subsidiary> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				Subsidiary.findByReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}
}
