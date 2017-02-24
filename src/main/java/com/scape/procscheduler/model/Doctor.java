package com.scape.procscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Simple JavaBean domain object representing an doctor.
 * 
 * @author swatik
 * 
 */

@Entity
@Table(name = "doctor")
public class Doctor extends BaseEntity {
	@Column(name = "name")
    @NotEmpty
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String toString() {
		return this.getName();
    }
}
