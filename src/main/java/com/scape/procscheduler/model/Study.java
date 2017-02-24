package com.scape.procscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing a study.
 *
 * @author swatik
 * 
 */
@Entity
@Table(name = "study")
public class Study extends BaseEntity {
    @Column(name = "description")
    @NotEmpty
    private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
		return this.getDescription();
    }
}
