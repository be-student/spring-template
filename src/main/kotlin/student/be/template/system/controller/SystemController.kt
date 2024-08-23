package student.be.template.system.controller

import io.sentry.Sentry
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import student.be.template.common.PrefixKLogging

@RestController
class SystemController {

    @GetMapping("/health")
    fun health(): String {
        return "OK"
    }

    @GetMapping("/errorlog")
    fun errorlog(): String {
        try {
            throw RuntimeException("Error")
        } catch (e: Exception) {
            Sentry.captureException(e)
            logger.error(e) { "Error" }
            return "Error"
        }
    }

    companion object : PrefixKLogging()
}
