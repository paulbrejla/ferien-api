package de.paulbrejla.holidays.rest.impl

import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.rest.api.HolidayWsV1
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(
    SpringExtension::class
)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class HolidayWsV1ImplITest {
    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `api responds with a 429 if rate limit is reached after 10 requests`() {
        repeat(11) { rep ->
            mvc.perform(
                MockMvcRequestBuilders
                    .get("/api/v1/holidays/HB")
            )
                .andExpect {
                    if (rep <= 10) {
                        status().isOk
                    } else {
                        status().isTooManyRequests
                    }
                }
        }
    }

}