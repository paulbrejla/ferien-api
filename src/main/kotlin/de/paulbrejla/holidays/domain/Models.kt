package de.paulbrejla.holidays.domain

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*


@AllOpen
@NoArg
@Entity
data class Holiday(@Id
                   @GeneratedValue(strategy= GenerationType.AUTO) var id: Long,
                   @Enumerated(EnumType.STRING) var stateCode: State,
                   val year: Int,
                   var summary: String,
                   var start: LocalDateTime,
                   var end: LocalDateTime,
                   var slug: String) : Serializable