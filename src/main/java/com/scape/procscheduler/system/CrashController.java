package com.scape.procscheduler.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to display when an exception is thrown
 *
 * @author swatik
 *
 */
@Controller
public class CrashController {

    @RequestMapping(value = "/oups", method = RequestMethod.GET)
    public String triggerException() {
        throw new RuntimeException(
                "Expected: controller to display when an exception is thrown");
    }

}
