package rebelsrescue.infra.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun logger(cls: KClass<*>): Logger = LoggerFactory.getLogger(cls.java)