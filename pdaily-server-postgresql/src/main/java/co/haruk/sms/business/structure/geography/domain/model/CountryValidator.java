package co.haruk.sms.business.structure.geography.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.geography.infrastructure.persistence.CountryRepository;
import co.haruk.sms.business.structure.geography.infrastructure.persistence.StateRepository;

/**
 * @author cristhiank on 2/12/19
 **/
@Dependent
public class CountryValidator {
	@Inject
	CountryRepository repository;
	@Inject
	StateRepository stateRepository;

	public void validate(Country country) {
		failIfDuplicatedCode( country );
		failIfDuplicatedName( country );
	}

	private void failIfDuplicatedName(Country country) {
		final var found = repository.findByName( country.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !country.isPersistent() || !country.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( country.name() );
			}
		}
	}

	private void failIfDuplicatedCode(Country country) {
		final var found = repository.findByCode( country.isoCode() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !country.isPersistent() || !country.equals( existent );
			if ( mustFail ) {
				throw new DuplicatedRecordException( country.isoCode() );
			}
		}
	}

	public void checkBeforeDelete(CountryId countryId) {
		final boolean hasStates = stateRepository.existsForCountry( countryId );
		Guards.check( !hasStates, "No se puede eliminar el pais, tiene departamentos asignados" );
	}
}
