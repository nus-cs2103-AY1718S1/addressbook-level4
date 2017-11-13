# HuWanqing
###### \java\seedu\address\model\event\EventNameTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventNameTest {
    @Test
    public void isValidEventName() {
        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("meeting")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("meeting 1")); // alphanumeric characters
        assertTrue(EventName.isValidName("First Meeting")); // with capital letters
        assertTrue(EventName.isValidName("A very important meeting tomorrow")); // long names
    }
}
```
###### \java\seedu\address\model\event\EventTimeTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventTimeTest {
    @Test
    public void isValidEventTime() {
        // invalid event time
        assertFalse(EventTime.isValidEventTime("32/10/2017")); // there is no 32th in a month
        assertFalse(EventTime.isValidEventTime("10/13/1992")); // there isn't a 13th month
        assertFalse(EventTime.isValidEventTime("0/1/1994")); // day cannot be 0
        assertFalse(EventTime.isValidEventTime("1/0/1995")); // month cannot be 0
        assertFalse(EventTime.isValidEventTime("31/4/1990")); // april does not have a 31st day
        assertFalse(EventTime.isValidEventTime("30/02/1996")); // february does not have a 30th day
        assertFalse(EventTime.isValidEventTime("29/02/1997")); // 1997 is not a leap year
        assertFalse(EventTime.isValidEventTime("1993/11/21")); // does not follow 'DD/MM/YYYY' format
        assertFalse(EventTime.isValidEventTime("09/1994/30")); // does not follow 'DD/MM/YYYY' format
        assertFalse(EventTime.isValidEventTime("04.04.2010")); // does not user '/' as splitter

        // valid event time
        assertTrue(EventTime.isValidEventTime("01/01/1990"));
        assertTrue(EventTime.isValidEventTime("13/05/1991"));
        assertTrue(EventTime.isValidEventTime("24/06/1992"));
        assertTrue(EventTime.isValidEventTime("17/02/1993"));

```
###### \java\seedu\address\model\EventListTest.java
``` java

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;






public class EventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EventList eventList = new EventList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eventList.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        eventList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyEventList_replacesData() {
        EventList newData = getTypicalEventList();
        eventList.resetData(newData);
        assertEquals(newData, eventList);
    }

    @Test
    public void resetData_withDuplicateEvents_throwsAssertionError() {
        // Repeat FIRST twice
        List<Event> newEvents = Arrays.asList(new Event(FIRST), new Event(FIRST));
        EventListStub newData = new EventListTest.EventListStub(newEvents);

        thrown.expect(AssertionError.class);
        eventList.resetData(newData);
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        eventList.getEventList().remove(0);
    }

    /**
     * A stub ReadOnlyEventList whose event lists can violate interface constraints.
     */
    private static class EventListStub implements ReadOnlyEventList {
        private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();

        EventListStub(Collection<? extends ReadOnlyEvent> events) {
            this.events.setAll(events);
        }

        @Override
        public ObservableList<ReadOnlyEvent> getEventList() {
            return events;
        }

    }
}

```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        EventList eventList = new EventListBuilder().withEvent(FIRST).withEvent(SECOND).build();
        AddressBook differentAddressBook = new AddressBook();
        EventList differentEventList = new EventList();
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, eventList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventList, userPrefs)));

```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        // different EventList -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentEventList, userPrefs)));

```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        // different filtered event list -> returns false
        String[] eventKeywords = FIRST.getEventName().fullEventName.split("\\s+");
        modelManager.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(eventKeywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventList, userPrefs)));

```
###### \java\seedu\address\model\UniqueEventListTest.java
``` java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.UniqueEventList;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\EventListBuilder.java
``` java
import seedu.address.model.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * A utility class help to build an event list
 */
public class EventListBuilder {
    private EventList eventList;

    public EventListBuilder() {
        eventList = new EventList();
    }

    public EventListBuilder(EventList eventList) {
        this.eventList = eventList;
    }

    /**
     * Adds a new {@code Event} to the {@code EventList} that we are building.
     */
    public EventListBuilder withEvent(ReadOnlyEvent event) {
        try {
            eventList.addEvent(event);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }

    public EventList build() {
        return eventList;
    }
}
```
