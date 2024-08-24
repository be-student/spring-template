package student.be.template.common

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class ObjectMapperFactoryTest {

    @Test
    fun `objectMapper를 생성하면 null 이 아니다`() {
        val objectMapper = ObjectMapperFactory.INSTANCE
        assertNotNull(objectMapper)
    }

    @Nested
    inner class `LocalDateTime이` {
        @Test
        fun `iso 형태로 serialize 된다`() {
            //given
            val objectMapper = ObjectMapperFactory.INSTANCE

            //when
            val result = objectMapper.writeValueAsString(LocalDateTime.of(2021, 1, 1, 0, 0, 0, 300))

            //then
            assertThat(result).isEqualTo("\"2021-01-01T00:00:00.0000003\"");
        }

        @Test
        fun `iso 형태에서 deserialize 된다1`() {
            //given
            val objectMapper = ObjectMapperFactory.INSTANCE

            //when
            val result = objectMapper.readValue("\"2021-01-01T00:00:00.0000003\"", LocalDateTime::class.java)

            //then
            assertThat(result).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0, 0, 300))
        }
    }

    @Nested
    inner class `LocalDate이` {
        @Test
        fun `iso 형태로 serialize 된다`() {
            //given
            val objectMapper = ObjectMapperFactory.INSTANCE

            //when
            val result = objectMapper.writeValueAsString(LocalDate.of(2021, 1, 1))

            //then
            assertThat(result).isEqualTo("\"2021-01-01\"");
        }

        @Test
        fun `iso 형태에서 deserialize 된다1`() {
            //given
            val objectMapper = ObjectMapperFactory.INSTANCE

            //when
            val result = objectMapper.readValue("\"2021-01-01\"", LocalDate::class.java)

            //then
            assertThat(result).isEqualTo(LocalDate.of(2021, 1, 1))
        }
    }

    @Test
    fun `objectMapper 에서 없는 property를 읽으려고 해도 에러가 나지 않는다`() {
        //given
        val objectMapper = ObjectMapperFactory.INSTANCE

        //when
        val result = objectMapper.readValue("{\"name\":\"test\"}", TestObject::class.java)

        //then
        assertThat(result).isEqualTo(TestObject("test"))
    }

    private data class TestObject(val name: String)
}
