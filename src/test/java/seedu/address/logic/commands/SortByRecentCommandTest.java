package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SortCommandTestUtil.assertFilteredSortCommandSuccess;
import static seedu.address.logic.commands.SortCommandTestUtil.assertUnfilteredSortCommandSuccess;
import static seedu.address.testutil.StorageUtil.getDummyStorage;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonRecentComparator;

//@@author marvinchin
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortByRecentCommand}.
 */
public class SortByRecentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        SortByRecentCommand sortCommand = prepareCommand();
        String expectedMessage = SortByRecentCommand.MESSAGE_SORT_SUCCESS;

        assertUnfilteredSortCommandSuccess(sortCommand, model, new PersonRecentComparator(), expectedMessage);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        SortByRecentCommand sortCommand = prepareCommand();
        String expectedMessage = SortByRecentCommand.MESSAGE_SORT_SUCCESS;

        assertFilteredSortCommandSuccess(sortCommand, model, new PersonRecentComparator(), expectedMessage);
    }

    @Test
    public void equals() {
        SortByRecentCommand sortByRecentCommandOne = new SortByRecentCommand();
        SortByRecentCommand sortByRecentCommandTwo = new SortByRecentCommand();

        // same object -> returns true
        assertTrue(sortByRecentCommandOne.equals(sortByRecentCommandOne));

        // same type -> returns true
        assertTrue(sortByRecentCommandOne.equals(sortByRecentCommandTwo));

        // different types -> returns false
        assertFalse(sortByRecentCommandOne.equals(1));

        // null -> returns false
        assertFalse(sortByRecentCommandOne.equals(null));
    }

    /**
     * Returns a {@code SortByRecentCommand}.
     */
    private SortByRecentCommand prepareCommand() {
        SortByRecentCommand sortCommand = new SortByRecentCommand();
        sortCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
