package student.be.template.system.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.apache.catalina.servlets.DefaultServlet
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.filter.GenericFilterBean
import student.be.template.common.LoggingHeaders

class MdcFilterTest {

    @BeforeEach
    fun setUp() {
        MDC.clear()
    }

    @AfterEach
    fun tearDown() {
        MDC.clear()
    }

    @Test
    fun `MdcFilter 는 trace id 가 없으면 uuid 를 MDC 에 추가해준다`() {
        // given
        val mvcInterceptor = MdcFilter()
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        // when
        mvcInterceptor.doFilter(request, response, MockFilterChain(DefaultServlet(), object : GenericFilterBean() {
            override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
                assertThat(MDC.get(LoggingHeaders.KEY_TRACE_ID.value)).isNotBlank()
                assertThat(MDC.get(LoggingHeaders.KEY_TRACE_ID.value)).hasSize(8)
            }
        }))
    }

    @Test
    fun `MdcFilter 는 trace id 가 있으면 그 값을 MDC 에 추가해준다`() {
        // given
        val mdcFilter = MdcFilter()
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val traceId = "trace-id"
        request.addHeader(LoggingHeaders.KEY_TRACE_ID.value, traceId)

        // when
        mdcFilter.doFilter(request, response, MockFilterChain(DefaultServlet(), object : GenericFilterBean() {
            override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
                assertThat(MDC.get(LoggingHeaders.KEY_TRACE_ID.value)).isEqualTo(traceId)
            }
        }))
    }
}
