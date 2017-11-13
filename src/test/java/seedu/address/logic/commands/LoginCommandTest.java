package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.concurrent.Executors;

import org.junit.Test;

import seedu.address.google.OAuth;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author derrickchua
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LoginCommand}.
 */
public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLogin_success() throws Exception {
        LoginCommand loginCommand = prepareCommand();

        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(loginCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void equals() {
        LoginCommand loginFirstCommand = new LoginCommand();
        LoginCommand loginSecondCommand = new LoginCommand();

        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // returns true
        assertTrue(loginFirstCommand.equals(loginSecondCommand));
    }

    /**
     * Returns a {@code LoginCommand} with the parameter {@code index}.
     */
    private LoginCommand prepareCommand() {
        LoginCommand logincommand = new LoginCommand();
        logincommand.setData(model, new CommandHistory(), new UndoRedoStack());
        logincommand.setOAuth(OAuth.getInstance());
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
