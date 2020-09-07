package de.paulbrejla.holidays.rest

import de.paulbrejla.holidays.domain.State
import java.time.LocalDateTime

data class HolidayDto(val start: String, val end: String, val year: Int, val stateCode: State, val name: String, val slug: String)