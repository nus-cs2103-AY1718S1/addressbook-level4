package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EventBuilder;

//@@author royceljh
public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = getAddEventCommandForEvent(validEvent, modelStub).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEventException();
        Event validEvent = new EventBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        getAddEventCommandForEvent(validEvent, modelStub).execute();
    }

    @Test
    public void equals() {
        Event meeting = new EventBuilder().withHeader("Meeting").build();
        Event birthday = new EventBuilder().withHeader("Birthday").build();
        AddEventCommand addMeetingCommand = new AddEventCommand(meeting);
        AddEventCommand addBirthdayCommand = new AddEventCommand(birthday);

        // same object -> returns true
        assertTrue(addMeetingCommand.equals(addMeetingCommand));

        // same values -> returns true
        AddEventCommand addMeetingCommandCopy = new AddEventCommand(meeting);
        assertTrue(addMeetingCommand.equals(addMeetingCommandCopy));

        // different types -> returns false
        assertFalse(addMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(addMeetingCommand.equals(null));

        // different event -> returns false
        assertFalse(addMeetingCommand.equals(addBirthdayCommand));
    }

    /**
     * Generates a new AddEventCommand with the details of the given event.
     */
    private AddEventCommand getAddEventCommandForEvent(Event event, Model model) {
        AddEventCommand command = new AddEventCommand(event);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        //@@author sidhmads
        @Override
        public void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
                throws PersonNotFoundException, IllegalValueException {
            fail("This method should not be called.");
        }
        //@@author

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
                throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void sortByViewCount() {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyEvent> getFilteredEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
            fail("This method should not be called.");
        }

        //@@author sidhmads
        @Override
        public String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return null;
        }
        //@@author

        //@@author sidhmads
        @Override
        public void findLocation(List<ReadOnlyPerson> person) {
            fail("This method should not be called.");
        }
        //@@author

        //@@author sidhmads
        @Override
        public void sortPersons() {
            fail("This method should not be called.");
        }
        //@@author
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add an event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            eventsAdded.add(new Event(event));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
