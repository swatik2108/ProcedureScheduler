package com.scape.procscheduler.web;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import org.springframework.stereotype.Component;

import com.scape.procscheduler.model.Doctor;
import com.scape.procscheduler.repository.DoctorRepository;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'Doctor'. 
 * 
 * @author swatik
 */
@Component
public class DoctorFormatter implements Formatter<Doctor> {

    private final DoctorRepository doctor;


    @Autowired
    public DoctorFormatter(DoctorRepository doctor) {
        this.doctor = doctor;
    }

    @Override
    public String print(Doctor doctor, Locale locale) {
        return doctor.getName();
    }

    @Override
    public Doctor parse(String text, Locale locale) throws ParseException {
        Collection<Doctor> findDoctors = this.doctor.findDoctors();
        for (Doctor doctor : findDoctors) {
            if (doctor.getName().equals(text)) {
                return doctor;
            }
        }
        throw new ParseException("doctor not found: " + text, 0);
    }

}
