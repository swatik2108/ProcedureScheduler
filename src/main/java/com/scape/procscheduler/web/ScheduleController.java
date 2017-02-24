package com.scape.procscheduler.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.scape.procscheduler.model.Doctor;
import com.scape.procscheduler.model.Patient;
import com.scape.procscheduler.model.Room;
import com.scape.procscheduler.model.Schedule;
import com.scape.procscheduler.model.Study;
import com.scape.procscheduler.repository.DoctorRepository;
import com.scape.procscheduler.repository.PatientRepository;
import com.scape.procscheduler.repository.RoomRepository;
import com.scape.procscheduler.repository.ScheduleRepository;
import com.scape.procscheduler.repository.StudyRepository;

/**
 * @author swatik
 */

@Controller
class ScheduleController {

    private static final String VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM = "schedule/createOrUpdateScheduleForm";
    private final ScheduleRepository schedules;
    private final DoctorRepository doctors;
    private final RoomRepository rooms;
    private final PatientRepository patients;
    private final StudyRepository studies;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    
    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository,RoomRepository roomRepository,
    		PatientRepository patientRepository, StudyRepository studyRepository ) {
        this.schedules = scheduleRepository;
        this.doctors = doctorRepository;
        this.rooms = roomRepository;
        this.patients = patientRepository;
        this.studies = studyRepository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");      
    }
    
    @InitBinder("schedule")
    public void initScheduleBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new ScheduleValidator());
    }
 
    @ModelAttribute("rooms")
    public Collection<Room> populateRooms() {
        return this.rooms.findRooms();
    }
    
    @ModelAttribute("doctors")
    public Collection<Doctor> populateDoctors() {
        return this.doctors.findDoctors();
    }
    
    @ModelAttribute("patients")
    public Collection<Patient> populatePatients() {
        return this.patients.findPatients();
    }
    
    @ModelAttribute("studies")
    public Collection<Study> populateStudies() {
        return this.studies.findStudy();
    }
    
    @ModelAttribute("status")
    public Collection<String> populateStatus() { 
		List<String> status = new ArrayList<String>();
		status.add("Planned");
		status.add("In Progress");
		status.add("Finished");
		return status; 	
    }
    
    @RequestMapping(value = "/schedule/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Schedule schedule = new Schedule();
        model.put("schedule", schedule);
        return VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/schedule/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Schedule schedule, BindingResult result,HttpServletRequest request) {
    	logger.debug(request.getParameterMap().toString());
    	System.out.println("Printing the param map"+request.getParameter("doctor"));
    	if (result.hasErrors()) {
            return VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM;
        } else {
            this.schedules.save(schedule);
            return "redirect:/schedules/" + schedule.getId();
        }
    }

  
    @RequestMapping(value = "/schedules/{scheduleId}/edit", method = RequestMethod.GET)
    public String initUpdateScheduleForm(@PathVariable("scheduleId") int scheduleId, Model model) {
        Schedule schedule = this.schedules.findById(scheduleId);
        model.addAttribute(schedule);
        return VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = { "/schedules.html" })
    public String showSchedules(Map<String, Object> model) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        schedules = this.schedules.findSchedules();       
        model.put("schedules", schedules);
        return "schedule/scheduleList";
    }
    
    @RequestMapping(value = "/schedules.html", method = RequestMethod.POST)
    public String processUpdateFirstScheduleForm(@Valid Schedule schedule, BindingResult result, @PathVariable("scheduleId") int scheduleId) {
        if (result.hasErrors()) {
            return VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM;
        } else {
        	schedule.setId(scheduleId);
            this.schedules.save(schedule);
            return "redirect:/schedules/{scheduleId}";
        }
    }

    /**
     * Custom handler for displaying an schedule.
     *
     * @param scheduleId the ID of the schedule to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/schedules/{scheduleId}")
    public ModelAndView showSchedule(@PathVariable("scheduleId") int scheduleId) {
        ModelAndView mav = new ModelAndView("schedule/scheduleDetails");
        mav.addObject(this.schedules.findById(scheduleId));
        return mav;
    }
    
    @RequestMapping(value = "/schedules/{scheduleId}/edit", method = RequestMethod.POST)
    public String processUpdateScheduleForm(@Valid Schedule schedule, BindingResult result, @PathVariable("scheduleId") int scheduleId) {
        if (result.hasErrors()) {
            return VIEWS_SCHEDULE_CREATE_OR_UPDATE_FORM;
        } else {
        	schedule.setId(scheduleId);
            this.schedules.save(schedule);
            return "redirect:/schedules/{scheduleId}";
        }
    }

}
