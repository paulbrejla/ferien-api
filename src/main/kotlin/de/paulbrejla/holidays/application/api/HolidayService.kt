package de.paulbrejla.holidays.application.api

import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.rest.HolidayDto

interface HolidayService {

    fun loadHolidays()
    fun findHolidays() : List<HolidayDto>
    fun findHolidays(forState: State) : List<HolidayDto>
    fun findHolidays(forState: State, andYear: Int) : List<HolidayDto>

}