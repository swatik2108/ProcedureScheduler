package com.scape.procscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import org.springframework.transaction.annotation.Transactional;

import com.scape.procscheduler.model.Room;

/**
 * Repository class for <code>Room</code> domain objects 
 * 
 * @author swatik
 * 
 */
public interface RoomRepository extends Repository<Room, Integer> {

    /**
     * Retrieve Room from the data store by room name
     * 
     * @param room.name Value to search for
     * @return a Collection of matching rooms (or an empty Collection if none found)
     * 
     */
    @Query("SELECT room FROM Room room ORDER BY room.name")
    @Transactional(readOnly = true)
    List<Room> findRooms();

  
}
