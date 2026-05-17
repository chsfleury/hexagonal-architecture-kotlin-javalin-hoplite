package rebelsrescue.infra.persistence

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DefaultDSLContext
import rebelsrescue.infra.configuration.DatabaseConfig
import javax.sql.DataSource

object Database {

    fun datasource(config: DatabaseConfig) = datasource(
        config.username,
        config.password.value,
        config.url,
        config.driver
    )

    private fun datasource(user: String, pass: String, url: String, driver: String) = try {
        HikariConfig().apply {
            username = user
            password = pass
            jdbcUrl = url
            driverClassName = driver
            connectionTimeout = 30000L
        }.let(::HikariDataSource)
    } catch (e: Exception) {
        throw RuntimeException("Failed to create datasource: url=$url, user=$user, pass=${pass.first()}...${pass.last()}", e)
    }

    fun migrate(dataSource: DataSource): MigrateResult = Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:db/migration/mysql")
        .load()
        .migrate()

    fun jooq(dataSource: DataSource, config: DatabaseConfig): DSLContext = DefaultDSLContext(dataSource, SQLDialect.valueOf(config.dialect))

}