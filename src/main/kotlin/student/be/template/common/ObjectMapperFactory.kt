package student.be.template.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperFactory {
    @Bean
    fun objectMapper(): ObjectMapper {
        return INSTANCE
    }

    companion object {
        private fun getInstance(): ObjectMapper {
            return ObjectMapper()
                .registerModule(KotlinModule.Builder().build())
                .registerModule(
                    JavaTimeModule()
                        .addSerializer(
                            LocalDateTime::class.java,
                            LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        )
                        .addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
                )
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

        val INSTANCE = getInstance()
    }
}
