package de.paulbrejla.holidays.application.impl

import biweekly.component.VEvent
import de.paulbrejla.holidays.application.api.HolidayService
import de.paulbrejla.holidays.application.assembleHoliday
import de.paulbrejla.holidays.domain.State
import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import de.paulbrejla.holidays.infrastructure.api.HolidayRepository
import de.paulbrejla.holidays.rest.HolidayDto
import de.paulbrejla.holidays.rest.assembleHolidayDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class HolidayServiceImpl(val calendarLoader: CalendarLoader,
                         val holidayRepository: HolidayRepository) : HolidayService {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun findHolidays(forState: State, andYear: Int): List<HolidayDto> {

        return holidayRepository.findAllByStateCodeAndYear(stateCode = forState, year = andYear).map {
            assembleHolidayDto(it)
        }
    }

    override fun findHolidays(): List<HolidayDto> {
        return holidayRepository.findAll().map {
            assembleHolidayDto(it)
        }
    }

    override fun findHolidays(forState: State): List<HolidayDto> {
        return holidayRepository.findAllByStateCode(forState).map { assembleHolidayDto(it) }
    }

    @Scheduled(fixedRate = 1500000, initialDelay = 5000)
    override fun loadHolidays() {
        calendarLoader.loadCalendarFiles().forEach { (state, calendars) ->
            calendars.forEach {
                it.events.forEach { vEvent: VEvent ->
                    try{
                        val holiday = assembleHoliday(vEvent, state)
                        if (holidayRepository.findOneBySummaryAndStateCodeAndYear(summary = holiday.summary, stateCode = holiday.stateCode, year = holiday.year) == null) {
                            holidayRepository.save(holiday)
                        }
                    }catch (e: Exception){
                        logger.error("Could not extract event $e for state $state")
                    }

                }
            }
        }
    }
}