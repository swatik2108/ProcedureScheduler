/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.scape.procscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import com.scape.procscheduler.model.Schedule;

/**
 * Repository class for <code>Schedule</code> domain objects 
 * 
 * @author swatik
 */
public interface ScheduleRepository extends Repository<Schedule, Integer> {

    
    /**
     * Retrieve a schedule from the data store by id.
     * @param id the id to search for
     * @return the schedule if found
     */
    @Query("SELECT schedule FROM Schedule schedule WHERE schedule.id =:id")
    @Transactional(readOnly = true)
    Schedule findById(@Param("id") Integer id);

    
    @Query("SELECT schedule FROM Schedule schedule")
    @Transactional(readOnly = true)
    List<Schedule> findSchedules();

    /**
     * Save a schedule to the data store, either inserting or updating it.
     * @param schedule to save
     */
    void save(Schedule schedule);

}
