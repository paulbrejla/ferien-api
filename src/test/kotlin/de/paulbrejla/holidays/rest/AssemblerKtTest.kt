package de.paulbrejla.holidays.rest

import de.paulbrejla.holidays.domain.Holiday
import de.paulbrejla.holidays.domain.State
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class AssemblerKtTest {

    @Test
    fun `a HolidayDto is assembled from a Holiday`() {
        // Given
        val holiday = Holiday(id = 1, stateCode = State.HB, year = 2020, summary = "Winterferien Bremen",
                start = LocalDate.now(), end = LocalDate.now().plusYears(2), slug = "ferien-hb")

        // When
        val holidayDto = assembleHolidayDto(holiday)

        // Then
        assertNotNull(holidayDto)
    }
}