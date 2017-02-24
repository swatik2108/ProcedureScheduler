package com.scape.procscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import org.springframework.transaction.annotation.Transactional;

import com.scape.procscheduler.model.Doctor;

/**
 * Repository class for <code>doctor</code> domain objects.
 * 
 * @author swatik
 * 
 */
public interface DoctorRepository extends Repository<Doctor, Integer> {

    /**
     * Retrieve Doctor from the data store by name, 
     * @return a Collection of Doctors (or an empty Collection if none found)
     */
    @Query("SELECT doctor FROM Doctor doctor ORDER BY doctor.name")
    @Transactional(readOnly = true)
    List<Doctor> findDoctors();

}
