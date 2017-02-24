package com.scape.procscheduler.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Doctor;
import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.model.Room;
import com.scape.procscheduler.model.Schedule;

/**
 * Integration test of the Service and the Repository layer.
 * 
 * @author swatik
 */

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ProcSchedServiceTests implements TestData{

    @Autowired
    protected DoctorRepository doctors;

    @Autowired
    protected PatientRepository patients;

    @Autowired
    protected RoomRepository rooms;

    @Autowired
    protected ScheduleRepository schedule;
    
    @Autowired
    protected StudyRepository study;

    @Test
    public void test_WhenFindDoctorsIsInvoked_AllDoctorsAreReturned() {
        Collection<Doctor> doctors = this.doctors.findDoctors();
        assertThat(doctors.size()).isEqualTo(5);
    }

    @Test
    public void test_WhenFindPatientsIsInvoked_AllPatientsAreReturned() {
        Collection<Patient> patients = this.patients.findPatients();
        assertThat(patients.size()).isEqualTo(1);
    }
    

    @Test
    public void test_WhenFindOnePatientsIsInvoked_PatientDetailsAreReturned() {
    	Patient patient = this.patients.findById(1);
        assertThat(patient.getLastName()).matches("Ann");
        assertThat(patient.getFirstName()).matches("Mary");
        assertThat(patient.getSex()).matches("Female");
    }
    
    @Test
    @Transactional
    public void test_WhenPatientIsAdded_ItIsSaved() throws ParseException {
        Collection<Patient> patients = this.patients.findPatients();
        int found = patients.size();
        Patient john = new Patient();
        john.setFirstName("John");
        john.setLastName("Long");
        john.setSex("Male");
        john.setDateOfBirth(formatter.parse(PAST_DATE_IN_STRING));
        this.patients.save(john);
        assertThat(john.getId().longValue()).isNotEqualTo(0);
        patients = this.patients.findPatients();
        assertThat(patients.size()).isEqualTo(found + 1);
    }
    
    @Test
    public void test_WhenFindRoomsIsInvoked_AllRoomsAreReturned() {
        Collection<Room> rooms = this.rooms.findRooms();
        assertThat(rooms.size()).isEqualTo(8);
    }

    
    @Test
    @Transactional
    public void test_WhenScheduleIsAdded_ItIsSaved() throws ParseException {
        Collection<Schedule> schedules = this.schedule.findSchedules();
        int found = schedules.size();
        Schedule schedule = getScheduleObject();
        
        this.schedule.save(schedule);
        assertThat(schedule.getId().longValue()).isNotEqualTo(0);
        schedules = this.schedule.findSchedules();
        assertThat(schedules.size()).isEqualTo(found + 1);
    }

}
