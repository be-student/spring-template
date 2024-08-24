package student.be.template.system.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import student.be.template.common.PrefixKLogging
import java.util.*

class HttpLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val start = System.currentTimeMillis()
        filterChain.doFilter(request, response)
        val end = System.currentTimeMillis()
        try {
            val log = """
            |
            |[REQUEST] ${request.method} ${request.requestURI} ${response.status} (${end - start}ms)
            |>> CLIENT_IP: ${request.remoteAddr}
            |>> HEADERS: ${Collections.list(request.headerNames).map { it to request.getHeader(it) }}
            |>> REQUEST_PARAM: ${request.parameterMap.map { it.key to it.value.toList() }}
            |>> REQUEST_BODY: ${request.reader.readText()}
            |>> RESPONSE_BODY: ${(response as ContentCachingResponseWrapper).contentAsByteArray.toString(Charsets.UTF_8)}
            """.trimMargin()
            logger.info(log)
        } catch (e: Exception) {
            logger.error("Error while logging request and response", e)
        }
    }

    companion object : PrefixKLogging()
}
