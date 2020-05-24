package co.haruk.sms.core.database.upgrader;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * @author cristhiank on 31/10/19
 **/
public final class DBSchema {
	private static final String historySchemeSQL = "CREATE TABLE IF NOT EXISTS haruk_schema_history(version LONG PRIMARY KEY,filename VARCHAR(255))";
	private static final String versionInsert = "INSERT INTO haruk_schema_history(version, filename) VALUES(?,?)";
	private static final String currentVersionQuery = "SELECT MAX(version) FROM haruk_schema_history";
	private final DataSource dataSource;
	private String catalog;
	private String schema;
	private String vendor;
	private Version currentVersion;

	private DBSchema(DataSource dataSource) {
		this.dataSource = dataSource;
		initialize();
	}

	static DBSchema forDataSource(DataSource dataSource) {
		return new DBSchema( dataSource );
	}

	public String catalog() {
		return catalog;
	}

	public String schema() {
		return schema;
	}

	public String vendor() {
		return vendor;
	}

	Connection connection() throws SQLException {
		return dataSource.getConnection();
	}

	public Version currentVersion() {
		return currentVersion;
	}

	void setLatestMigration(MigrationFile migration) {
		requireNonNull( migration );
		try (PreparedStatement stmt = connection().prepareStatement( versionInsert )) {
			stmt.setLong( 1, migration.version().value() );
			stmt.setString( 2, migration.fileName() );
			stmt.executeUpdate();
			this.currentVersion = migration.version();
		} catch (SQLException e) {
			throw new IllegalStateException( e );
		}
	}

	private void initialize() {
		try (var connection = connection();
				var statement = connection.createStatement()) {
			statement.execute( historySchemeSQL );
			extractMetadata( connection );
			readCurrentVersion( statement );
		} catch (SQLException e) {
			throw new IllegalStateException( e );
		}
	}

	private void readCurrentVersion(Statement statement) throws SQLException {
		try (ResultSet rs = statement.executeQuery( currentVersionQuery )) {
			long readVersion = 0;
			while ( rs.next() ) {
				readVersion = rs.getLong( 1 );
			}
			this.currentVersion = Version.of( readVersion );
		}
	}

	private void extractMetadata(Connection connection) throws SQLException {
		this.catalog = connection.getCatalog();
		this.schema = connection.getSchema();
		this.vendor = connection.getMetaData().getDatabaseProductName();
	}

	@Override
	public String toString() {
		return "vendor=" + vendor + ", schema=" + schema + ", catalog=" + catalog + ", version=" + currentVersion;
	}

	void applyMigration(MigrationFile migration) {
		requireNonNull( migration );
		if ( migration.version().compareTo( this.currentVersion ) > 0 ) {
			migration.applyTo( this );
		}
	}
}
