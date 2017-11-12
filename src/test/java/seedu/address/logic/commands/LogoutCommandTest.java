package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author derrickchua

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LogoutCommand}.
 */
public class LogoutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLogout_success() throws Exception {

        LogoutCommand logoutCommand = prepareCommand();
        java.io.File filetoDelete =
                new java.io.File("data/StoredCredential");
        filetoDelete.mkdirs();
        filetoDelete.createNewFile();
        String expectedMessage = String.format(LogoutCommand.MESSAGE_SUCCESS);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(logoutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addLogout_failure() throws Exception {

        LogoutCommand logoutCommand = prepareCommand();

        java.io.File filetoDelete =
                new java.io.File("data/StoredCredential");
        filetoDelete.delete();
        String expectedMessage = String.format(LogoutCommand.MESSAGE_FAILURE);
        assertCommandFailure(logoutCommand, model, expectedMessage);
    }


    @Test
    public void equals() {
        LogoutCommand logoutFirstCommand = new LogoutCommand();
        LogoutCommand logoutSecondCommand = new LogoutCommand();

        // same object -> returns true
        assertTrue(logoutFirstCommand.equals(logoutFirstCommand));

        // different types -> returns false
        assertFalse(logoutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(logoutFirstCommand.equals(null));

        // returns true
        assertTrue(logoutFirstCommand.equals(logoutSecondCommand));
    }

    /**
     * Returns a {@code LogoutCommand} with the parameter {@code index}.
     */
    private LogoutCommand prepareCommand() {
        LogoutCommand logoutcommand = new LogoutCommand();
        logoutcommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return logoutcommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
