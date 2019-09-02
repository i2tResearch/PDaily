package co.pdaily.cloud.core.database

import org.eclipse.microprofile.config.inject.ConfigProperty
import org.flywaydb.core.Flyway
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class DBUpgrader {
    @Inject
    @ConfigProperty(name = "quarkus.datasource.url")
    lateinit var databaseURL: String
    @Inject
    @ConfigProperty(name = "quarkus.datasource.username")
    lateinit var databaseUser: String
    @Inject
    @ConfigProperty(name = "quarkus.datasource.password")
    lateinit var databasePassword: String
    @Inject

    fun upgradeDatabase() {
        upgradeRelational()
    }

    private fun upgradeRelational() {
        val flyway = Flyway
                .configure()
                .dataSource(databaseURL, databaseUser, databasePassword)
                .load()
        flyway.repair()
        flyway.migrate()
    }
}
