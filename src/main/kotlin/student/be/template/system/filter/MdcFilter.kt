package student.be.template.system.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import student.be.template.common.LoggingHeaders
import java.util.*

class MdcFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val traceId = request.getHeader(LoggingHeaders.KEY_TRACE_ID.value)
            ?: UUID.randomUUID().toString().substring(0, 8)
        MDC.put(LoggingHeaders.KEY_TRACE_ID.value, traceId)
        filterChain.doFilter(request, response)
        MDC.clear()
    }
}
