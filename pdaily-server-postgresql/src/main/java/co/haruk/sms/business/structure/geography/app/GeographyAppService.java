package co.haruk.sms.business.structure.geography.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.geography.domain.model.City;
import co.haruk.sms.business.structure.geography.domain.model.CityId;
import co.haruk.sms.business.structure.geography.domain.model.CountryId;
import co.haruk.sms.business.structure.geography.domain.model.GeographyService;
import co.haruk.sms.business.structure.geography.domain.model.ImporterResult;
import co.haruk.sms.business.structure.geography.domain.model.State;
import co.haruk.sms.business.structure.geography.domain.model.StateId;

/**
 * @author andres2508 on 2/12/19
 **/
@ApplicationScoped
public class GeographyAppService {
	@Inject
	GeographyService geographyService;

	public List<ImporterResult> runImporters() {
		return geographyService.runImporters();
	}

	public List<CountryDTO> findAllCountries() {
		final var all = geographyService.findAllCountries();
		return StreamUtils.map( all, CountryDTO::of );
	}

	@Transactional
	public CountryDTO saveCountry(CountryDTO dto) {
		final var saved = geographyService.saveCountry( dto.toCountry() );
		return CountryDTO.of( saved );
	}

	public CountryDTO findCountryById(String id) {
		final var found = geographyService.findCountryById( CountryId.ofNotNull( id ) );
		return CountryDTO.of( found );
	}

	@Transactional
	public void deleteCountryById(String id) {
		geographyService.deleteCountryById( CountryId.ofNotNull( id ) );
	}

	@Transactional
	public StateDTO saveState(StateDTO dto) {
		final var saved = geographyService.saveState( dto.toState() );
		return StateDTO.of( saved );
	}

	public StateDTO findStateById(String id) {
		final State found = geographyService.findStateById( StateId.ofNotNull( id ) );
		return StateDTO.of( found );
	}

	@Transactional
	public void deleteStateById(String id) {
		geographyService.deleteStateById( StateId.ofNotNull( id ) );
	}

	public List<StateDTO> findStatesForCountry(String countryId) {
		final List<State> all = geographyService.findStatesForCountry( CountryId.ofNotNull( countryId ) );
		return StreamUtils.map( all, StateDTO::of );
	}

	@Transactional
	public CityDTO saveCity(CityDTO dto) {
		final City saved = geographyService.saveCity( dto.toCity() );
		return CityDTO.of( saved );
	}

	public CityDTO findCityById(String id) {
		final City found = geographyService.findCityById( CityId.ofNotNull( id ) );
		return CityDTO.of( found );
	}

	@Transactional
	public void deleteCityById(String cityId) {
		geographyService.deleteCityById( CityId.ofNotNull( cityId ) );
	}

	public List<CityDTO> findCitiesForState(String stateId) {
		final List<City> all = geographyService.findCitiesForState( StateId.ofNotNull( stateId ) );
		return StreamUtils.map( all, CityDTO::of );
	}
}
