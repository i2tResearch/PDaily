package co.icesi.pdaily.core.database.upgrader.scanner;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Scanner para leer archivos directamente del disco.
 *
 * @author cristhiank on 30/10/19
 **/
public final class FileSystemScanner implements ClasspathScanner {
	private final String path;

	FileSystemScanner(String path) {
		this.path = PathUtils.sanitizePath( path );
	}

	private static boolean isFile(Path path) {
		return path.toFile().isFile();
	}

	private static URI fromURI(Path path) {
		return path.toUri();
	}

	@Override
	public Set<URI> scanForFiles() {
		try (var stream = Files.list( Paths.get( this.path ) )
				.filter( FileSystemScanner::isFile )
				.map( FileSystemScanner::fromURI )) {
			return stream.collect( Collectors.toCollection( HashSet::new ) );
		} catch (IOException e) {
			throw new IllegalStateException( e );
		}
	}
}
