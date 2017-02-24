package com.scape.procscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scape.procscheduler.model.Study;

/**
 * Repository class for <code>Study</code> domain objects
 * 
 * @author swatik
 * 
 */
public interface StudyRepository extends Repository<Study, Integer> {

    /**
     * Retrieve 'Study' from the data store by description
     * 
     * @param description Value to search for
     * @return a Collection of matching Study objects (or an empty Collection if none found)
     */
	
    @Query("SELECT study FROM Study study ORDER BY study.description")
    @Transactional(readOnly = true)
    List<Study> findStudy();

}
