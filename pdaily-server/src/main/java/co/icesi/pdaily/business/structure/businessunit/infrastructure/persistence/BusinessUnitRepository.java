package co.icesi.pdaily.business.structure.businessunit.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.common.model.Reference;

@ApplicationScoped
public class BusinessUnitRepository extends JPARepository<BusinessUnit> {

	public Optional<BusinessUnit> findByReference(Reference reference) {
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				BusinessUnit.findByIntReference,
				QueryParameter.with( "reference", reference.text() ).parameters()
		);
	}

	public Optional<BusinessUnit> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				BusinessUnit.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
