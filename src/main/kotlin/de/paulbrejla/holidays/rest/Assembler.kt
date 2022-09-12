package de.paulbrejla.holidays.rest

import de.paulbrejla.holidays.domain.Holiday

fun assembleHolidayDto(holiday: Holiday) = HolidayDto(
        start = holiday.start,
        end = holiday.end,
        year = holiday.year,
        stateCode = holiday.stateCode,
        name = holiday.summary,
        slug = holiday.slug)