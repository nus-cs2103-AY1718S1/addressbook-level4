package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author Chng-Zhi-Xuan
public class ClearBookmarkCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearBookmarkCommand.MESSAGE_CLEAR_BOOKMARK_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearBookmarkCommand.MESSAGE_CLEAR_BOOKMARK_SUCCESS, model);
    }

    @Test
    public void equals() {
        ClearBookmarkCommand clearBookmarkFirstCommand = new ClearBookmarkCommand();

        // same object -> returns true
        assertTrue(clearBookmarkFirstCommand.equals(clearBookmarkFirstCommand));

        // same values -> returns true
        ClearBookmarkCommand clearBookmarkFirstCommandCopy = new ClearBookmarkCommand();
        assertTrue(clearBookmarkFirstCommand.equals(clearBookmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(clearBookmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearBookmarkFirstCommand.equals(null));
    }

    private ClearBookmarkCommand prepareCommand(Model model) {
        ClearBookmarkCommand command = new ClearBookmarkCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
