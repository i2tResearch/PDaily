package co.pdaily.cloud.core

import co.pdaily.cloud.core.database.DBUpgrader
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import java.util.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class QuarkusApplication {
    private val LOGGER = Logger.getLogger("co.haruk")
    @Inject
    lateinit var upgrader: DBUpgrader

    fun onStart(@Observes ev: StartupEvent) {
        LOGGER.info("Pdaily Cloud is starting...")
        LOGGER.info("Upgrading database")
        upgrader.upgradeDatabase()
    }

    fun onStop(@Observes ev: ShutdownEvent) {
        LOGGER.info("Pdaily Cloud is stopping...")
    }
}
