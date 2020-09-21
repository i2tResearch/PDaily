package co.icesi.pdaily.startup;

import java.util.logging.Logger;

import co.haruk.core.domain.model.events.EventBus;
import co.haruk.core.domain.model.persistence.upgrader.IDataSourceUpgrader;

/**
 * @author andres2508 on 20/11/19
 **/
public class SMSStartup {
	public static boolean STARTED = false;
	private static final Logger logger = Logger.getLogger( SMSStartup.class.getName() );

	private SMSStartup() {
	}

	public static void startPlatform() {
		logger.warning( "=== Starting HARUK ===" );
		upgradeDatabase();
		EventBus.fireSyncEvent( new SMSStartedEvent() {
		} );
		STARTED = true;
	}

	private static void upgradeDatabase() {
		IDataSourceUpgrader.current().upgradeDefaultDataSource();
	}
}
