package seedu.room.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.EventBook;
import seedu.room.model.Model;
import seedu.room.model.ReadOnlyEventBook;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.ResidentBook;
import seedu.room.model.event.Event;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;
import seedu.room.model.event.exceptions.EventNotFoundException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.NoneHighlightedException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.person.exceptions.TagNotFoundException;
import seedu.room.model.tag.Tag;
import seedu.room.testutil.EventBuilder;

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

        String successMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent);
        assertEquals(successMessage, commandResult.feedbackToUser);
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
        Event orientation = new EventBuilder().withTitle("Orientation").build();
        Event quidditch = new EventBuilder().withTitle("Quidditch").build();
        AddEventCommand addOrientationCommand = new AddEventCommand(orientation);
        AddEventCommand addQuidditchCommand = new AddEventCommand(quidditch);

        // same object -> returns true
        assertTrue(addOrientationCommand.equals(addOrientationCommand));

        // same values -> returns true
        AddEventCommand addOrientationCommandCopy = new AddEventCommand(orientation);
        assertTrue(addOrientationCommand.equals(addOrientationCommandCopy));

        // different types -> returns false
        assertFalse(addOrientationCommand.equals(1));

        // null -> returns false
        assertFalse(addOrientationCommand.equals(null));

        // different event -> returns false
        assertFalse(addOrientationCommand.equals(addQuidditchCommand));
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
        public void resetData(ReadOnlyResidentBook newData, ReadOnlyEventBook newEventData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyResidentBook getResidentBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        //@@author Haozhe321
        @Override
        public void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
            fail("this method should not be called.");
        }
        //@@author

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

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
        public void updateFilteredPersonListPicture(Predicate<ReadOnlyPerson> predicate, Person p) {
            fail("This method should not be called.");
        }

        @Override
        public void removeTag(Tag tag) {
            fail("This method should not be called.");
        }

        //@@author sushinoya
        @Override
        public void sortBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }

        //@@author
        @Override
        public void updateHighlightStatus(String highlightTag) throws TagNotFoundException {

        }

        @Override
        public void resetHighlightStatus() throws NoneHighlightedException {
            fail("This method should not be called.");
        }

        //@@author sushinoya
        public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addEvent(ReadOnlyEvent person) throws DuplicateEventException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent) throws DuplicateEventException,
                EventNotFoundException {
            fail("This method should not be called.");
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

        @Override
        public void sortEventsBy(String sortCriteria) throws AlreadySortedException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateEventException extends ModelStub {
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            throw new DuplicateEventException();
        }

        @Override
        public ReadOnlyResidentBook getResidentBook() {
            return new ResidentBook();
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
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
        public ReadOnlyResidentBook getResidentBook() {
            return new ResidentBook();
        }

        @Override
        public ReadOnlyEventBook getEventBook() {
            return new EventBook();
        }
    }

}
