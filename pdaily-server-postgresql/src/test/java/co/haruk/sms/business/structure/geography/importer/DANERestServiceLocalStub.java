package co.haruk.sms.business.structure.geography.importer;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import co.haruk.core.testing.TestNamesGenerator;
import co.haruk.sms.business.structure.geography.infrastructure.importer.DANERestService;
import co.haruk.sms.business.structure.geography.infrastructure.importer.DaneCityDTO;
import co.haruk.sms.business.structure.geography.infrastructure.importer.DaneStateDTO;

/**
 * @author andres2508 on 3/12/19
 **/
@RestClient
@ApplicationScoped
public class DANERestServiceLocalStub implements DANERestService {
	@Override
	public List<DaneStateDTO> selectStates(String select) {
		// Return two test states
		return List.of(
				DaneStateDTO.of( TestNamesGenerator.generateCode(), TestNamesGenerator.generateName() ),
				DaneStateDTO.of( TestNamesGenerator.generateCode(), TestNamesGenerator.generateName() )
		);
	}

	@Override
	public List<DaneCityDTO> citiesForState(
			String stateId, String select) {
		// Return 10 random cities
		final var list = new ArrayList<DaneCityDTO>();
		for ( int i = 0; i < 10; i++ ) {
			list.add( DaneCityDTO.of( TestNamesGenerator.generateCode(), TestNamesGenerator.generateName() ) );
		}
		return list;
	}
}
