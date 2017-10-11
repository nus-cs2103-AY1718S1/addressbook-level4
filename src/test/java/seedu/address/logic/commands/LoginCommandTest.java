package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_ACKNOWLEDGEMENT;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_UNSUCCESSFUL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.events.ui.LoginAppRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Password;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.Username;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jelneo
public class LoginCommandTest {

    private static final String TEST_USERNAME = "TESTloanShark97";
    private static final String TEST_PASSWORD = "TESThitMeUp123";
    private final boolean hasLoggedIn = true;
    private LoginAppRequestEvent event;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_login_success() {
        try {
            Username username = new Username(TEST_USERNAME);
            Password password = new Password(TEST_PASSWORD);
            LoginCommand loginCommand = new LoginCommand(username, password);
            loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());
            CommandResult result = loginCommand.execute();
            assertEquals(hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_ACKNOWLEDGEMENT, result.feedbackToUser);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_login_failure() {
        try {
            Username username = new Username(TEST_USERNAME);
            Password password = new Password(TEST_PASSWORD);
            LoginCommand loginCommand = new LoginCommand(username, password);
            loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());
            CommandResult result = loginCommand.execute();
            assertEquals(!hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_UNSUCCESSFUL, result.feedbackToUser);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
}
