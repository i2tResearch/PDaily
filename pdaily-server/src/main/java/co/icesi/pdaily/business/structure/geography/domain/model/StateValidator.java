package co.icesi.pdaily.business.structure.geography.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.geography.infrastructure.persistence.CityRepository;
import co.icesi.pdaily.business.structure.geography.infrastructure.persistence.StateRepository;

/**
 * @author cristhiank on 2/12/19
 **/
@Dependent
public class StateValidator {
	@Inject
	StateRepository repository;
	@Inject
	CityRepository cityRepository;

	public void validate(State state) {
		failIfDuplicatedName( state );
		if ( state.reference() != null ) {
			failIfDuplicatedReference( state );
		}
	}

	private void failIfDuplicatedReference(State state) {
		final var found = repository.findByReference( state.country(), state.reference() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !state.isPersistent() || !state.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( state.reference() );
			}
		}
	}

	private void failIfDuplicatedName(State state) {
		final var found = repository.findByName( state.country(), state.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !state.isPersistent() || !state.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( state.name() );
			}
		}
	}

	public void checkBeforeDelete(StateId stateId) {
		final boolean hasCities = cityRepository.existsForState( stateId );
		check( !hasCities, "No se puede eliminar el departamento, tiene ciudades asignadas" );
	}
}
