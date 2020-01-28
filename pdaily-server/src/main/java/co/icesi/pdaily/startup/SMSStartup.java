package co.icesi.pdaily.startup;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import co.icesi.pdaily.core.database.upgrader.DBUpgrader;
import co.icesi.pdaily.core.database.upgrader.UpgraderConfig;

/**
 * @author cristhiank on 20/11/19
 **/
@Dependent
public class SMSStartup {
	private final Logger logger = Logger.getLogger( "HARUK" );

	public void startPlatform() {
		logger.warning( "=== Starting HARUK ===" );
		upgradeDatabase();
	}

	private void upgradeDatabase() {
		final UpgraderConfig config = UpgraderConfig.of( "db/migration" );
		final DBUpgrader upgrader = DBUpgrader.of( config, SMSStartup.class.getClassLoader() );
		upgrader.upgrade( IDataSourceProvider.current().getDataSource() );
	}
}
