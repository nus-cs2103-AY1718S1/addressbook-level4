package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.ARG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.ARG_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.ARG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.ARG_NAME;
import static seedu.address.logic.parser.CliSyntax.ARG_PHONE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalPersons;
import static seedu.address.testutil.TypicalPersons.getUnsortedTypicalAddressBook;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author bladerail
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Test
    public void execute_sortByNameSuccess() {
        List<ReadOnlyPerson> sortedListByName = getSortedTypicalPersons();
        sortUnsortedAddressBookByFilterType(ARG_NAME, sortedListByName);
    }

    @Test
    public void execute_sortByDefaultSortsByNameSuccess() {
        List<ReadOnlyPerson> sortedListByName = getSortedTypicalPersons();
        sortUnsortedAddressBookByFilterType(ARG_DEFAULT, sortedListByName);
    }

    @Test
    public void execute_sortByEmailSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(GEORGE), new Person(DANIEL), new Person(CARL),
                new Person(BENSON), new Person(FIONA), new Person(ELLE));
        sortUnsortedAddressBookByFilterType(ARG_EMAIL, sortedList);

    }

    @Test
    public void execute_sortByPhoneSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                new Person(CARL), new Person(BENSON));
        sortUnsortedAddressBookByFilterType(ARG_PHONE, sortedList);

    }

    @Test
    public void execute_sortByAddressSuccess() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(DANIEL),
                new Person(ALICE), new Person(BENSON), new Person(GEORGE), new Person(FIONA),
                new Person(ELLE), new Person(CARL));
        sortUnsortedAddressBookByFilterType(ARG_ADDRESS, sortedList);

    }

    @Test
    public void execute_sortCommandSortsLastShownList() {
        List<ReadOnlyPerson> sortedList = Arrays.asList(new Person(ALICE),
                new Person(DANIEL), new Person(ELLE), new Person(FIONA), new Person(GEORGE),
                new Person(CARL), new Person(BENSON));
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = createExpectedModel(sortedList);


        Predicate<ReadOnlyPerson> predicate = new ContainsKeywordsPredicate(Arrays.asList("Street"));
        model.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        String filterType = ARG_PHONE;

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);
    }

    @Test
    public void equals() {
        String filterType = "";

        final SortCommand standardCommand = new SortCommand(filterType);

        // same filterTypes -> returns true
        SortCommand commandWithSameValues = new SortCommand(filterType);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different filterTypes -> returns false
        assertFalse(standardCommand.equals(new SortCommand("default")));

    }

    /**
     * Returns a {@code SortCommand} with parameters {@code filterType}.
     */
    private SortCommand prepareCommand(String filterType) {
        SortCommand sortCommand = new SortCommand(filterType);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Returns an expectedModel with a new addressBook created using the sortedList
     * @param sortedList A sortedlist of {@code ReadOnlyPerson}
     * @return
     */
    private Model createExpectedModel(List<ReadOnlyPerson> sortedList) {
        try {
            AddressBook sortedAddressBook = new AddressBook();
            sortedAddressBook.setPersons(sortedList);
            return new ModelManager(sortedAddressBook, new UserPrefs(), new UserPerson());
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
    }

    /**
     * Sorts the unsortedAddressBook by indicated filter type and matches it with the expected list order
     * @param filterType A filtertype
     * @param sortedList A sorted list of {@code ReadOnlyPerson}
     */
    private void sortUnsortedAddressBookByFilterType(String filterType, List<ReadOnlyPerson> sortedList) {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs(), new UserPerson());
        expectedModel = createExpectedModel(sortedList);

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SUCCESS, filterType), expectedModel);
    }
}
