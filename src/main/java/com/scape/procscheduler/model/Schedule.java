package com.scape.procscheduler.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple JavaBean domain object representing a procedure schedule.
 *
 * @author swatik
 * 
 */
@Entity
@Table(name = "procedureSchedule")
public class Schedule extends BaseEntity {
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="doctor_id", referencedColumnName="id")
	private Doctor doctor;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="room_id", referencedColumnName="id")
	private Room room;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="patient_id", referencedColumnName="id")
	private Patient patient;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="study_id", referencedColumnName="id")
	private Study study;
	
	@Column(name = "status")
    @NotEmpty
	private String status;
	
	@Column(name = "planned_start_time")
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private Date plannedStartTime;
    
	@Column(name = "estimated_end_time")
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date estimatedEndTime;
    

    public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPlannedStartTime() {
		return plannedStartTime;
	}

	public void setPlannedStartTime(Date plannedStartTime) {
		this.plannedStartTime = plannedStartTime;
	}

	public Date getEstimatedEndTime() {
		return estimatedEndTime;
	}

	public void setEstimatedEndTime(Date estimatedEndTime) {
		this.estimatedEndTime = estimatedEndTime;
	}

	@Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("new", this.isNew())
            .toString();
    }
}
