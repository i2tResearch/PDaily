package co.icesi.pdaily.core.database.upgrader;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.core.database.upgrader.scanner.PathUtils;

/**
 * @author cristhiank on 30/10/19
 **/
public class MigrationFile implements Comparable<MigrationFile> {
	private static final Pattern filePattern = Pattern.compile( "^V(\\d+.\\d+.\\d+)__.*\\.sql$" );
	private final URI fileURL;
	private final Version version;

	private MigrationFile(URI fileURL, Version version) {
		this.fileURL = fileURL;
		this.version = version;
	}

	public static MigrationFile of(URI fileURI) {
		final var path = PathUtils.sanitizePath( fileURI.getPath() );
		var matcher = filePattern.matcher( Path.of( path ).getFileName().toString() );
		Guards.require( matcher.matches(), () -> "El archivo tiene un nombre invalido " + fileURI );
		return new MigrationFile( fileURI, Version.of( matcher.group( 1 ) ) );
	}

	public String fileName() {
		return Path.of( PathUtils.sanitizePath( fileURL.getPath() ) ).getFileName().toString();
	}

	public Version version() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		MigrationFile that = (MigrationFile) o;
		return version.equals( that.version );
	}

	@Override
	public int hashCode() {
		return Objects.hash( version );
	}

	@Override
	public int compareTo(MigrationFile o) {
		return version.compareTo( o.version );
	}

	@Override
	public String toString() {
		return fileURL.getRawPath() + " version=" + version;
	}

	void applyTo(DBSchema schema) {
		try (Connection connection = requireNonNull( schema ).connection();
				Statement stmt = connection.createStatement()) {
			String sqlQuery = readFileContent();
			List<String> queries = Arrays.stream( sqlQuery.split( ";" ) )
					.filter( query -> !query.isBlank() )
					.collect( Collectors.toList() );
			for ( String query : queries ) {
				stmt.execute( query );
			}
			schema.setLatestMigration( this );
		} catch (IOException | SQLException e) {
			throw new IllegalStateException( e );
		}
	}

	private String readFileContent() throws IOException {
		try (InputStream stream = this.fileURL.toURL().openStream();
				BufferedReader buffer = new BufferedReader( new InputStreamReader( stream ) )) {
			List<String> br = buffer.lines().collect( Collectors.toList() );
			return String.join( "", br );
		}
	}
}
