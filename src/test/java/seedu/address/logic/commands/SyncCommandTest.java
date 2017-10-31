package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.concurrent.Executors;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author derrickchua
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SyncCommand}.
 */
public class SyncCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSync_failure() throws Exception {
        SyncCommand syncCommand = prepareCommand();

        String expectedMessage = String.format(SyncCommand.MESSAGE_FAILURE);
        assertCommandFailure(syncCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        SyncCommand syncFirstCommand = new SyncCommand();
        SyncCommand syncSecondCommand = new SyncCommand();

        // same object -> returns true
        assertTrue(syncFirstCommand.equals(syncFirstCommand));

        // different types -> returns false
        assertFalse(syncFirstCommand.equals(1));

        // null -> returns false
        assertFalse(syncFirstCommand.equals(null));

        // returns true
        assertTrue(syncFirstCommand.equals(syncSecondCommand));
    }

    /**
     * Returns a {@code SyncCommand}
     */
    private SyncCommand prepareCommand() {
        SyncCommand synccommand = new SyncCommand();
        synccommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return synccommand;
    }

    /**
     * Returns a {@code LoginCommand}
     */
    private LoginCommand prepareLogin() {
        LoginCommand logincommand = new LoginCommand();
        logincommand.setData(model, new CommandHistory(), new UndoRedoStack());
        logincommand.setExecutor(Executors.newSingleThreadExecutor());
        return logincommand;
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
