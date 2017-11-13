package seedu.address.logic.commands;

//@@author quanle1994

import static junit.framework.TestCase.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

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
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.UserNotFoundException;
import seedu.address.ui.UiManager;

/**
 * Test Remove User Command
 */
public class RemoveUserCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_noUserToRemove() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_ENCRYPTION_ERROR);

        new RemoveUserCommand("abc", "abc", true).execute();
    }

    @Test
    public void execute_userNotFound() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_USER_NOT_FOUND);

        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        modelStub.control = true;
        getRemoveUserCommand(modelStub, true).execute();
    }

    @Test
    public void execute_deleteUserFailure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_USER_NOT_FOUND);

        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        modelStub.control2 = true;
        getRemoveUserCommand(modelStub, true).execute();
    }

    @Test
    public void execute_deleteEncryptedFileFailure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_ENCRYPTION_ERROR);

        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        getRemoveUserCommand(modelStub, true).execute();
    }

    @Test
    public void execute_decryptionFailure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_ENCRYPTION_ERROR);

        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        getRemoveUserCommand(modelStub, false).execute();
    }

    @Test
    public void execute_releaseContactFailure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveUserCommand.MESSAGE_ENCRYPTION_ERROR);

        ModelStubThrowingError modelStub = new ModelStubThrowingError();
        getRemoveUserCommand(modelStub, false).execute();
    }

    @Test
    public void execute_removeSuccessful() throws Exception {
        ModelStubSuccess modelStub = new ModelStubSuccess();
        CommandResult commandResult = getRemoveUserCommand(modelStub, false).execute();
        assertEquals(String.format(RemoveUserCommand.MESSAGE_REMOVE_USER_SUCCESS, "abc"), commandResult.feedbackToUser);
        assertEquals(new ArrayList<>(), modelStub.users);
    }

    @Test
    public void execute_removeSuccessfulWithCascading() throws Exception {
        ModelStubSuccess modelStub = new ModelStubSuccess();
        CommandResult commandResult = getRemoveUserCommand(modelStub, true).execute();
        assertEquals(String.format(RemoveUserCommand.MESSAGE_REMOVE_USER_SUCCESS, "abc"), commandResult.feedbackToUser);
        assertEquals(new ArrayList<>(), modelStub.users);
    }

    private RemoveUserCommand getRemoveUserCommand(Model model, boolean cascade) {
        RemoveUserCommand command = new RemoveUserCommand("abc", "abc", cascade);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    private class ModelStubSuccess extends ModelStub {
        private ArrayList<ReadOnlyUser> users = new ArrayList<>();

        private void addUser() {
            users.add(new User("abc"));
        }

        @Override
        public String retrieveSaltFromStorage(String userId) throws UserNotFoundException {
            return "abc";
        }

        @Override
        public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
            users.remove(new User(userName));
        }
    }

    private class ModelStubThrowingError extends ModelStub {
        private boolean control = false;
        private boolean control2 = false;
        private boolean control3 = false;

        @Override
        public String retrieveSaltFromStorage(String userId) throws UserNotFoundException {
            if (control) {
                throw new UserNotFoundException();
            }
            return "abc";
        }

        @Override
        public void deleteUser(String userName, String saltedPasswordHex) throws UserNotFoundException {
            if (control2) {
                throw new UserNotFoundException();
            }
        }

        @Override
        public void deleteEncryptedContacts(String substring) {
            throw new NullPointerException();
        }

        @Override
        public void decrypt(String fileName, String pass) throws Exception {
            if (control3) {
                throw new Exception();
            }
        }

        @Override
        public void releaseEncryptedContacts(String fileName) throws DataConversionException,
                IOException {
            throw new IOException();
        }
    }
}
