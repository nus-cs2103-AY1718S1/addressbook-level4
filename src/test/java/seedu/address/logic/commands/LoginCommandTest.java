package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
            LoginCommand loginCommand = prepareCommand(TEST_USERNAME, TEST_PASSWORD);
            CommandResult result = loginCommand.execute();
            assertEquals(hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_ACKNOWLEDGEMENT, result.feedbackToUser);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_login_failure() {
        try {
            LoginCommand loginCommand = prepareCommand(TEST_USERNAME, TEST_PASSWORD);
            CommandResult result = loginCommand.execute();
            assertEquals(!hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_UNSUCCESSFUL, result.feedbackToUser);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void equals() {
        try {
            Username usernameOne = new Username("hellonihao123");
            Username usernameTwo = new Username("nihaohello123");
            Password passwordOne = new Password("idontcare!?");
            Password passwordTwo = new Password("careidont!?");

            LoginCommand loginFirstCommand = new LoginCommand(usernameOne, passwordOne);
            LoginCommand loginSecondCommand = new LoginCommand(usernameTwo, passwordTwo);

            // same object -> returns true
            assertTrue(loginFirstCommand.equals(loginFirstCommand));

            // same values -> returns true
            LoginCommand loginFirstCommandCopy = new LoginCommand(usernameOne, passwordOne);
            assertTrue(loginFirstCommand.equals(loginFirstCommandCopy));

            // different types -> returns false
            assertFalse(loginFirstCommand.equals(1));

            // null -> returns false
            assertFalse(loginFirstCommand.equals(null));

            // different login details -> returns false
            assertFalse(loginFirstCommand.equals(loginSecondCommand));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }

    /**
     * Parses {@code uname} and {@pwd} into a {@code LoginCommand}.
     */
    private LoginCommand prepareCommand(String uname, String pwd) {
       try {
           Username username = new Username(uname);
           Password password = new Password(pwd);
           LoginCommand command = new LoginCommand(username, password);
           command.setData(model, new CommandHistory(), new UndoRedoStack());
           return command;
       } catch (IllegalValueException ive) {
           ive.printStackTrace();
       }
    }
}
