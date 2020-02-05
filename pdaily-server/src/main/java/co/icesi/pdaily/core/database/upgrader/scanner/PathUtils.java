package co.icesi.pdaily.core.database.upgrader.scanner;

public final class PathUtils {

	private PathUtils() {
	}

	public static String sanitizePath(String path) {
		return path.replaceFirst( "^/(.:/)", "$1" );
	}
}
