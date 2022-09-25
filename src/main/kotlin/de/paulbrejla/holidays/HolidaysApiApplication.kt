package de.paulbrejla.holidays

import de.paulbrejla.holidays.config.LoaderProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(LoaderProperties::class)
class HolidaysApiApplication

fun main(args: Array<String>) {
    SpringApplication.run(HolidaysApiApplication::class.java, *args)
}
