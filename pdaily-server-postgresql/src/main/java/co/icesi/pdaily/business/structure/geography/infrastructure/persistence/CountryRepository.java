package co.icesi.pdaily.business.structure.geography.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.geography.domain.model.Country;
import co.icesi.pdaily.business.structure.geography.domain.model.CountryIsoCode;

/**
 * @author andres2508 on 2/12/19
 **/
@ApplicationScoped
public class CountryRepository extends JPARepository<Country> {

	@Override
	public List<Country> findAll() {
		return findBy( "ORDER BY  name.name", null );
	}

	public Optional<Country> findByCode(CountryIsoCode isoCode) {
		requireNonNull( isoCode );
		return findSingleWithNamedQuery(
				Country.findByCode,
				QueryParameter.with( "code", isoCode ).parameters()
		);
	}

	public Optional<Country> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Country.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
