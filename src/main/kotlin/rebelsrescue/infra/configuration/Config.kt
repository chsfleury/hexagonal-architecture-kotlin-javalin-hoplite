package rebelsrescue.infra.configuration

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addEnvironmentSource
import com.sksamuel.hoplite.addResourceOrFileSource
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
                .addResourceOrFileSource(propertySource)
                .build()
                .loadConfigOrThrow<Config>()

            log.info("Configuration loaded from $propertySource")
            log.info(config.toString())
            return config
        }
    }
}