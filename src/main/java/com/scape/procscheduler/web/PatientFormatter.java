package com.scape.procscheduler.web;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.repository.PatientRepository;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'Patient'.
 *
 * @author swatik
 * 
 */
@Component
public class PatientFormatter implements Formatter<Patient> {

    private final PatientRepository patient;


    @Autowired
    public PatientFormatter(PatientRepository patient) {
        this.patient = patient;
    }

    @Override
    public String print(Patient patient, Locale locale) {
        return patient.getFirstName()+" "+patient.getLastName();
    }

    @Override
    public Patient parse(String text, Locale locale) throws ParseException {
        Collection<Patient> findPatients = this.patient.findPatients();
        for (Patient patient : findPatients) {
            if ((patient.getFirstName()+" "+patient.getLastName()).equals(text)) {
                return patient;
            }
        }
        throw new ParseException("patient not found: " + text, 0);
    }

}
