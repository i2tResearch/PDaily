package co.icesi.pdaily.business.structure.geography.importer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.haruk.core.testing.cdi.ICDIContainerDependent;
import co.icesi.pdaily.business.structure.geography.infrastructure.importer.DANEGeographyImporter;
import co.icesi.testing.SMSTest;

/**
 * @author cristhiank on 2/12/19
 **/
@SMSTest
@DisplayName("DANE Importer tests")
class DANEImporterIT implements ICDIContainerDependent {

	@Inject
	DANEGeographyImporter importer;

	@Override
	public List<Class<?>> requiredBeans() {
		return List.of( DANERestServiceLocalStub.class );
	}

	@Test
	@DisplayName("Run DANE importer correctly")
	void runImporters() {
		assertDoesNotThrow( () -> {
			importer.doImport();
		} );
	}
}
