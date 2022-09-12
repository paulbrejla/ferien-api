package de.paulbrejla.holidays.infrastructure.loader.impl

import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import biweekly.Biweekly
import de.paulbrejla.holidays.infrastructure.CalendarDto
import de.paulbrejla.holidays.infrastructure.assembleStateFromFileName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
@ConditionalOnProperty(prefix = "loader", name = ["source"], havingValue = "filesystem")
class LocalFilesystemCalendarLoaderGateway : CalendarLoader {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Value("classpath:holidays/*.ics")
    private val calendarFiles: Array<Resource>? = null

    @PostConstruct
    fun postConstruct() {
        logger.info("Using ${this.javaClass.name} for ICS import.")
    }

    override fun loadCalendarFiles(): List<CalendarDto> {
        return calendarFiles?.map {
            CalendarDto(assembleStateFromFileName(it.filename!!.toString()),
                    calendars = Biweekly.parse(it.inputStream).all())
        }!!.toList()
    }
}