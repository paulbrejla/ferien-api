package de.paulbrejla.holidays.rest.impl

import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.rest.HolidayDto
import de.paulbrejla.holidays.rest.api.HolidayWsV1
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

@RestController
class HolidayWsV1Impl(val holidayService: HolidayService) : HolidayWsV1 {

    override fun getHolidaysForState(@PathVariable("state") state: State): List<HolidayDto> {
        return holidayService.findHolidays(forState = state)
    }

    override fun getHolidays(): List<HolidayDto> {
        return handleApplicationCall { holidayService.findHolidays() }
    }

    override fun getHolidaysForStateAndYear(@PathVariable("state") state: State, @PathVariable("year") year: Int): List<HolidayDto> {
        return handleApplicationCall { holidayService.findHolidays(forState = state, andYear = year) }
    }

    private inline fun <T> handleApplicationCall(
            call: () -> T
    ): List<HolidayDto> {
        return try {
            val result = call()
            result as List<HolidayDto>
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not extract holidays for your query.")
        }
    }

}