package student.be.template.system.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper


class RequestResponseCachingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val contentCachingRequestWrapper = ContentCachingRequestWrapper(request)
        val contextCachingResponseWrapper = ContentCachingResponseWrapper(response)
        filterChain.doFilter(contentCachingRequestWrapper, contextCachingResponseWrapper)
        contextCachingResponseWrapper.copyBodyToResponse()
    }
}
