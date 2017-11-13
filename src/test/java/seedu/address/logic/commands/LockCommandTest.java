package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.commons.util.digestutil.HashDigest;
import seedu.address.commons.util.digestutil.HexCode;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.lockmodelstub.ModelStub;
import seedu.address.logic.currentuser.CurrentUserDetails;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.ui.UiManager;

//@@author quanle1994

/**
 * Test Lock Command.
 */
public class LockCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_lockSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();

        CommandResult commandResult = getLockCommand("test", "123", modelStub).execute();
        assertEquals(LockCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        String userIdHash = new String(new HashDigest().getHashDigest("test"));
        String userIdHex = new HexCode().getHexFormat(userIdHash);
        assertEquals(userIdHex, modelStub.userAdded.getUserId());
        assertEquals(new CurrentUserDetails().getUserId(), "test");

        commandResult = getLockCommand("lequangquan", "123", modelStub).execute();
        assertEquals(LockCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        userIdHash = new String(new HashDigest().getHashDigest("lequangquan"));
        userIdHex = new HexCode().getHexFormat(userIdHash);
        assertEquals(userIdHex, modelStub.userAdded.getUserId());
        assertEquals(new CurrentUserDetails().getUserId(), "lequangquan");
    }

    @Test
    public void execute_throwDuplicateUserException() throws Exception {
        ModelStubThrowingDuplicateUserException modelStub = new ModelStubThrowingDuplicateUserException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(LockCommand.MESSAGE_EXISTING_USER);

        getLockCommand("test", "123456", modelStub).execute();
    }

    @Test
    public void execute_correctUserAndPassword() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        LockCommand lockCommand = getLockCommand("test", "123456", modelStub);
        lockCommand.execute();
        String userName = lockCommand.getUserId();
        String passWord = lockCommand.getPasswordText();
        assertEquals(userName, "test");
        assertEquals(passWord, "123456");

    }

    @Test
    public void execute_nullModelException() throws Exception {
        Model modelStub = null;

        thrown.expect(NullPointerException.class);

        getLockCommand("test", "123456", modelStub).execute();
    }

    /**
     * Generates a new LockCommand with the details of the given event.
     */
    public LockCommand getLockCommand(String userName, String password, Model model) {
        LockCommand command = new LockCommand(userName, password);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    /**
     * A Model stub that always throw a DuplicateEventException when trying to add a event.
     */
    private class ModelStubThrowingDuplicateUserException extends ModelStub {
        @Override
        public void persistUserAccount(ReadOnlyUser event) throws DuplicateUserException {
            throw new DuplicateUserException();
        }
    }

    private class ModelStubAcceptingUserAdded extends ModelStub {
        private User userAdded;

        @Override
        public void persistUserAccount(ReadOnlyUser user) throws DuplicateUserException {
            userAdded = new User(user);
        }
    }
}
