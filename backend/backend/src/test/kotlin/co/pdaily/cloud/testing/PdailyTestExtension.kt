package co.pdaily.cloud.testing

import org.hsqldb.cmdline.SqlFile
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class PdailyTestExtension : BeforeAllCallback, AfterAllCallback {
    private var configuration = Properties()

    private var sqlConnection: Connection? = null

    init {
        configuration.load(Thread.currentThread().contextClassLoader.getResourceAsStream("application.properties"))
    }

    override fun beforeAll(context: ExtensionContext) {
        println("Executing SONAR test extension")
        val annotation = context.requiredTestClass.getAnnotation(PdailyTest::class.java)
        if (annotation.sqlDataSets.isNotEmpty()) {
            loadSQLDataSets(annotation.sqlDataSets)
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        closeConnections()
    }

    private fun closeConnections() {
        sqlConnection?.close()
    }

    private fun loadSQLDataSets(sqlDataSets: Array<String>) {
        openSQLConnection()
        val connection = sqlConnection ?: throw IllegalStateException("SQL Connection null")
        for (dataset in sqlDataSets) {
            loadSQLDataSet(dataset, connection)
        }
    }

    private fun loadSQLDataSet(dataset: String, connection: Connection) {
        javaClass.getResourceAsStream(dataset).use { inputStream ->
            val sqlFile = SqlFile(InputStreamReader(inputStream), "init", System.out, "UTF-8", false, File("."))
            sqlFile.connection = connection
            sqlFile.execute()
        }

    }

    private fun openSQLConnection() {
        if (this.sqlConnection == null) {
            val databaseURL = configuration.getProperty("quarkus.datasource.url")
            val databaseUser = configuration.getProperty("quarkus.datasource.username")
            val databasePassword = configuration.getProperty("quarkus.datasource.password")
            this.sqlConnection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword)
        }
    }
}
