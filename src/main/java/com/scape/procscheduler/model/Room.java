package com.scape.procscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing a Room.
 * 
 * @author swatik
 */
@Entity
@Table(name = "room")
public class Room extends BaseEntity {
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
