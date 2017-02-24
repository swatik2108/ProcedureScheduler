package com.scape.procscheduler.web;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.repository.PatientRepository;


/**
 * Test class for {@link PatientFormatter}
 *
 * @author swatik
 */
@RunWith(MockitoJUnitRunner.class)
public class PatientFormatterTests implements TestData {

    @Mock
    private PatientRepository patient;

    private PatientFormatter patientFormatter;

    @Before
    public void setup() {
        this.patientFormatter = new PatientFormatter(patient);
    }

    @Test
    public void testPrint() {
        Patient patient = new Patient();
        patient.setFirstName("Mary");
        patient.setLastName("Ann");
        String patientName = this.patientFormatter.print(patient, Locale.ENGLISH);
        assertEquals("Mary Ann", patientName);
    }

   @Test
    public void shouldParse() throws ParseException {
        Mockito.when(this.patient.findPatients()).thenReturn(makePatients());
        Patient patient = patientFormatter.parse("Mary Ann", Locale.ENGLISH);
        assertEquals("Mary", patient.getFirstName());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(this.patient.findPatients()).thenReturn(makePatients());
        patientFormatter.parse("TestFail", Locale.ENGLISH);
    }

    /**
     * Helper method to produce some sample patient types just for test purpose
     *
     * @return {@link Collection} of {@link Patient}
     */
    private List<Patient> makePatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(){
            {
            	setFirstName("Mary");
            	setLastName("Ann");
            }
        });
        patients.add(new Patient(){
            {
            	setFirstName("John");
            	setLastName("Long");
            }
        });
        return patients;
    }

}
