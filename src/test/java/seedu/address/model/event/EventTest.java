package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;


//@@author eldriclim
public class EventTest {

    private Event e1;
    private Event e2;
    private Event e3;
    private Event e4;

    private EventName eventNameTest;
    private EventTime eventTimeTest;
    private EventDuration eventDurationTest;
    private MemberList eventMemberListTest;

    @Test
    public void testEventCreation() throws IllegalValueException {
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.CARL);

        e1 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));
        e2 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        //Different time from e1
        e3 = new Event(new MemberList(list), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        //Different name from e1
        e4 = new Event(new MemberList(list), new EventName("Event name different"),
                new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30), Duration.ofMinutes(90)),
                new EventDuration(Duration.ofMinutes(90)));

        eventNameTest = new EventName("Event name");
        eventTimeTest = new EventTime(LocalDateTime.of(2017, 2, 7, 8, 0, 30),
                Duration.ofMinutes(90));
        eventDurationTest = new EventDuration(Duration.ofMinutes(90));
        eventMemberListTest = new MemberList(list);

        testEventEqual();
        testOutput();
        testGetter();
        testSetter();
        testUpdateStatus();
    }

    private void testUpdateStatus() {
        e1.updateEventStatusSelection(e1.getEventTime().getStart().toLocalDate());
        assertEquals(e1.getEventStatusStyle(), "-fx-background-color: #b91372");
    }

    @Test
    public void overloadedConstructorTest() throws IllegalValueException {
        Event e1 = new Event(new MemberList(), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                new EventDuration(Duration.ofMinutes(5)));
        Event e2 = new Event(e1);

        assertEquals(e1, e2);
    }

    /**
     * Test equals function of Event
     */
    public void testEventEqual() {
        assertTrue(e1.equals(e2));
        assertFalse(e1.equals(e3));
        assertFalse(e1.equals(e4));
    }

    /**
     * Test hashcode and toString of Event
     *
     * @throws IllegalValueException
     */
    public void testOutput() throws IllegalValueException {

        //Test hashcode
        assertEquals(Objects.hash(eventNameTest, eventTimeTest, eventDurationTest),
                e1.hashCode());

        //Test toString
        assertEquals("Name: Event name Time: 2017-02-07 08:00 Duration: 1hr30min\n"
                + "Members: Alice Pauline, Carl Kurz", e1.toString());
    }

    /**
     * Test Property getter of Event
     */
    public void testGetter() {
        assertEquals("ObjectProperty [value: Alice Pauline, Carl Kurz]",
                e1.eventMemberListProperty().toString());
        assertEquals("ObjectProperty [value: Event name]",
                e1.eventNameProperty().toString());
        assertEquals("ObjectProperty [value: 2017-02-07 08:00]",
                e1.eventTimeProperty().toString());
        assertEquals("ObjectProperty [value: 1hr30min]",
                e1.eventDurationProperty().toString());
        assertEquals("ObjectProperty [value: Past]",
                e1.eventStatusProperty().toString());
        assertEquals("ObjectProperty [value: -fx-background-color: #a31621]",
                e1.eventStatusStyleProperty().toString());
    }

    /**
     * Test Setter of Event
     */
    private void testSetter() throws IllegalValueException {

        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.GEORGE);
        list.add(TypicalPersons.HOON);
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        Event actual = e4;
        Event expected = new Event(new MemberList(list), new EventName("For testing setter"),
                new EventTime(futureDate, Duration.ofMinutes(60)),
                new EventDuration(Duration.ofMinutes(60)));

        //Before setting
        assertFalse(actual.equals(expected));

        actual.setEventName(new EventName("For testing setter"));
        actual.setEventTime(new EventTime(futureDate, Duration.ofMinutes(60)));
        actual.setEventDuration(new EventDuration(Duration.ofMinutes(60)));
        actual.setMemberList(new MemberList(list));


        //After setting
        assertTrue(actual.equals(expected));

    }
}
