package co.icesi.pdaily.core.database.upgrader.scanner;

/**
 * @author andres2508 on 30/10/19
 **/
public final class ScannerFactory {
	private ScannerFactory() {
	}

	public static ClasspathScanner scannerForProtocol(String protocol, String path) {
		switch (protocol) {
			case "file":
				return new FileSystemScanner( path );
			case "vfs":
				return new JBossVFSScanner( path );
			case "jar":
				var parts = path.split( "!" );
				if ( parts[0].startsWith( "file:" ) ) {
					parts[0] = parts[0].replaceFirst( "file:/", "/" );
				}
				return new JarFileScanner( parts[0], parts[1] );
			default:
				throw new UnsupportedOperationException( "No implemented scanner for " + protocol );
		}
	}
}
