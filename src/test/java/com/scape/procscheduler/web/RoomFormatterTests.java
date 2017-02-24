package com.scape.procscheduler.web;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.scape.procschedule.TestData;
import com.scape.procscheduler.model.Room;
import com.scape.procscheduler.repository.RoomRepository;


/**
 * Test class for {@link RoomFormatter}
 *
 * @author swatik
 */
@RunWith(MockitoJUnitRunner.class)
public class RoomFormatterTests implements TestData {

    @Mock
    private RoomRepository room;

    private RoomFormatter roomFormatter;

    @Before
    public void setup() {
        this.roomFormatter = new RoomFormatter(room);
    }

    @Test
    public void testPrint() {
        Room room = new Room();
        room.setName("CC 12");
        String roomName = this.roomFormatter.print(room, Locale.ENGLISH);
        assertEquals("CC 12", roomName);
    }

   @Test
    public void shouldParse() throws ParseException {
        Mockito.when(this.room.findRooms()).thenReturn(makeRooms());
        Room room = roomFormatter.parse("CC 12", Locale.ENGLISH);
        assertEquals("CC 12", room.getName());
    }

    @Test(expected = ParseException.class)
    public void shouldThrowParseException() throws ParseException {
        Mockito.when(this.room.findRooms()).thenReturn(makeRooms());
        roomFormatter.parse("TestFail", Locale.ENGLISH);
    }

    /**
     * Helper method to produce some sample room types just for test purpose
     *
     * @return {@link Collection} of {@link Room}
     */
    private List<Room> makeRooms() {
        List<Room> studies = new ArrayList<>();
        studies.add(new Room(){
            {
            	setName("CC 12");
            }
        });
        studies.add(new Room(){
            {
            	setName("CC 13");
            }
        });
        return studies;
    }

}
