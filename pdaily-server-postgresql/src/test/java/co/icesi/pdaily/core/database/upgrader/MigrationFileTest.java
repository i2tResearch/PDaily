package co.icesi.pdaily.core.database.upgrader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author andres2508 on 8/12/19
 **/
@DisplayName("MigrationFile tests")
class MigrationFileTest {

	@Test
	@DisplayName("Creation from valid filename")
	void validFile() {
		assertDoesNotThrow( () -> {
			MigrationFile.of( URI.create( "file:/path/to/migration/V1.0.0__ValidMigration.sql" ) );
		} );
	}

	@Test
	@DisplayName("Fails if invalid filename")
	void invalidFile() {
		assertThrows( IllegalArgumentException.class, () -> {
			MigrationFile.of( URI.create( "file:/path/to/migration/XX1.0.0__Invalid.sql" ) );
		} );
	}
}