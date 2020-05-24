package co.haruk.sms.core.database.upgrader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author cristhiank on 30/10/19
 **/
@DisplayName("Version tests")
public class VersionTest {

	@Test
	@DisplayName("Creates valid version")
	public void createsValid() {
		Version version = Version.of( "1.0.0" );
		assertEquals( 100, version.value() );
	}

	@Test
	@DisplayName("Compares version correctly")
	public void comparesCorrectly() {
		// Mayor que
		Version version = Version.of( "1.0.0" );
		Version versionEqual = Version.of( "1.0.0" );
		Version version2 = Version.of( "1.0.1" );
		assertEquals( -1, version.compareTo( version2 ) );
		// Menor que
		assertEquals( 1, version2.compareTo( version ) );
		// Igual
		assertEquals( 0, version.compareTo( versionEqual ) );
		assertEquals( version, versionEqual );
	}

	@Test
	@DisplayName("Fails if invalid version")
	public void failsIfInvalid() {
		Assertions.assertThrows( IllegalArgumentException.class, () -> Version.of( "A.B.C" ) );
	}
}