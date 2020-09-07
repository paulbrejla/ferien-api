package de.paulbrejla.holidays.rest.api

import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.rest.HolidayDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/api/v1")
interface HolidayWsV1 {

    @RequestMapping("/holidays")
    fun getHolidays() : List<HolidayDto>

    @RequestMapping("/holidays/{state}")
    fun getHolidaysForState(@PathVariable("state") state: State) : List<HolidayDto>

    @RequestMapping("/holidays/{state}/{year}")
    fun getHolidaysForStateAndYear(@PathVariable("state") state: State, @PathVariable("year") year: Int) : List<HolidayDto>

}