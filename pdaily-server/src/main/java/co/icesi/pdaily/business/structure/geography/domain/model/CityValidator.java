package co.icesi.pdaily.business.structure.geography.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.geography.infrastructure.persistence.CityRepository;

/**
 * @author cristhiank on 2/12/19
 **/
@Dependent
public class CityValidator {
	@Inject
	CityRepository repository;

	public void validate(City city) {
		failIfDuplicatedName( city );
		if ( city.reference() != null ) {
			failIfDuplicatedReference( city );
		}
	}

	private void failIfDuplicatedReference(City city) {
		final var found = repository.findByReference( city.state(), city.reference() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !city.isPersistent() || !city.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( city.reference() );
			}
		}
	}

	private void failIfDuplicatedName(City city) {
		final var found = repository.findByName( city.state(), city.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !city.isPersistent() || !city.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( city.name() );
			}
		}
	}
}
