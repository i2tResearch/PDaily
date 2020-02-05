package co.icesi.pdaily.core.database.upgrader;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cristhiank on 30/10/19
 **/
public class Version implements Comparable<Version> {
	private static final Pattern versionPattern = Pattern.compile( "^\\d+.\\d+.\\d+$" );
	private long value;

	private Version(long version) {
		setVersion( version );
	}

	private Version(String version) {
		setValue( version );
	}

	public static Version of(String version) {
		return new Version( version );
	}

	public static Version of(long version) {
		return new Version( version );
	}

	public long value() {
		return value;
	}

	private void setValue(String value) {
		final var finalVersion = requireNonNull( value, "La versión es requerida" );
		final Matcher matcher = versionPattern.matcher( finalVersion );
		require( matcher.matches(), "La versión tiene un formato invalido" );
		setVersion( Long.parseLong( matcher.group( 0 ).replace( ".", "" ) ) );
	}

	private void setVersion(long version) {
		require( version >= 0, "La version debe ser positiva" );
		this.value = version;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Version version1 = (Version) o;
		return value == version1.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash( value );
	}

	@Override
	public int compareTo(Version o) {
		return Long.compare( this.value, o.value );
	}

	@Override
	public String toString() {
		return String.valueOf( value );
	}
}
