package de.paulbrejla.holidays.application

import biweekly.ICalendar
import biweekly.component.ICalComponent
import biweekly.component.VEvent
import biweekly.property.DateStart
import biweekly.property.DateTimeStamp
import biweekly.util.ICalDate
import de.paulbrejla.holidays.domain.Holiday
import de.paulbrejla.holidays.domain.State
import java.time.*
import java.time.temporal.TemporalAccessor

fun assembleHoliday(event : VEvent, state: String) : Holiday = Holiday(id = 0, stateCode = assembleStateCode(state),
        summary = event.summary.value.toLowerCase(),
        start = LocalDateTime.ofInstant(event.dateStart.value.toInstant(), ZoneOffset.UTC) ,
        end = LocalDateTime.ofInstant(event.dateEnd.value.toInstant(), ZoneOffset.UTC),
        year = event.dateStart.value.rawComponents.year,
        slug = assembleSlug(event.dateStart.value.rawComponents.year, event.summary.value, assembleStateCode(state)))

fun assembleSlug(startDate: Int, summary: String, stateCode: State) : String = "${summary.toLowerCase()}-$startDate-$stateCode"

fun assembleStateCode(state: String) : State = when(state){
    "baden-wuerttemberg" -> State.BW
    "bayern" -> State.BY
    "berlin" -> State.BE
    "brandenburg" -> State.BB
    "bremen" -> State.HB
    "hamburg"-> State.HH
    "hessen" -> State.HE
    "mecklenburg-vorpommern" -> State.MV
    "niedersachsen" -> State.NI
    "nordrhein-westfalen" -> State.NW
    "rheinland-pfalz"-> State.RP
    "saarland" -> State.SL
    "sachsen" -> State.SN
    "sachsen-anhalt" -> State.ST
    "schleswig-holstein" -> State.SH
    "thueringen" -> State.TH
    else -> throw Exception("Code for state '$state' not found.")
}