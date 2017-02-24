package com.scape.procscheduler.web;


import java.util.Calendar;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.scape.procscheduler.model.Schedule;

/**
 * <code>Validator</code> for <code>Schedule</code> forms.
 * <p>
 * Bean Validation annotations are not used here because it is easier to define such validation rule in Java.
 * </p>
 *
 * @author swatik
 * 
 */
public class ScheduleValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public void validate(Object obj, Errors errors) {
        Schedule schedule = (Schedule) obj;
        Calendar cal = Calendar.getInstance();
      
        if (schedule.getPlannedStartTime() == null  ) {
            errors.rejectValue("plannedStartTime", REQUIRED, REQUIRED);
        } else if (schedule.getEstimatedEndTime() != null && schedule.getEstimatedEndTime().before(schedule.getPlannedStartTime())) {
	    	errors.rejectValue("estimatedEndTime", "after", "Estimated end time cannot be before Planned start time");
	    }
   }

    /**
     * This Validator validates *just* Schedule instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Schedule.class.isAssignableFrom(clazz);
    }


}
