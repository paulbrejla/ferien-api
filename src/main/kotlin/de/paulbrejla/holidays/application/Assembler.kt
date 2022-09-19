package de.paulbrejla.holidays.application

import biweekly.component.VEvent
import de.paulbrejla.holidays.domain.Holiday
import de.paulbrejla.holidays.domain.State
import java.time.*
import java.util.*

fun assembleHoliday(event: VEvent, state: String): Holiday = Holiday(id = 0, stateCode = assembleStateCode(state),
        summary = event.summary.value.lowercase(Locale.getDefault()),
        start = LocalDateTime.ofInstant(event.dateStart.value.toInstant(), ZoneOffset.UTC).toLocalDate(),
        end = LocalDateTime.ofInstant(event.dateEnd.value.toInstant(), ZoneOffset.UTC).toLocalDate(),
        year = event.dateStart.value.rawComponents.year,
        slug = assembleSlug(event.dateStart.value.rawComponents.year, event.summary.value, assembleStateCode(state)))

fun assembleSlug(startDate: Int, summary: String, stateCode: State): String = "${summary.toLowerCase()}-$startDate-$stateCode"

fun assembleStateCode(state: String): State = when (state) {
    "baden-wuerttemberg", "Baden-Wuerttemberg", "baden-württemberg" -> State.BW
    "bayern", "Bayern" -> State.BY
    "berlin", "Berlin" -> State.BE
    "brandenburg", "Brandenburg" -> State.BB
    "bremen", "Bremen" -> State.HB
    "hamburg", "Hamburg" -> State.HH
    "hessen", "Hessen" -> State.HE
    "mecklenburg-vorpommern", "Mecklenburg-Vorpommern" -> State.MV
    "niedersachsen", "Niedersachsen" -> State.NI
    "nordrhein-westfalen", "Nordrhein-Westfalen" -> State.NW
    "rheinland-pfalz", "Rheinland-Pfalz" -> State.RP
    "saarland", "Saarland" -> State.SL
    "sachsen", "Sachsen" -> State.SN
    "sachsen-anhalt", "Sachsen-Anhalt" -> State.ST
    "schleswig-holstein", "Schleswig-Holstein" -> State.SH
    "thueringen", "Thueringen", "thüringen" -> State.TH
    else -> throw Exception("Code for state '$state' not found.")
}