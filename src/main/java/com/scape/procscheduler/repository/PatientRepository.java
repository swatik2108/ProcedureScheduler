package com.scape.procscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import com.scape.procscheduler.model.Patient;

/**
 * Repository class for Patient domain objects
 * 
 * @author swatik
 */
public interface PatientRepository extends Repository<Patient, Integer> {

  
    /**
     * Retrieve a patient from the data store by id.
     * @param id the id to search for
     * @return the patient if found
     */
    @Query("SELECT patient FROM Patient patient WHERE patient.id =:id")
    @Transactional(readOnly = true)
    Patient findById(@Param("id") Integer id);
    
    
    @Query("SELECT patient FROM Patient patient ORDER BY patient.lastName")
    @Transactional(readOnly = true)
    List<Patient> findPatients();

    /**
     * Save patient to the data store, either inserting or updating it.
     * @param patient to save
     */
    void save(Patient patient);


}
