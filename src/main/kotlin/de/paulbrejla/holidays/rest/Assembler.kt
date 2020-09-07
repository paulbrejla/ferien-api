package de.paulbrejla.holidays.rest

import de.paulbrejla.holidays.domain.Holiday
import java.time.ZoneOffset

fun assembleHolidayDto(holiday: Holiday) = HolidayDto(
        start = holiday.start.atOffset(ZoneOffset.UTC).toString(),
        end = holiday.end.atOffset(ZoneOffset.UTC).toString(),
        year = holiday.year,
        stateCode = holiday.stateCode,
        name = holiday.summary,
        slug = holiday.slug)