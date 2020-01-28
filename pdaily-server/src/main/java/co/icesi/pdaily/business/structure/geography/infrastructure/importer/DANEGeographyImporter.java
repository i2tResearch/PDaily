package co.icesi.pdaily.business.structure.geography.infrastructure.importer;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.business.structure.geography.domain.model.City;
import co.icesi.pdaily.business.structure.geography.domain.model.Country;
import co.icesi.pdaily.business.structure.geography.domain.model.CountryIsoCode;
import co.icesi.pdaily.business.structure.geography.domain.model.GeographyService;
import co.icesi.pdaily.business.structure.geography.domain.model.IGeographyImporter;
import co.icesi.pdaily.business.structure.geography.domain.model.State;
import co.icesi.pdaily.common.model.Reference;

import io.quarkus.arc.Unremovable;

/**
 * @author cristhiank on 2/12/19
 **/
@Dependent
@Unremovable
public class DANEGeographyImporter implements IGeographyImporter {
	@Inject
	GeographyService geographyService;
	@Inject
	@RestClient
	DANERestService daneService;

	@Override
	public String name() {
		return "Sincronización DANE";
	}

	public void doImport() {
		final var country = createCountry();
		final List<DaneStateDTO> daneStates = getDaneStates();
		for ( DaneStateDTO stateDTO : daneStates ) {
			final State newState = country.createState(
					Reference.of( stateDTO.departamentoCode() ),
					PlainName.of( stateDTO.departamentoSanitized() )
			);
			final var state = geographyService.findOrCreateStateByName( newState );
			final List<DaneCityDTO> stateCities = getStateCities( newState.reference().text() );
			for ( DaneCityDTO cityDTO : stateCities ) {
				final City newCity = state.createCity(
						Reference.of( cityDTO.municipioCode() ),
						PlainName.of( cityDTO.municipioSanitized() )
				);
				geographyService.createCityIfNotExistsByName( newCity );
			}
		}
	}

	private List<DaneStateDTO> getDaneStates() {
		return daneService.selectStates( "distinct c_digo_dane_del_departamento,departamento" );
	}

	private List<DaneCityDTO> getStateCities(String stateCode) {
		return daneService.citiesForState( stateCode, "distinct c_digo_dane_del_municipio,municipio" );
	}

	private Country createCountry() {
		final CountryIsoCode isoCode = CountryIsoCode.of( "CO" );
		final Optional<Country> found = geographyService.findCountryByCode( isoCode );
		if ( found.isPresent() ) {
			return found.get();
		}
		final var colombia = Country.of( null, isoCode, PlainName.of( "Colombia" ) );
		return geographyService.saveCountry( colombia );
	}
}
