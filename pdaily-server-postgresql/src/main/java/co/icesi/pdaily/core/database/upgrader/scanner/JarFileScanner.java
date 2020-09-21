package co.icesi.pdaily.core.database.upgrader.scanner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import org.jboss.vfs.TempFileProvider;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;

/**
 * Scanner para leer archivos dentro de wildfly.
 *
 * @author andres2508 on 30/10/19
 **/
public final class JarFileScanner implements ClasspathScanner {
	private final String filePath;
	private final String innerPath;

	JarFileScanner(String filePath, String innerPath) {
		this.filePath = filePath;
		this.innerPath = innerPath;
	}

	@Override
	public Set<URI> scanForFiles() {
		try (TempFileProvider provider = TempFileProvider.create( "tmp", Executors.newScheduledThreadPool( 2 ) )) {
			VirtualFile vfsFile = VFS.getChild( this.filePath );
			VFS.mountZip( vfsFile, vfsFile, provider );
			var children = vfsFile.getChild( innerPath ).getChildrenRecursively( VirtualFile::isFile );
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
