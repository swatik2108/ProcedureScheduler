package com.scape.procscheduler.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.text.ParseException;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Schedule;
import com.scape.procscheduler.repository.DoctorRepository;
import com.scape.procscheduler.repository.PatientRepository;
import com.scape.procscheduler.repository.RoomRepository;
import com.scape.procscheduler.repository.ScheduleRepository;
import com.scape.procscheduler.repository.StudyRepository;



/**
 * Test class for {@link ScheduleController}
 *
 * @author swatik
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(ScheduleController.class)
@WebMvcTest(value = ScheduleController.class,
includeFilters ={ @ComponentScan.Filter(
                        value = DoctorFormatter.class,
                        type = FilterType.ASSIGNABLE_TYPE),
					@ComponentScan.Filter(
                        value = RoomFormatter.class,
                        type = FilterType.ASSIGNABLE_TYPE),
					@ComponentScan.Filter(
	                        value = StudyFormatter.class,
	                        type = FilterType.ASSIGNABLE_TYPE),
					@ComponentScan.Filter(
	                        value = PatientFormatter.class,
	                        type = FilterType.ASSIGNABLE_TYPE),
					}
				)
public class ScheduleControllerTest implements TestData{

    @Autowired
    private MockMvc mockMvc;

 
    @MockBean
    private  ScheduleRepository schedules;
    
    @MockBean
    private  DoctorRepository doctors;
    
    @MockBean
    private  RoomRepository rooms;
    
    @MockBean
    private  PatientRepository patients;
    
    @MockBean
    private  StudyRepository studies;
    
    private static final String VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM = "schedule/createOrUpdateScheduleForm";
    private Schedule schedule;

    @Before
    public void setup() throws ParseException {
    	schedule = getScheduleObject();   
    	given(this.doctors.findDoctors()).willReturn(Lists.newArrayList(getDoctorObject()));
        given(this.rooms.findRooms()).willReturn(Lists.newArrayList(getRoomObject()));
        given(this.patients.findPatients()).willReturn(Lists.newArrayList(getPatientObject()));
        given(this.studies.findStudy()).willReturn(Lists.newArrayList(getStudyObject()));
        given(this.schedules.findById(TEST_SCHEDULE_ID)).willReturn(schedule);
    }

    @Test
    public void test_WhenFormIsIntialized_CreateFormIsDisplayed() throws Exception {
        mockMvc.perform(get("/schedule/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("schedule"))
            .andExpect(view().name(VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM));
    }

    @Test
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/schedule/new")
            .param("doctor", getDoctorObject().toString())
            .param("room", getRoomObject().toString())
            .param("patient", getPatientObject().toString())
            .param("study",getStudyObject().toString())
            .param("status","In Progress")
            .param("plannedStartTime",FUTURE_PLANED_TIMESTAMP_IN_STRING)
            .param("estimatedEndTime",FUTURE_TIMESTAMP_IN_STRING)
        )
            .andExpect(status().is3xxRedirection());
    }

    @Test
    public void test_WhenPlannedStartTimeIsIsEmpty_FieldErrorIsGenerated() throws Exception {
        mockMvc.perform(post("/schedule/new")
        		 .param("doctor", getDoctorObject().toString())
                 .param("room", getRoomObject().toString())
                 .param("patient", getPatientObject().toString())
                 .param("patient",getStudyObject().toString())
                 .param("status","In Progress")
                 .param("plannedStartTime","")
                 .param("estimatedEndTime",FUTURE_TIMESTAMP_IN_STRING)
        )
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("schedule"))
            .andExpect(model().attributeHasFieldErrors("schedule", "plannedStartTime"))
            .andExpect(view().name(VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM));
    }
    


    @Test
    public void test_WhenUpdatePatientCalled_ExistingDetailsPassed() throws Exception {
        mockMvc.perform(get("/schedules/{scheduleId}/edit", TEST_SCHEDULE_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("schedule"))
            .andExpect(model().attribute("schedule", hasProperty("doctor", is(getScheduleObject().getDoctor()))))
            .andExpect(model().attribute("schedule", hasProperty("room", is(getScheduleObject().getRoom()))))
            .andExpect(model().attribute("schedule", hasProperty("patient", is(getScheduleObject().getPatient()))))
            .andExpect(model().attribute("schedule", hasProperty("study", is(getScheduleObject().getStudy()))))
            .andExpect(model().attribute("schedule", hasProperty("status",  is(getScheduleObject().getStatus()))))
            .andExpect(model().attribute("schedule", hasProperty("plannedStartTime",  is(getScheduleObject().getPlannedStartTime()))))
            .andExpect(model().attribute("schedule", hasProperty("estimatedEndTime",  is(getScheduleObject().getEstimatedEndTime()))))
            .andExpect(view().name(VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM));
    	}
   
    @Test
    public void test_WhenFormSubmittedonEdit_PageIsRedirected() throws Exception {
        mockMvc.perform(post("/schedules/{scheduleId}/edit", TEST_SCHEDULE_ID)
        		.param("doctor", getDoctorObject().toString())
                .param("room", getRoomObject().toString())
                .param("patient", getPatientObject().toString())
                .param("patient",getStudyObject().toString())
                .param("status","In Progress")
                .param("plannedStartTime",FUTURE_PLANED_TIMESTAMP_IN_STRING)
                .param("estimatedEndTime",FUTURE_TIMESTAMP_IN_STRING)        
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/schedules/{scheduleId}"));
    }
    
  

}