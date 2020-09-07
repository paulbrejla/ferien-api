package de.paulbrejla.holidays.infrastructure.api

import de.paulbrejla.holidays.domain.Holiday
import de.paulbrejla.holidays.domain.State
import org.springframework.data.jpa.repository.JpaRepository

interface HolidayRepository : JpaRepository<Holiday, Long> {
    fun findOneBySummaryAndStateCodeAndYear(summary: String, stateCode: State, year: Int): Holiday?
    fun findAllByStateCode(stateCode: State): List<Holiday>
    fun findAllByStateCodeAndYear(stateCode: State, year: Int): List<Holiday>

}