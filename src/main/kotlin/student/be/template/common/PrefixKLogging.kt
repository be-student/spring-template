package student.be.template.common

import io.github.oshai.kotlinlogging.KLoggable
import io.github.oshai.kotlinlogging.KLogger
import student.be.template.common.KotlinPrefixLogging.Companion.prefix

abstract class PrefixKLogging(prefix: String? = null) : KLoggable {

    override val logger: KLogger by lazy {
        if (prefix.isNullOrBlank()) {
            logger().prefix()
        } else {
            logger().prefix(prefix)
        }
    }
}

class KotlinPrefixLogging(
    prefix: String,
    private val logger: KLogger,
) : KLogger by logger {
    companion object {
        private fun KLogger.simpleClassName(): String {
            val lastSeparatorIndex = this.name.lastIndexOf(".")

            return this.name.substring(lastSeparatorIndex + 1)
        }

        private fun String.sanitizeAsPrefix(): String {
            return (if (this.startsWith("[")) "" else "[") + this + (if (this.endsWith("]")) "" else "]")
        }

        fun KLogger.prefix() = KotlinPrefixLogging(this.simpleClassName().sanitizeAsPrefix(), this)

        fun KLogger.prefix(prefix: String) = KotlinPrefixLogging(prefix.sanitizeAsPrefix(), this)
    }

    private val adjustedPrefix = prefix.trimEnd() + " "

    override fun debug(throwable: Throwable?, message: () -> Any?) =
        logger.debug(throwable) { adjustedPrefix + message() }

    override fun info(throwable: Throwable?, message: () -> Any?) =
        logger.info(throwable) { adjustedPrefix + message() }

    override fun warn(throwable: Throwable?, message: () -> Any?) =
        logger.warn(throwable) { adjustedPrefix + message() }

    override fun error(throwable: Throwable?, message: () -> Any?) =
        logger.error(throwable) { adjustedPrefix + message() }

    override fun info(message: () -> Any?) = logger.info { adjustedPrefix + message() }
    override fun debug(message: () -> Any?) = logger.debug { adjustedPrefix + message() }
    override fun warn(message: () -> Any?) = logger.warn { adjustedPrefix + message() }
}
