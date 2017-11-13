package seedu.address.logic.commands;

//@@author quanle1994

import static junit.framework.TestCase.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.lockmodelstub.ModelStub;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.exceptions.UserNotFoundException;
import seedu.address.ui.UiManager;

/**
 * Test Login Command
 */
public class LoginCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_loginBeforeLogout() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_LOGIN_ERROR);

        CurrentUserDetails.setUserId("test");
        getLoginCommand(modelStub).execute();
    }

    @Test
    public void execute_userNotFoundException() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        modelStub.control = false;

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ERROR_NO_USER);

        CurrentUserDetails.setCurrentUser("PUBLIC", "", "PUBLIC", "PUBLIC");
        getLoginCommand(modelStub).execute();
    }

    @Test
    public void execute_loginSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        CurrentUserDetails.setCurrentUser("PUBLIC", "", "PUBLIC", "PUBLIC");
        LoginCommand loginCommand = getLoginCommand(modelStub);
        CommandResult commandResult = loginCommand.execute();
        assertEquals(LoginCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(CurrentUserDetails.getUserId(), "test");
        assertEquals(CurrentUserDetails.getPasswordText(), "test");
    }

    private LoginCommand getLoginCommand(Model model) {
        LoginCommand command = new LoginCommand("test", "test");
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    private class ModelStubAcceptingUserAdded extends ModelStub {
        private boolean control = true;

        @Override
        public String retrieveSaltFromStorage(String userId) throws UserNotFoundException {
            if (!control) {
                throw new UserNotFoundException();
            }
            return "123";
        }
    }
}
