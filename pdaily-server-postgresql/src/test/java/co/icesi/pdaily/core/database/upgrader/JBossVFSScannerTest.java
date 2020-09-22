package co.icesi.pdaily.core.database.upgrader;

import java.io.IOException;
import java.util.concurrent.Executors;

import org.jboss.vfs.TempFileProvider;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author andres2508 on 30/10/19
 **/
@DisplayName("JBoss VFS scanner tests")
public class JBossVFSScannerTest {

	@Test
	void validLocation() throws IOException {
		var pathURL = ClassLoader.getSystemClassLoader().getResource( "file/db.jar" ).getPath();
		TempFileProvider provider = TempFileProvider.create( "tmp", Executors.newScheduledThreadPool( 2 ) );
		VirtualFile warFile = VFS.getChild( pathURL );
		var mounted = VFS.mountZip( warFile, warFile, provider );
		var children = warFile.getChildrenRecursively();
		mounted.close();
		Assertions.assertEquals( 4, children.size() );
	}
}
