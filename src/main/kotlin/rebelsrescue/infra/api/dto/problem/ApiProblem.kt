package rebelsrescue.infra.api.dto.problem

import java.time.Clock
import java.time.OffsetDateTime

data class ApiProblem(
    val type: String? = null,
    val title: String? = null,
    val status: Int? = null,
    val timestamp: OffsetDateTime = OffsetDateTime.now(Clock.systemUTC()),
    val detail: String? = null,
    val instance: String? = null,
    val extensions: Map<String, Any?>? = null
)