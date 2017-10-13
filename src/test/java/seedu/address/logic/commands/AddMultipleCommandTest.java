package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.org.apache.regexp.internal.RE;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddMultipleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMultipleCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        validPersonArrayList.add(validPerson1);
        validPersonArrayList.add(validPerson2);
        
        CommandResult commandResult = getAddMultipleCommandForPerson(validPersonArrayList, modelStub).execute();

        StringBuilder successMessage = new StringBuilder();
        for(ReadOnlyPerson personToAdd: validPersonArrayList) {
            successMessage.append(System.lineSeparator());
            successMessage.append(personToAdd);
        }
        
        assertEquals(String.format(AddMultipleCommand.MESSAGE_SUCCESS, successMessage), commandResult.feedbackToUser);
        assertEquals(validPersonArrayList, modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        ArrayList<ReadOnlyPerson> validPersonArrayList = new ArrayList<>();
        Person validPerson1 = new PersonBuilder().withName("Alice").build();
        Person validPerson2 = new PersonBuilder().withName("Bob").build();
        validPersonArrayList.add(validPerson1);
        validPersonArrayList.add(validPerson2);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMultipleCommand.MESSAGE_DUPLICATE_PERSON);

        getAddMultipleCommandForPerson(validPersonArrayList, modelStub).execute();
    }

    @Test
    public void equals() {
        ArrayList<ReadOnlyPerson> personArrayList1 = new ArrayList<>();
        ArrayList<ReadOnlyPerson> personArrayList2 = new ArrayList<>();
        
        ReadOnlyPerson alice = new PersonBuilder().withName("Alice").build();
        ReadOnlyPerson bob = new PersonBuilder().withName("Bob").build();
        ReadOnlyPerson mary = new PersonBuilder().withName("Mary").build();
        ReadOnlyPerson jane = new PersonBuilder().withName("Jane").build();
        
        personArrayList1.add(alice);
        personArrayList1.add(bob);
        personArrayList2.add(mary);
        personArrayList2.add(jane);
        
        AddMultipleCommand addPersonArrayList1 = new AddMultipleCommand(personArrayList1);
        AddMultipleCommand addPersonArrayList2 = new AddMultipleCommand(personArrayList2);

        // same object -> returns true
        assertTrue(addPersonArrayList1.equals(addPersonArrayList1));

        // same values -> returns true
        AddMultipleCommand addPersonArrayList1Copy = new AddMultipleCommand(personArrayList1);
        assertTrue(addPersonArrayList1.equals(addPersonArrayList1Copy));

        // different types -> returns false
        assertFalse(addPersonArrayList1.equals(1));

        // null -> returns false
        assertFalse(addPersonArrayList1.equals(null));

        // different person -> returns false
        assertFalse(addPersonArrayList1.equals(addPersonArrayList2));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddMultipleCommand getAddMultipleCommandForPerson(ArrayList<ReadOnlyPerson> personList, Model model) {
        AddMultipleCommand command = new AddMultipleCommand(personList);
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
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
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
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
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
    }

}
