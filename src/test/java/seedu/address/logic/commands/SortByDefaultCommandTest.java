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
import seedu.address.model.person.PersonDefaultComparator;

//@@author marvinchin
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortByDefaultCommand}.
 */
public class SortByDefaultCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        SortByDefaultCommand sortCommand = prepareCommand();
        String expectedMessage = SortByDefaultCommand.MESSAGE_SORT_SUCCESS;

        assertUnfilteredSortCommandSuccess(sortCommand, model, new PersonDefaultComparator(), expectedMessage);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        SortByDefaultCommand sortCommand = prepareCommand();
        String expectedMessage = SortByDefaultCommand.MESSAGE_SORT_SUCCESS;

        assertFilteredSortCommandSuccess(sortCommand, model, new PersonDefaultComparator(), expectedMessage);
    }

    @Test
    public void equals() {
        SortByDefaultCommand sortByDefaultCommandOne = new SortByDefaultCommand();
        SortByDefaultCommand sortByDefaultCommandTwo = new SortByDefaultCommand();

        // same object -> returns true
        assertTrue(sortByDefaultCommandOne.equals(sortByDefaultCommandOne));

        // same type -> returns true
        assertTrue(sortByDefaultCommandOne.equals(sortByDefaultCommandTwo));

        // different types -> returns false
        assertFalse(sortByDefaultCommandOne.equals(1));

        // null -> returns false
        assertFalse(sortByDefaultCommandOne.equals(null));
    }

    /**
     * Returns a {@code SortByDefaultCommand}.
     */
    private SortByDefaultCommand prepareCommand() {
        SortByDefaultCommand sortCommand = new SortByDefaultCommand();
        sortCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
