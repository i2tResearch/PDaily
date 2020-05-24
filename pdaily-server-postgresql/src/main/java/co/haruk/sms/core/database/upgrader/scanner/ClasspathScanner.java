package co.haruk.sms.core.database.upgrader.scanner;

import java.net.URI;
import java.util.Set;

/**
 * @author andres2508 on 30/10/19
 **/
public interface ClasspathScanner {
	Set<URI> scanForFiles();
}
