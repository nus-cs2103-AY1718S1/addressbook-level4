package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute() throws CommandException {
        CommandResult result = sortCommand.execute();

        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        assertEquals(SortCommand.MESSAGE_SUCCESS, result.feedbackToUser);

        assertSortSuccessful(model.getAddressBook(), model.getFilteredPersonList());

        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    private void assertSortSuccessful(ReadOnlyAddressBook addressBook, List<ReadOnlyPerson> peopleList) {
        assertTrue(isAddressBookSorted(addressBook, peopleList));
    }

    /**
     * Returns true or false to see whether {@code AddressBook} is sorted.<br>
     * Asserts that {@code AddressBook} contains all individuals being compared.
     */
    private boolean isAddressBookSorted(ReadOnlyAddressBook addressBook, List<ReadOnlyPerson> peopleList) {
        for (int i = 0; i < peopleList.size() - 1; i++) {
            ReadOnlyPerson person1 = peopleList.get(i);
            ReadOnlyPerson person2 = peopleList.get(i + 1);

            assertTrue(addressBook.getPersonList().contains(person1));
            assertTrue(addressBook.getPersonList().contains(person2));

            //return false when names are incorrectly positioned or unsorted
            if (compareNamesAlphabetically(person1, person2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns false if name of person1 is correctly positioned alphabetically in front of person2.
     */
    private boolean compareNamesAlphabetically(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        String name1 = person1.getName().toString();
        String name2 = person2.getName().toString();
        System.out.println(name1 + " " + name2);
        int compare = name1.compareTo(name2);

        //compare > 0 if for e.g. name1 starts with h and name2 starts with f
        if (compare > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns an {@code AddressBook} with people in unsorted names.
     */
    private static AddressBook getUnsortedAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    private static List<ReadOnlyPerson> getUnsortedPersons() {
        return new ArrayList<>(Arrays.asList(CARL, ALICE, FIONA, DANIEL, ELLE, BENSON, GEORGE));
    }
}
