package com.scape.procscheduler.web;


import java.util.Calendar;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.scape.procscheduler.model.Patient;

/**
 * <code>Validator</code> for <code>Patient</code> forms.
 * <p>
 * We're not using Bean Validation annotations here because it is easier to define such validation rule in Java.
 * </p>
 *
 * @author swatik
 * 
 */
public class PatientValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public void validate(Object obj, Errors errors) {
        Patient patient = (Patient) obj;
        String name = patient.getFirstName();
        Calendar cal = Calendar.getInstance();
        
        // name validation
        if (!StringUtils.hasLength(name)) {
            errors.rejectValue("firstName", REQUIRED, REQUIRED);
        }  
  
        //Date of birth should be in the past
        if (patient.getDateOfBirth() != null && patient.getDateOfBirth().after(cal.getTime())) {
	        errors.rejectValue("dateOfBirth", "before", "Date of birth cannot be in the future");
	    }
        
    }

    /**
     * This validator validates *just* Patient instances
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.isAssignableFrom(clazz);
    }


}
