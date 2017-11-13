# HuWanqing
###### \java\seedu\address\logic\commands\JoinCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvent;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

```
###### \java\seedu\address\logic\commands\JoinCommandTest.java
``` java
/**
 * contains integration test for join command
 */
public class JoinCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void testJoinInvalidIndexFail() {
        final Index validIndex = INDEX_FIRST_EVENT;
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        JoinCommand invalidPersonIndexCommand = prepareCommand(invalidLargeIndex, validIndex, model);

        invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        JoinCommand invalidEventIndexCommand = prepareCommand(validIndex, invalidLargeIndex, model);

        String expectedMessageForPerson = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        String expectedMessageForEvent = Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        assertCommandFailure(invalidEventIndexCommand, model, expectedMessageForEvent);
        assertCommandFailure(invalidPersonIndexCommand, model, expectedMessageForPerson);
    }

    @Test
    public void testHaveParticipatedFail() {
        String expectedMessage = JoinCommand.MESSAGE_DUPLICATE_PERSON;
        final Index validIndex = INDEX_FIRST_EVENT;
        final Index invalidIndex = Index.fromOneBased(2);
        Model actualModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        JoinCommand haveParticipatedCommand = prepareCommand(invalidIndex, validIndex, actualModel);

        assertCommandFailure(haveParticipatedCommand, actualModel, expectedMessage);

    }

    @Test
    public void testSuccess() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index eventIndex = INDEX_THIRD_PERSON;
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased());
        String expectedMessage = String.format(JoinCommand.MESSAGE_JOIN_SUCCESS, person.getName(),
                event.getEventName());
        JoinCommand command = prepareCommand(personIndex, eventIndex, model);
        joinEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_THIRD_EVENT);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code DisjoinCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private JoinCommand prepareCommand(Index personIndex, Index eventIndex, Model model) {
        JoinCommand command = new JoinCommand(personIndex, eventIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
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
