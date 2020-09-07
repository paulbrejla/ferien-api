package de.paulbrejla.holidays.application.impl

import biweekly.component.VEvent
import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.application.assembleHoliday
import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import de.paulbrejla.holidays.infrastructure.api.HolidayRepository
import de.paulbrejla.holidays.rest.HolidayDto
import de.paulbrejla.holidays.rest.assembleHolidayDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class HolidayServiceImpl @Autowired constructor(val calendarLoader: CalendarLoader,
                                                val holidayRepository: HolidayRepository) : HolidayService {
    override fun findHolidays(forState: State,  andYear: Int): List<HolidayDto> {

        return holidayRepository.findAllByStateCodeAndYear(stateCode = forState, year = andYear).map {
            assembleHolidayDto(it)
        }
    }

    override fun findHolidays() : List<HolidayDto> {
        return holidayRepository.findAll().map{
            assembleHolidayDto(it)
        }
    }

    override fun findHolidays(forState: State) : List<HolidayDto> {
        return holidayRepository.findAllByStateCode(forState).map { assembleHolidayDto(it) }
    }

    @Scheduled(fixedRate = 900000, initialDelay = 15000)
    override fun loadHolidays() {
        calendarLoader.loadCalendarFiles().forEach { (state, calendars) ->
            calendars.forEach {it.events.forEach { it : VEvent ->
                val holiday = assembleHoliday(it, state)
                if(holidayRepository.findOneBySummaryAndStateCodeAndYear(summary = holiday.summary, stateCode = holiday.stateCode, year = holiday.year) == null) {
                    holidayRepository.save(holiday)
                }
            } }
        }
    }
}