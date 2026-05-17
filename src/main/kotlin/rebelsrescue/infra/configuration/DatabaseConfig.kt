package rebelsrescue.infra.configuration

import com.sksamuel.hoplite.Masked
import com.sksamuel.hoplite.Secret

data class DatabaseConfig(
    val username: String = "",
    val password: Masked = Masked(""),
    val url: String = "",
    val driver: String = "",
    val dialect: String = ""
)