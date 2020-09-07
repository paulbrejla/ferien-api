package de.paulbrejla.holidays.infrastructure

import de.paulbrejla.holidays.infrastructure.loader.impl.LocalFilesystemCalendarLoaderGateway
import org.apache.commons.io.IOUtils

fun assembleIcsFileNames() : List<String> =  IOUtils.readLines(LocalFilesystemCalendarLoaderGateway::class.java
            .getResourceAsStream("/holidays"), Charsets.UTF_8)

fun assembleIcsString(fileName: String) = IOUtils.toString(
        LocalFilesystemCalendarLoaderGateway::class.java.getResourceAsStream(fileName),
        "UTF-8")!!

fun assembleStateFromFileName(fileName: String) = fileName.split("_")[1]