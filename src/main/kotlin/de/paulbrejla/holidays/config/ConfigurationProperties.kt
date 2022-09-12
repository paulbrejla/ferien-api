package de.paulbrejla.holidays.config

import de.paulbrejla.holidays.domain.AllOpen
import de.paulbrejla.holidays.domain.NoArg
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "loader")
@AllOpen
@NoArg
data class LoaderProperties(
    var source: String,
    var remoteURL: String,
    var branch: String,
    var filePath: String
)