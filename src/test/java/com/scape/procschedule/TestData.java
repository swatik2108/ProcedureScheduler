package com.scape.procschedule;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.scape.procscheduler.model.Doctor;
import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.model.Room;
import com.scape.procscheduler.model.Schedule;
import com.scape.procscheduler.model.Study;

public interface TestData {

	public static final int TEST_PATIENT_ID = 1;
	public static final int TEST_SCHEDULE_ID = 1;
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter_dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	
	
    String PAST_DATE_IN_STRING = "2016-02-23";
    String FUTURE_DATE_IN_STRING = "2018-02-23";
    
    String PAST_TIMESTAMP_IN_STRING = "2006-02-23'T'08:30";
    String FUTURE_PLANED_TIMESTAMP_IN_STRING = "2017-03-23T08:00";
    String FUTURE_TIMESTAMP_IN_STRING = "2017-03-23T08:30";
    
    public static final Patient mary = new Patient();
    public static final Schedule schedule = new Schedule();
    public static final Doctor doctor = new Doctor();
    public static final Room room = new Room();
    public static final Study study = new Study();
    
	 default Patient getPatientObject() throws ParseException {	 	
        mary.setId(TEST_PATIENT_ID);
        mary.setFirstName("mary");
        mary.setLastName("Ann");
        mary.setSex("Female");
        mary.setDateOfBirth(formatter.parse(PAST_DATE_IN_STRING));
        return mary;
	       
	 }
	 
	 default Schedule getScheduleObject() throws ParseException {	 	
		 	schedule.setId(TEST_SCHEDULE_ID);
		 	schedule.setDoctor(getDoctorObject());
		 	schedule.setRoom(getRoomObject());
		 	schedule.setStatus("In Progress");
		 	schedule.setStudy(getStudyObject());
		 	schedule.setPatient(getPatientObject());
		 	schedule.setPlannedStartTime(formatter_dateTime.parse(FUTURE_PLANED_TIMESTAMP_IN_STRING));
		 	schedule.setEstimatedEndTime(formatter_dateTime.parse(FUTURE_TIMESTAMP_IN_STRING));
	        return schedule;		       
	}
	 
	 default Doctor getDoctorObject() throws ParseException {	 	
		 	doctor.setId(1);
		 	doctor.setName("Felix Berger");
	        return doctor;		       
	}
	 
	 default Room getRoomObject() throws ParseException {	 	
		 	room.setId(1);
		 	room.setName("CC 12");
	        return room;		       
	}
	 
	 default Study getStudyObject() throws ParseException {	 	
		 	study.setId(1);
		 	study.setDescription("Electrocardiography");
	        return study;		       
	}

	
}
