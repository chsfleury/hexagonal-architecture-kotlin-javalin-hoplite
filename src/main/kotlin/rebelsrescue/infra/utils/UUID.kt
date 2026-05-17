package rebelsrescue.infra.utils

import java.util.UUID

fun String.toUUID(): UUID? = try {
    UUID.fromString(this)
} catch (e: IllegalArgumentException) {
    null
}