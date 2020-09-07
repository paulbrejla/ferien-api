package de.paulbrejla.holidays.rest.api

import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.rest.HolidayDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@RequestMapping("/api/v1", produces = ["application/json"])
interface HolidayWsV1 {

    @RequestMapping("/holidays", "/holidays.json", method = [RequestMethod.GET])
    fun getHolidays(): List<HolidayDto>

    @RequestMapping("/holidays/{state}.json", "/holidays/{state}", method = [RequestMethod.GET])
    fun getHolidaysForState(@PathVariable("state") state: State): List<HolidayDto>

    @RequestMapping("/holidays/{state}/{year}.json", "/holidays/{state}/{year}", method = [RequestMethod.GET])
    fun getHolidaysForStateAndYear(@PathVariable("state") state: State, @PathVariable("year") year: Int): List<HolidayDto>

}