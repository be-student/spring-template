package student.be.template.system.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import student.be.template.system.filter.HttpLoggingFilter
import student.be.template.system.filter.MdcFilter
import student.be.template.system.filter.RequestResponseCachingFilter

@Configuration
class FilterConfiguration {

    @Bean
    fun mdcFilter(): FilterRegistrationBean<MdcFilter> {
        val filterRegistrationBean = FilterRegistrationBean(MdcFilter())
        filterRegistrationBean.order = HIGHEST_PRECEDENCE
        filterRegistrationBean.addUrlPatterns("/*")
        return filterRegistrationBean
    }

    @Bean
    fun requestCachingFilter(): FilterRegistrationBean<RequestResponseCachingFilter> {
        val filterRegistrationBean = FilterRegistrationBean(RequestResponseCachingFilter())
        filterRegistrationBean.order = HIGHEST_PRECEDENCE + 1
        filterRegistrationBean.addUrlPatterns("/*")
        return filterRegistrationBean
    }

    @Bean
    fun httpLoggingFilter(): FilterRegistrationBean<HttpLoggingFilter> {
        val filterRegistrationBean = FilterRegistrationBean(HttpLoggingFilter())
        filterRegistrationBean.order = HIGHEST_PRECEDENCE + 2
        filterRegistrationBean.addUrlPatterns("/*")
        return filterRegistrationBean
    }
}
