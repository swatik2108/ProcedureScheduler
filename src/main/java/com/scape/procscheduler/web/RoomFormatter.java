package com.scape.procscheduler.web;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.scape.procscheduler.model.Room;
import com.scape.procscheduler.repository.RoomRepository;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'Room'. 
 *
 * @author swatik
 */
@Component
public class RoomFormatter implements Formatter<Room> {

    private final RoomRepository room;


    @Autowired
    public RoomFormatter(RoomRepository room) {
        this.room = room;
    }

    @Override
    public String print(Room room, Locale locale) {
        return room.getName();
    }

    @Override
    public Room parse(String text, Locale locale) throws ParseException {
        Collection<Room> findRooms = this.room.findRooms();
        for (Room room : findRooms) {
            if (room.getName().equals(text)) {
                return room;
            }
        }
        throw new ParseException("room not found: " + text, 0);
    }

}
