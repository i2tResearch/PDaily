package co.haruk.sms.business.structure.geography.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.geography.domain.model.CountryId;
import co.haruk.sms.business.structure.geography.domain.model.State;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 2/12/19
 **/
@ApplicationScoped
public class StateRepository extends JPARepository<State> {

	@Override
	public List<State> findAll() {
		return findBy( "ORDER BY name.name", null );
	}

	public Optional<State> findByName(CountryId country, PlainName name) {
		requireNonNull( country );
		requireNonNull( name );
		return findSingleWithNamedQuery(
				State.findByName,
				QueryParameter.with( "name", name.text() ).and( "country", country ).parameters()
		);
	}

	public Optional<State> findByReference(CountryId country, Reference reference) {
		requireNonNull( country );
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				State.findByReference,
				QueryParameter.with( "reference", reference.text() ).and( "country", country ).parameters()
		);
	}

	public boolean existsForCountry(CountryId countryId) {
		requireNonNull( countryId );
		final int count = executeAggregateQuery(
				State.countByCountry,
				QueryParameter.with( "country", countryId ).parameters()
		).intValue();
		return count > 0;
	}

	public List<State> findByCountry(CountryId countryId) {
		requireNonNull( countryId );
		return findWithNamedQuery(
				State.findByCountry,
				QueryParameter.with( "country", countryId ).parameters()
		);
	}
}
