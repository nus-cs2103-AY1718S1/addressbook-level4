//@@author zenghou
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;

public class LoginCommandTest {
    private Model model;

    @Before
    public void setup() {
        model = new ModelManager(new AddressBook(), new UserPrefs(),
                new UserCreds("test", "123"));
    }
    @Test
    public void execute_validUser_success() throws Exception {
        LoginCommand loginCommand = prepareCommand("test", "123");
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        Model expectedModel = new ModelManager();
        expectedModel.getUserCreds().validateCurrentSession();

        assertLoginSuccess(loginCommand, model, LoginCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidUsername_failure() throws Exception {
        LoginCommand loginCommand = prepareCommand("wrongUserName", "123");
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(loginCommand, model, LoginCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidPassword_failure() throws Exception {
        LoginCommand loginCommand = prepareCommand("test", "aslkdamlskd");
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(loginCommand, model, LoginCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_blankUsernameAndPassword_failure() throws Exception {
        LoginCommand loginCommand = prepareCommand("", "");
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(loginCommand, model, LoginCommand.MESSAGE_FAILURE);
    }

    private LoginCommand prepareCommand(String username, String password) {
        return new LoginCommand(username, password);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel}'s UserCreds matches {@code expectedModel}'s UserCreds
     */
    public static void assertLoginSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel.getUserCreds().isValidSession(), actualModel.getUserCreds().isValidSession());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

}
