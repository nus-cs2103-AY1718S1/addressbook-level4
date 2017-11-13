package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TypicalPersons;

//@@author eldriclim
public class ScheduleAddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testScheduleAddCommand() throws Exception {
        EventName eventName = new EventName("Event name");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));

        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(0));

        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();

        ScheduleAddCommand command;
        command = getScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes, modelStub);

        CommandResult commandResult = command.execute();

        ArrayList<ReadOnlyPerson> expectedMember = new ArrayList<>();
        expectedMember.add(TypicalPersons.ALICE);
        Event event = new Event(new MemberList(expectedMember), eventName, eventTime, eventDuration);

        assertEquals(commandResult.feedbackToUser,
                String.format(ScheduleAddCommand.MESSAGE_SUCCESS, event.toString()));

    }

    @Test
    public void invalidIndexTest() throws Exception {

        thrown.expectMessage(ScheduleAddCommand.ERROR_INVALID_INDEX);

        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();

        EventName eventName = new EventName("Event name");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));

        Set<Index> uniqueMemberIndexes = new HashSet<>();
        uniqueMemberIndexes.add(new Index(modelStub.getFilteredPersonList().size()));


        ScheduleAddCommand command;
        command = getScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes, modelStub);

        command.execute();
    }

    @Test
    public void testScheduleRemoveEquals() throws Exception {

        EventName eventName = new EventName("Event name");
        EventName eventName1 = new EventName("Event name1");
        EventTime eventTime = new EventTime(LocalDateTime.now(), Duration.ofMinutes(5));
        EventTime eventTime1 = new EventTime(LocalDateTime.now(), Duration.ofMinutes(2));
        EventDuration eventDuration = new EventDuration(Duration.ofMinutes(5));
        EventDuration eventDuration1 = new EventDuration(Duration.ofMinutes(2));
        Set<Index> uniqueMemberIndexes = new HashSet<>(Arrays.asList(new Index(0)));
        Set<Index> uniqueMemberIndexes1 = new HashSet<>(Arrays.asList(new Index(1)));


        ScheduleAddCommand s1 = new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes);
        ScheduleAddCommand s2Same = new ScheduleAddCommand(eventName, eventTime, eventDuration, uniqueMemberIndexes);
        ScheduleAddCommand s3DifferentName = new ScheduleAddCommand(eventName1, eventTime, eventDuration,
                uniqueMemberIndexes);

        //eventTime and eventDuration has to be similar due to a check done in Event class
        ScheduleAddCommand s4DifferentTime = new ScheduleAddCommand(eventName, eventTime1, eventDuration1,
                uniqueMemberIndexes);

        ScheduleAddCommand s5DifferentMember = new ScheduleAddCommand(eventName, eventTime, eventDuration,
                uniqueMemberIndexes1);

        assertTrue(s1.equals(s2Same));
        assertFalse(s1.equals(s3DifferentName));
        assertFalse(s1.equals(s4DifferentTime));
        assertFalse(s1.equals(s5DifferentMember));
    }

    /**
     * Generates a new ScheduleAddCommand with the details of the given event.
     */
    private ScheduleAddCommand getScheduleAddCommand(EventName eventName, EventTime eventTime,
                                                     EventDuration eventDuration, Set<Index> uniqueMemberIndexes,
                                                     Model model) {
        ScheduleAddCommand command = new ScheduleAddCommand(eventName, eventTime,
                eventDuration, uniqueMemberIndexes);
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
        public void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void restorePerson(ArrayList<ReadOnlyPerson> person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyAddressBook newRecycleibin) {
            fail("This method should not be called.");
        }

        @Override
        public void resetRecyclebin(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        public void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        public void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
            fail("This method should not be called");
        }

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

        public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Event> getEventList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateListOfPerson(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");

        }

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void sortEvents(LocalDate date) {
            fail("This method should not be called.");
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            fail("This method should not be called.");
            return false;
        }

    }

    /**
     * A Model stub that always accept the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public void addEvent(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons, Event event)
                throws DuplicateEventException, DuplicatePersonException, PersonNotFoundException {

            ArrayList<ReadOnlyPerson> members = new ArrayList<>();
            targets.forEach(p -> members.add(p));

            event.setMemberList(new MemberList(targets));
            eventsAdded.add(new Event(event));
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            ArrayList<ReadOnlyPerson> stubList = new ArrayList<>();
            stubList.add(TypicalPersons.ALICE);
            stubList.add(TypicalPersons.BOB);
            stubList.add(TypicalPersons.CARL);

            return FXCollections.observableArrayList(stubList);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }

        @Override
        public boolean hasEvenClashes(Event event) {
            return false;
        }

        //Overwrite default model stub as filtered list is updated during execution
        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {

        }
    }
}
