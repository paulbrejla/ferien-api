package de.paulbrejla.holidays.rest.impl

import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.infrastructure.api.HolidayRepository
import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import de.paulbrejla.holidays.rest.api.HolidayWsV1
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(
    SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HolidayWsV1ImplTest {

    @Autowired lateinit var holidayWsV1: HolidayWsV1
    @Autowired lateinit var holidayService: HolidayService

    @BeforeAll
    fun setUp(){
        holidayService.loadHolidays()
    }

    @Test
    fun `returns holidays for a given state`() {
        // Given
        val state = State.BW
        val expectedSize = 5

        // When
        val holidays = holidayWsV1.getHolidaysForState(state)

        // Then
        assertNotNull(holidays)
        assertEquals(expectedSize, holidays.size)
    }

    @Test
    fun `returns all holidays`() {
        // Given
        val expectedSize = holidayService.findHolidays().size

        // When
        val holidays = holidayWsV1.getHolidays()

        // Then
        assertNotNull(holidays)
        assertEquals(expectedSize, holidays.size)

    }

    @Test
    fun `returns holidays for state and year`() {
        // Given
        val state = State.BW
        val expectedSize = 5
        val year = 2017

        // When
        val holidays = holidayWsV1.getHolidaysForStateAndYear(state, year)

        // Then
        assertNotNull(holidays)
        assertEquals(expectedSize, holidays.size)
    }

}