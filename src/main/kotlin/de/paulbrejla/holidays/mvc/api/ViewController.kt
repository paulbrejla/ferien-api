package de.paulbrejla.holidays.mvc.api

import org.springframework.web.bind.annotation.RequestMapping

interface ViewController {

    @RequestMapping("/")
    fun frontPage() : String
}