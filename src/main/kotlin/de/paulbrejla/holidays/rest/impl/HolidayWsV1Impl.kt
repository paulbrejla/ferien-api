package de.paulbrejla.holidays.rest.impl

import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.rest.HolidayDto
import de.paulbrejla.holidays.rest.api.HolidayWsV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HolidayWsV1Impl @Autowired constructor(val holidayService: HolidayService) : HolidayWsV1 {

    override fun getHolidaysForState(@PathVariable("state") state: State): List<HolidayDto> {
        return holidayService.findHolidays(forState = state)
    }

    override fun getHolidays(): List<HolidayDto> {
        return holidayService.findHolidays()
    }

    override fun getHolidaysForStateAndYear(@PathVariable("state") state: State, @PathVariable("year") year: Int): List<HolidayDto> {
        return holidayService.findHolidays(forState = state, andYear = year)
    }

}