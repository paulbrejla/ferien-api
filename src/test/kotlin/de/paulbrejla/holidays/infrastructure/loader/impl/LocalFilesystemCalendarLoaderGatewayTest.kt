package de.paulbrejla.holidays.infrastructure.loader.impl

import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class LocalFilesystemCalendarLoaderGatewayTest {

    @Autowired
    lateinit var calendarLoader: CalendarLoader

    @Test
    fun `calendar files are loaded`() {
        // Given
        val eventCount = 5

        // When
        val calendars = calendarLoader.loadCalendarFiles()

        // Then
        assertNotNull(calendars.first().calendars)
        assertEquals(calendars.first().calendars.first().events.count(), eventCount)

    }
}