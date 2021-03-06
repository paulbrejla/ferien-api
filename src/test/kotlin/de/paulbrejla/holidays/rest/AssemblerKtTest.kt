package de.paulbrejla.holidays.rest

import de.paulbrejla.holidays.domain.Holiday
import de.paulbrejla.holidays.domain.State
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime

class AssemblerKtTest {

    @Test
    fun `a HolidayDto is assembled from a Holiday`() {
        // Given
        val holiday = Holiday(id = 1, stateCode = State.HB, year = 2020, summary = "Winterferien Bremen",
                start = LocalDateTime.now(), end = LocalDateTime.now().plusYears(2), slug = "ferien-hb")

        // When
        val holidayDto = assembleHolidayDto(holiday)

        // Then
        assertNotNull(holiday)
    }
}