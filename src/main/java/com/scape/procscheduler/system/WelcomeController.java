package com.scape.procscheduler.system;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Landing page of the application
 *
 * @author swatik
 *
 */

@Controller
class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }
}
