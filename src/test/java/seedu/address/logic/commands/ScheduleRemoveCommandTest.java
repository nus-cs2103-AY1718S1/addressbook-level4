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

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

//@@author eldriclim
public class ScheduleRemoveCommandTest {

    @Test
    public void testScheduleRemoveCommand() throws Exception {
        Set<Index> eventListIndex = new HashSet<>();
        eventListIndex.add(new Index(0));

        ModelStubAcceptingEventRemoved modelStub = new ModelStubAcceptingEventRemoved();

        ScheduleRemoveCommand command;

        command = getScheduleRemoveCommand(eventListIndex, modelStub);

        CommandResult commandResult = command.execute();


        ArrayList<ReadOnlyPerson> members = new ArrayList<>();
        members.add(TypicalPersons.ALICE);

        Event expectedEventToRemove = new Event(new MemberList(members), new EventName("Event name"),
                new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                new EventDuration(Duration.ofMinutes(5)));

        assertEquals(commandResult.feedbackToUser,
                String.format(ScheduleRemoveCommand.MESSAGE_SUCCESS, expectedEventToRemove.toString()));
    }

    public ScheduleRemoveCommand getScheduleRemoveCommand(Set<Index> eventListIndex, Model model) {
        ScheduleRemoveCommand command = new ScheduleRemoveCommand(eventListIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void testScheduleRemoveEquals() {
        ScheduleRemoveCommand s1 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0))));
        ScheduleRemoveCommand s2 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(0))));
        ScheduleRemoveCommand s3 = new ScheduleRemoveCommand(new HashSet<>(Arrays.asList(new Index(1))));

        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
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
     * A Model stub that always accept the event being removed.
     */
    private class ModelStubAcceptingEventRemoved extends ModelStub {

        private ArrayList<Event> eventList = new ArrayList<>();
        private AddressBook addressBookStub = new AddressBook();
        private ObservableList<ReadOnlyPerson> filteredPersonListStub;

        @Override
        public void removeEvents(ArrayList<ReadOnlyPerson> targets, ArrayList<ReadOnlyPerson> editedPersons,
                                 ArrayList<Event> toRemoveEvents)
                throws DuplicatePersonException, PersonNotFoundException, EventNotFoundException {

            toRemoveEvents.forEach(e -> eventList.remove(e));
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
            return addressBookStub;
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }

        /**
         * Returns an ObservableList of Events with a single event containing one members.
         *
         * @return
         */
        @Override
        public ObservableList<Event> getEventList() {
            try {
                eventList.clear();
                Event e1 = new Event(new MemberList(), new EventName("Event name"),
                        new EventTime(LocalDateTime.now(), Duration.ofMinutes(5)),
                        new EventDuration(Duration.ofMinutes(5)));

                eventList.add(e1);

                Person alice = new PersonBuilder().withName("Alice Pauline")
                        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                        .withPhone("85355255")
                        .withTags("friends").build();
                alice.setEvents(new HashSet<>(Arrays.asList(eventList.get(0))));

                ReadOnlyPerson aliceReadOnly = new Person(alice);
                addressBookStub.addPerson(aliceReadOnly);
                addressBookStub.addPerson(TypicalPersons.BOB);
                addressBookStub.addPerson(TypicalPersons.CARL);
                ArrayList<ReadOnlyPerson> members = new ArrayList<>();
                members.add(aliceReadOnly);

                e1.setMemberList(new MemberList(members));

            } catch (IllegalValueException e) {
                //Should not reach this point, try-catch is used to bypass Override restriction
                e.printStackTrace();
            }

            return FXCollections.observableArrayList(eventList);

        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            filteredPersonListStub = new FilteredList<ReadOnlyPerson>(addressBookStub.getPersonList());
            filteredPersonListStub.filtered(predicate);
        }
    }
}
