package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;


public class UniqueEventListTest {
    private UniqueEventList eventList = new UniqueEventList();

    @Test
    public void createUniqueEventListSuccess() throws IllegalValueException {
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.BOB);
        LocalDateTime now = LocalDateTime.now();

        Event e1 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e2 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e3 = new Event(new MemberList(list), new EventName("2"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e4 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now.plus(Duration.ofMinutes(1)), Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)));
        Event e5 = new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(1)),
                new EventDuration(Duration.ofMinutes(1)));

        eventList.add(e1);
        eventList.add(e3);
        eventList.add(e4);
        eventList.add(e5);

        //Test contains
        assertTrue(eventList.contains(e1));
        assertTrue(eventList.contains(new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)))));

        //Test equals
        assertTrue(e1.equals(e2));
        assertTrue(e1.equals(new Event(new MemberList(list), new EventName("1"),
                new EventTime(now, Duration.ofMinutes(10)),
                new EventDuration(Duration.ofMinutes(10)))));
        assertFalse(e1.equals(e3));
    }

    @Test
    public void testEventClashes() throws IllegalValueException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minus(Duration.ofHours(1));
        LocalDateTime future = now.plus(Duration.ofHours(1));

        Event e1 = new Event(new MemberList(), new EventName("1"),
                new EventTime(now, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)));
        eventList.add(e1);

        //Test for overlaps
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(past, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)))));
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(future, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)))));

        //Test for in between
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(future, Duration.ofMinutes(30)),
                new EventDuration(Duration.ofMinutes(30)))));
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(past, Duration.ofHours(5)),
                new EventDuration(Duration.ofHours(5)))));

        //Test for exact date-time match
        assertTrue(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now, Duration.ofHours(2)),
                new EventDuration(Duration.ofHours(2)))));

        //Test for adjacent events
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.minus(Duration.ofHours(2)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.plus(Duration.ofHours(2)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));

        //Test for adjacent events with time buffer
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.minus(Duration.ofHours(5)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
        assertFalse(eventList.hasClashes(new Event(new MemberList(), new EventName("1"),
                new EventTime(now.plus(Duration.ofHours(5)), Duration.ofHours(1)),
                new EventDuration(Duration.ofHours(1)))));
    }
}
