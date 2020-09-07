package de.paulbrejla.holidays.infrastructure.loader.impl

import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import biweekly.Biweekly
import de.paulbrejla.holidays.infrastructure.CalendarDto
import de.paulbrejla.holidays.infrastructure.assembleStateFromFileName
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component


@Component
class LocalFilesystemCalendarLoaderGateway : CalendarLoader {

    @Value("classpath:holidays/*.ics")
    private val calendarFiles: Array<Resource>? = null

    override fun loadCalendarFiles(): List<CalendarDto> {
        return calendarFiles?.map {
            CalendarDto(assembleStateFromFileName(it.filename.toString()),
                    calendars = Biweekly.parse(it.inputStream).all())
        }!!.toList()
    }
}