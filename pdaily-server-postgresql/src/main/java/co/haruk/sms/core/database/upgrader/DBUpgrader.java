package co.haruk.sms.core.database.upgrader;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * @author cristhiank on 30/10/19
 **/
public class DBUpgrader {
	private static final Logger log = Logger.getLogger( DBUpgrader.class.getName() );

	private final UpgraderConfig config;
	private final ClassLoader classLoader;

	private DBUpgrader(UpgraderConfig config, ClassLoader classLoader) {
		this.config = requireNonNull( config );
		this.classLoader = requireNonNull( classLoader );
	}

	public static DBUpgrader of(UpgraderConfig config, ClassLoader classLoader) {
		return new DBUpgrader( config, classLoader );
	}

	private TreeSet<MigrationFile> scanLocations() {
		TreeSet<MigrationFile> migrations = new TreeSet<>();
		var locations = config.locations.split( "," );
		for ( String location : locations ) {
			SortedSet<MigrationFile> found = MigrationsScanner.of( location, classLoader ).scanForMigrations();
			migrations.addAll( found );
		}
		return migrations;
	}

	public void upgrade(DataSource dataSource) {
		var migrations = scanLocations();
		var schema = resolveSchema( dataSource );
		log.info( () -> "Upgrading database: " + schema );
		log.info( () -> "Current schema version is " + schema.currentVersion() );
		for ( MigrationFile migration : migrations ) {
			log.fine( () -> "Applying migration " + migration );
			schema.applyMigration( migration );
		}
		log.info( () -> "New schema version is " + schema.currentVersion() );
	}

	private DBSchema resolveSchema(DataSource dataSource) {
		return DBSchema.forDataSource( dataSource );
	}
}
