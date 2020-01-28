package co.icesi.pdaily.business.structure.geography.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.geography.domain.model.City;
import co.icesi.pdaily.business.structure.geography.domain.model.CityId;
import co.icesi.pdaily.business.structure.geography.domain.model.StateId;
import co.icesi.pdaily.business.structure.geography.domain.model.view.CityTextView;
import co.icesi.pdaily.common.model.Reference;

/**
 * @author cristhiank on 2/12/19
 **/
@ApplicationScoped
public class CityRepository extends JPARepository<City> {

	@Override
	public List<City> findAll() {
		return findBy( "ORDER BY name.name", null );
	}

	public Optional<City> findByName(StateId state, PlainName name) {
		requireNonNull( state );
		requireNonNull( name );
		return findSingleWithNamedQuery(
				City.findByName,
				QueryParameter.with( "name", name.text() ).and( "state", state ).parameters()
		);
	}

	public Optional<City> findByReference(StateId state, Reference reference) {
		requireNonNull( state );
		requireNonNull( reference );
		return findSingleWithNamedQuery(
				City.findByReference,
				QueryParameter.with( "reference", reference.text() ).and( "state", state ).parameters()
		);
	}

	public boolean existsForState(StateId stateId) {
		requireNonNull( stateId );
		final int count = executeAggregateQuery(
				City.countByState,
				QueryParameter.with( "state", stateId ).parameters()
		).intValue();
		return count > 0;
	}

	public List<City> findForState(StateId stateId) {
		requireNonNull( stateId );
		return findWithNamedQuery(
				City.findByState,
				QueryParameter.with( "state", stateId ).parameters()
		);
	}

	public CityTextView findCityTextView(CityId cityId) {
		requireNonNull( cityId );
		return findOtherSingleWithNamedQuery(
				CityTextView.class,
				City.findByIdTextView,
				QueryParameter.with( "cityId", cityId ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
