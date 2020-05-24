package co.haruk.sms.business.structure.geography.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.business.structure.geography.domain.model.view.CityTextView;
import co.haruk.sms.business.structure.geography.infrastructure.persistence.CityRepository;
import co.haruk.sms.business.structure.geography.infrastructure.persistence.CountryRepository;
import co.haruk.sms.business.structure.geography.infrastructure.persistence.StateRepository;

/**
 * @author andres2508 on 2/12/19
 **/
@ApplicationScoped
public class GeographyService {
	@Inject
	CountryRepository countryRepository;
	@Inject
	CountryValidator countryValidator;
	@Inject
	StateRepository stateRepository;
	@Inject
	StateValidator stateValidator;
	@Inject
	CityRepository cityRepository;
	@Inject
	CityValidator cityValidator;

	public Optional<Country> findCountryByCode(CountryIsoCode isoCode) {
		return countryRepository.findByCode( isoCode );
	}

	@Transactional
	public Country saveCountry(Country changes) {
		if ( changes.isPersistent() ) {
			final var original = countryRepository.findOrFail( changes.id() );
			original.updateFrom( changes );
			countryValidator.validate( original );
			return countryRepository.update( original );
		} else {
			changes.setId( CountryId.generateNew() );
			countryValidator.validate( changes );
			return countryRepository.create( changes );
		}
	}

	public List<Country> findAllCountries() {
		return countryRepository.findAll();
	}

	public List<ImporterResult> runImporters() {
		final var importers = CDI.current().select( IGeographyImporter.class );
		final var result = new ArrayList<ImporterResult>();
		for ( IGeographyImporter importer : importers ) {
			try {
				importer.doImport();
				result.add( ImporterResult.success( importer.name() ) );
			} catch (Exception ex) {
				result.add( ImporterResult.failure( importer.name(), ex.getMessage() ) );
			}
		}
		return result;
	}

	@Transactional
	public State findOrCreateStateByName(State state) {
		final var found = stateRepository.findByName( state.country(), state.name() );
		if ( found.isPresent() ) {
			return found.get();
		}
		state.setId( StateId.generateNew() );
		stateValidator.validate( state );
		return stateRepository.create( state );
	}

	@Transactional
	public void createCityIfNotExistsByName(City city) {
		final var found = cityRepository.findByName( city.state(), city.name() );
		if ( found.isEmpty() ) {
			city.setId( CityId.generateNew() );
			cityValidator.validate( city );
			cityRepository.create( city );
		}
	}

	public Country findCountryById(CountryId countryId) {
		return countryRepository.findOrFail( countryId );
	}

	@Transactional
	public void deleteCountryById(CountryId countryId) {
		countryValidator.checkBeforeDelete( countryId );
		countryRepository.delete( countryId );
	}

	@Transactional
	public State saveState(State changed) {
		State saved;
		if ( changed.isPersistent() ) {
			final var original = stateRepository.findOrFail( changed.id() );
			original.updateFrom( changed );
			stateValidator.validate( original );
			saved = stateRepository.update( original );
		} else {
			changed.setId( StateId.generateNew() );
			stateValidator.validate( changed );
			saved = stateRepository.create( changed );
		}
		return saved;
	}

	public State findStateById(StateId stateId) {
		return stateRepository.findOrFail( stateId );
	}

	@Transactional
	public void deleteStateById(StateId stateId) {
		stateValidator.checkBeforeDelete( stateId );
		stateRepository.delete( stateId );
	}

	public List<State> findStatesForCountry(CountryId countryId) {
		return stateRepository.findByCountry( countryId );
	}

	@Transactional
	public City saveCity(City changed) {
		City saved;
		if ( changed.isPersistent() ) {
			final var original = cityRepository.findOrFail( changed.id() );
			original.updateFrom( changed );
			cityValidator.validate( original );
			saved = cityRepository.update( original );
		} else {
			changed.setId( CityId.generateNew() );
			cityValidator.validate( changed );
			saved = cityRepository.create( changed );
		}
		return saved;
	}

	public City findCityById(CityId cityId) {
		return cityRepository.findOrFail( cityId );
	}

	@Transactional
	public void deleteCityById(CityId cityId) {
		cityRepository.delete( cityId );
	}

	public List<City> findCitiesForState(StateId stateId) {
		return cityRepository.findForState( stateId );
	}

	public CityTextView findCityTextView(CityId cityId) {
		return cityRepository.findCityTextView( cityId );
	}
}
