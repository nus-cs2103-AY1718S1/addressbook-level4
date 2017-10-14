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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
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
    public void constructor_nullSortType_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(null, true);
    }

    @Test
    public void constructor_nullSortOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SortCommand(CliSyntax.PREFIX_NAME.toString(), null);
    }

    @Test
    public void execute_catchEmptyListException() throws CommandException {
        thrown.expect(CommandException.class);

        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        getSortCommandForPerson("n/", false, modelStub).execute();
    }

    @Test
    public void test_getSortCommandResult_sortSuccessful() throws Exception {
        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        populateModel(modelStub);

        String sortType = "/n";
        Boolean isDescending = false;
        CommandResult commandResult = getSortCommandForPerson(sortType, isDescending, modelStub).execute();

        assertEquals(String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name", "ascending"),
                commandResult.feedbackToUser);
    }

    @Test
    public void testComparator() throws Exception {
        ModelStubAcceptingPersonForSort modelStub = new ModelStubAcceptingPersonForSort();
        populateModel(modelStub);

        ArrayList<Person> expectedList = new ArrayList<>();
        expectedList.add(validPerson1);
        expectedList.add(validPerson2);
        expectedList.add(validPerson3);

        //Test comparator - sort by name
        expectedList.sort((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_NAME.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by phone
        expectedList.sort((o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_PHONE.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by email
        expectedList.sort((o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(o2.getEmail().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_EMAIL.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by address
        expectedList.sort((o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(o2.getAddress().toString()));
        getSortCommandForPerson(CliSyntax.PREFIX_ADDRESS.toString(), false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

        //Test comparator - sort by address
        expectedList.sort((o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()));
        getSortCommandForPerson("z/", false, modelStub).execute();
        assertEquals(expectedList, modelStub.personsAdded);

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
            if (personsAdded.size() < 1) {
                throw new EmptyListException();
            }

            personsAdded.sort(sortType);
            if (isDescending) {
                Collections.reverse(personsAdded);
            }
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }


}
