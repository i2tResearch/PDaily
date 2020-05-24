package co.haruk.sms.common.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author andres2508 on 18/12/19
 **/
@DisplayName("Reference tests")
class ReferenceTest {

	@Test
	@DisplayName("Allows empty references")
	void emptyReferences() {
		assertNull( Reference.of( "   " ) );
		assertNull( Reference.of( "" ) );
		assertNull( Reference.of( null ) );
	}

	@Test
	@DisplayName("Allows non-empty references")
	void notEmptyReferences() {
		assertNotNull( Reference.of( "VALID" ) );
		assertNotNull( Reference.of( "VALID MULTI LINE" ) );
	}
}