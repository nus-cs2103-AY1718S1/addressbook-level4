package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
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
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyAddressBook getRecycleBin() {
            return new AddressBook();
        }
    }

}
