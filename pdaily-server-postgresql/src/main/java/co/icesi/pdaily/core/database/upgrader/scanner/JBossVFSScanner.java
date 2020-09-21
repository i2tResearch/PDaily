package co.icesi.pdaily.core.database.upgrader.scanner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;

/**
 * Scanner para leer archivos dentro de wildfly.
 *
 * @author andres2508 on 30/10/19
 **/
public final class JBossVFSScanner implements ClasspathScanner {
	private final String path;

	JBossVFSScanner(String path) {
		this.path = path;
	}

	@Override
	public Set<URI> scanForFiles() {
		try {
			VirtualFile vfsFile = VFS.getChild( this.path );
			var children = vfsFile.getChildrenRecursively( VirtualFile::isFile );
			Set<URI> result = new HashSet<>();
			for ( VirtualFile child : children ) {
				result.add( child.asFileURI() );
			}
			return result;
		} catch (URISyntaxException | IOException e) {
			throw new IllegalStateException( e );
		}
	}
}
