package de.paulbrejla.holidays

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class HolidaysApiApplication

fun main(args: Array<String>) {
    SpringApplication.run(HolidaysApiApplication::class.java, *args)
}
