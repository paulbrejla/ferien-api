package de.paulbrejla.holidays.mvc.impl

import de.paulbrejla.holidays.mvc.api.ViewController
import org.springframework.stereotype.Controller

@Controller
class ViewControllerImpl : ViewController {

    override fun frontPage(): String {
        return "index"
    }
}