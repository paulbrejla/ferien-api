package de.paulbrejla.holidays.infrastructure

import biweekly.ICalendar

data class CalendarDto(val state: String, val calendars: List<ICalendar>)