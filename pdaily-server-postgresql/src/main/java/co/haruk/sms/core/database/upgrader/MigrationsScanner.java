package co.haruk.sms.core.database.upgrader;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.net.URI;
import java.net.URL;
import java.util.SortedSet;
import java.util.TreeSet;

import co.haruk.sms.core.database.upgrader.scanner.ScannerFactory;

/**
 * @author cristhiank on 30/10/19
 **/
public final class MigrationsScanner {
	private final String location;
	private final ClassLoader classLoader;

	private MigrationsScanner(String location, ClassLoader classLoader) {
		this.location = requireNonNull( location ).trim();
		this.classLoader = requireNonNull( classLoader );
	}

	public static MigrationsScanner of(String location, ClassLoader classLoader) {
		return new MigrationsScanner( location, classLoader );
	}

	public SortedSet<MigrationFile> scanForMigrations() {
		final var result = new TreeSet<MigrationFile>();
		final URL locationURL = classLoader().getResource( this.location );
		requireNonNull( locationURL, () -> "No se encontró la ubicación " + location );
		final var scanner = ScannerFactory.scannerForProtocol( locationURL.getProtocol(), locationURL.getFile() );
		final var files = scanner.scanForFiles();
		for ( URI file : files ) {
			result.add( MigrationFile.of( file ) );
		}
		return result;
	}

	private ClassLoader classLoader() {
		return this.classLoader;
	}
}
