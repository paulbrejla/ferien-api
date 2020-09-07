package de.paulbrejla.holidays.infrastructure.loader.api

import de.paulbrejla.holidays.infrastructure.CalendarDto

interface CalendarLoader {

    fun loadCalendarFiles() : List<CalendarDto>
}