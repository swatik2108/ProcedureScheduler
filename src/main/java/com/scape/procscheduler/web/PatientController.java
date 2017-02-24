package com.scape.procscheduler.web;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.repository.PatientRepository;

/**
 * 
 * @author swatik
 */
@Controller
class PatientController {

    private static final String VIEWS_PATIENT_CREATE_OR_UPDATE_FORM = "patients/createOrUpdatePatientForm";
    private final PatientRepository patients;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    public PatientController(PatientRepository patientService) {
        this.patients = patientService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    @InitBinder("patient")
    public void initPatientBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PatientValidator());
    }

    @RequestMapping(value = "/patients/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Patient patient = new Patient();
        model.put("patient", patient);
        return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/patients/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
        	
        	return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
        } else {
            this.patients.save(patient);
           
            return "redirect:/patients/" + patient.getId();
        }
    }

    @RequestMapping(value = "/patients/{patientId}/edit", method = RequestMethod.GET)
    public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Model model) {
        Patient patient = this.patients.findById(patientId);
        model.addAttribute(patient);
        return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/patients/{patientId}/edit", method = RequestMethod.POST)
    public String processUpdatePatientForm(@Valid Patient patient, BindingResult result, @PathVariable("patientId") int patientId) {
        if (result.hasErrors()) {
            return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
        } else {
        	patient.setId(patientId);
            this.patients.save(patient);
            return "redirect:/patients/{patientId}";
        }
    }

    /**
     * Custom handler for displaying a patient.
     *
     * @param patientId the ID of the patient to display
     * @return a ModelAndView with the model attributes for the view
     */
    @RequestMapping("/patients/{patientId}")
    public ModelAndView showPatient(@PathVariable("patientId") int patientId) {
        ModelAndView mav = new ModelAndView("patients/patientDetails");
        mav.addObject(this.patients.findById(patientId));
        return mav;
    }

}
