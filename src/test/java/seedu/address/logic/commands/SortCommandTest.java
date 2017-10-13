package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Person validPerson1 = new PersonBuilder()
            .withName("C").withPhone("465").withEmail("z@z").withAddress("a").build();
    private Person validPerson2 = new PersonBuilder()
            .withName("B").withPhone("123").withEmail("d@d").withAddress("s").build();
    private Person validPerson3 = new PersonBuilder()
            .withName("A").withPhone("987").withEmail("f@f").withAddress("b").build();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null, new Boolean(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        populateModel(modelStub);

        CommandResult commandResult = getSortCommandForPerson("n/", false, modelStub).execute();

        assertEquals(String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS), commandResult.feedbackToUser);
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
     * Generates a new SortCommand with the details of the given list.
     */
    private SortCommand getSortCommandForPerson(String sortType, boolean isDescending, Model model) {
        SortCommand command = new SortCommand(sortType, isDescending);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        return command;
    }

    /**
     * Populate model list with persons
     */
    private void populateModel(Model modelStub) throws Exception {
        getAddCommandForPerson(validPerson1, modelStub).execute();
        getAddCommandForPerson(validPerson2, modelStub).execute();
        getAddCommandForPerson(validPerson3, modelStub).execute();
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
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
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
        public void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
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
    }

    /**
     * A Model stub that always accept the person being added and sort list.
     */
    private class ModelStubAcceptingPersonForSort extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
            personsAdded.sort(sortType);
            if (isDescending) {
                Collections.reverse(personsAdded);
            }
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            return;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
