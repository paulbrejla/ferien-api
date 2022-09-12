package de.paulbrejla.holidays.rest

import com.fasterxml.jackson.annotation.JsonFormat
import de.paulbrejla.holidays.domain.State
import java.time.LocalDate

data class HolidayDto(
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") val start: LocalDate,
    @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") val end: LocalDate,
    val year: Int,
    val stateCode: State,
    val name: String,
    val slug: String
)