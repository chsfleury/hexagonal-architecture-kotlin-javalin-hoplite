package rebelsrescue.infra.configuration

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addEnvironmentSource
import com.sksamuel.hoplite.addResourceSource
import rebelsrescue.infra.utils.logger

data class Config(
    val server: JavalinConfig,
    val database: DatabaseConfig,
    val swapi: SwapiConfig
) {
    companion object {
        private val log = logger(Config::class)

        operator fun invoke(propertySource: String): Config {
            val config = ConfigLoaderBuilder.default()
                .addEnvironmentSource(useUnderscoresAsSeparator = true, allowUppercaseNames = true)
                .addResourceSource(propertySource)
                .build()
                .loadConfigOrThrow<Config>()

            log.info("Configuration loaded")
            log.info(config.toString())
            return config
        }
    }
}