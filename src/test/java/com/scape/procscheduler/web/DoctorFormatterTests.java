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
import com.scape.procscheduler.model.Doctor;
import com.scape.procscheduler.repository.DoctorRepository;


/**
 * Test class for {@link DoctorFormatter}
 *
 * @author swatik
 */
@RunWith(MockitoJUnitRunner.class)
public class DoctorFormatterTests implements TestData {

    @Mock
    private DoctorRepository doctor;

    private DoctorFormatter doctorFormatter;

    @Before
    public void setup() {
        this.doctorFormatter = new DoctorFormatter(doctor);
    }

    @Test
    public void testPrint() {
        Doctor doctor = new Doctor();
        doctor.setName("Prof. Dr. Felix Berger");
        String doctorName = this.doctorFormatter.print(doctor, Locale.ENGLISH);
        assertEquals("Prof. Dr. Felix Berger", doctorName);
    }

   @Test
    public void shouldParse() throws ParseException {
        Mockito.when(this.doctor.findDoctors()).thenReturn(makeDoctors());
        Doctor doctor = doctorFormatter.parse("Prof. Dr. Felix Berger", Locale.ENGLISH);
        assertEquals("Prof. Dr. Felix Berger", doctor.getName());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(this.doctor.findDoctors()).thenReturn(makeDoctors());
        doctorFormatter.parse("TestFail", Locale.ENGLISH);
    }

    /**
     * Helper method to produce some sample doctor types just for test purpose
     *
     * @return {@link Collection} of {@link Doctor}
     */
    private List<Doctor> makeDoctors() {
        List<Doctor> studies = new ArrayList<>();
        studies.add(new Doctor(){
            {
            	setName("Prof. Dr. Felix Berger");
            }
        });
        studies.add(new Doctor(){
            {
            	setName("Prof. Dr.  Jens-Uwe Blohmer");
            }
        });
        return studies;
    }

}
