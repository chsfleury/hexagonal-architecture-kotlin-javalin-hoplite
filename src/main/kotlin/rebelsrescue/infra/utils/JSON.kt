package rebelsrescue.infra.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

object JSON: ObjectMapper() {
    private fun readResolve(): Any = JSON

    init {
        registerModule(KotlinModule.Builder().build())
    }

}