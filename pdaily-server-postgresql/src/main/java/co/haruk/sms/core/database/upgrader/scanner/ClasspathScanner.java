package co.haruk.sms.core.database.upgrader.scanner;

import java.net.URI;
import java.util.Set;

/**
 * @author cristhiank on 30/10/19
 **/
public interface ClasspathScanner {
	Set<URI> scanForFiles();
}
