package de.paulbrejla.holidays.domain

import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*


@AllOpen
@NoArg
@Entity
data class Holiday(@Id
                   @GeneratedValue(strategy= GenerationType.AUTO) var id: Long,
                   @Enumerated(EnumType.STRING) var stateCode: State,
                   val year: Int,
                   var summary: String,
                   var start: LocalDate,
                   var end: LocalDate,
                   var slug: String) : Serializable