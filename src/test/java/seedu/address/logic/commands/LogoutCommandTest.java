package seedu.address.logic.commands;

//@@author quanle1994

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.lockmodelstub.ModelStub;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.ui.UiManager;

/**
 * Test Logout Command
 */
public class LogoutCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_logoutBeforeLoginException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_LOGOUT_ERROR);

        getLogoutCommand(new ModelStubAcceptingUserAdded()).execute();
    }

    @Test
    public void execute_logoutSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        CurrentUserDetails.setCurrentUser("test", "1111111111111111111111111111111", "", "");
        LogoutCommand logoutCommand = getLogoutCommand(modelStub);
        CommandResult commandResult = logoutCommand.execute();
        assertEquals(LogoutCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_hexIdIndexOutOfBound() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        CurrentUserDetails.setCurrentUser("test", "", "", "");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_ENCRYPTION_ERROR);

        getLogoutCommand(modelStub).execute();
    }

    @Test
    public void execute_decryptionError() throws Exception {
        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        CurrentUserDetails.setCurrentUser("test", "1111111111111111111", "abc",
                "abc");
        modelStub.control = true;
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_ENCRYPTION_ERROR);

        getLogoutCommand(modelStub).execute();
    }

    @Test
    public void execute_refreshAbError() throws Exception {
        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        CurrentUserDetails.setCurrentUser("test", "1111111111111111111", "abc",
                "abc");
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_ENCRYPTION_ERROR);

        getLogoutCommand(modelStub).execute();
    }

    private LogoutCommand getLogoutCommand(Model model) {
        LogoutCommand command = new LogoutCommand();
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    private class ModelStubAcceptingUserAdded extends ModelStub {
    }

    private class ModelStubThrowingError extends ModelStub {
        private boolean control = false;

        @Override
        public void decrypt(String fileName, String pass) throws Exception {
            if (control) {
                throw new Exception();
            }
        }

        @Override
        public void refreshAddressBook() throws IOException, DataConversionException {
            throw new IOException();
        }
    }
}
