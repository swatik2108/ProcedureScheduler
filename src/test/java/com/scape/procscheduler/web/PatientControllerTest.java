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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.repository.PatientRepository;



/**
 * Test class for {@link PatientController}
 *
 * @author swatik
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest implements TestData{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRepository patients;

    private Patient mary;

    @Before
    public void setup() throws ParseException {
    		mary = getPatientObject();    
        given(this.patients.findById(TEST_PATIENT_ID)).willReturn(mary);
    }

    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/patients/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("patient"))
            .andExpect(view().name("patients/createOrUpdatePatientForm"));
    }

    @Test
    public void test_WhenPostMethodIsCalled_RequestIsRedirected() throws Exception {
        mockMvc.perform(post("/patients/new")
            .param("firstName", "mary")
            .param("lastName", "Ann")
            .param("sex", "Female")
            .param("dateOfBirth","2016-02-23")
           
        )
            .andExpect(status().is3xxRedirection());
      		
    }

    @Test
    public void test_WhenMandatoryFieldOmitted_FieldErrorIsGenerated() throws Exception {
        mockMvc.perform(post("/patients/new")
                .param("lastName", "Ann")
                .param("sex", "Female")
        )
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("patient"))
            .andExpect(model().attributeHasFieldErrors("patient", "firstName"))
            .andExpect(view().name("patients/createOrUpdatePatientForm"));
    }
    
    @Test
    public void test_WhenOptionalFieldOmmited_NoFieldErrorIsGenerated() throws Exception {
        mockMvc.perform(post("/patients/new")
        			.param("firstName", "Mary")
                .param("lastName", "Ann")
                .param("sex", "Female")
        )
            .andExpect(status().is3xxRedirection());        
    }

    @Test
    public void test_WhenUpdatePatientCalled_ExistingDetailsPassed() throws Exception {
        mockMvc.perform(get("/patients/{patientId}/edit", TEST_PATIENT_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("patient"))
            .andExpect(model().attribute("patient", hasProperty("firstName", is(mary.getFirstName()))))
            .andExpect(model().attribute("patient", hasProperty("lastName", is(mary.getLastName()))))
            .andExpect(model().attribute("patient", hasProperty("sex", is(mary.getSex()))))
            .andExpect(model().attribute("patient", hasProperty("dateOfBirth",  is(mary.getDateOfBirth()))))
            .andExpect(view().name("patients/createOrUpdatePatientForm"));
    		}
   
    @Test
    public void test_WhenFormSubmittedonEdit_PageIsRedirected() throws Exception {
        mockMvc.perform(post("/patients/{patientId}/edit", TEST_PATIENT_ID)
            .param("firstName", mary.getFirstName())
            .param("lastName", mary.getLastName())
            .param("sex", mary.getSex())
            .param("dateOfBirth", PAST_DATE_IN_STRING)           
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/patients/{patientId}"));
    		}
    

	@Test
	public void test_WhenFutureBirthDateIsGiven_FormHasError() throws Exception {
    mockMvc.perform(post("/patients/{patientId}/edit", TEST_PATIENT_ID)
        .param("firstName", mary.getFirstName())
        .param("lastName", mary.getLastName())
        .param("sex", mary.getSex())
        .param("dateOfBirth", FUTURE_DATE_IN_STRING)           
    )
    		.andExpect(status().isOk())
    		.andExpect(model().attributeHasErrors("patient"));
	}


}