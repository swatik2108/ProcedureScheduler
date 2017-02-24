package com.scape.procscheduler.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple JavaBean domain object representing a patient.
 * 
 * @author swatik
 * 
 */
@Entity
@Table(name = "patient")
public class Patient extends BaseEntity {
	@Column(name = "first_name")
    @NotEmpty
    private String firstName;

	@Column(name = "last_name")
	@NotEmpty
	private String lastName;

 	@Column(name = "sex")
    private String sex;

    @Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    
    public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
    public String toString() {
		return this.getFirstName() + " " + this.getLastName();
    }
}
