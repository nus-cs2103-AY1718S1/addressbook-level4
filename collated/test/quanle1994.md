# quanle1994
###### /java/seedu/address/logic/commands/LockCommandIntegrationTest.java
``` java

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUsers.getTypicalAccount;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;
import seedu.address.ui.UiManager;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class LockCommandIntegrationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new EventBook(), new UserPrefs(), getTypicalAccount(),
                new Config());
    }

    @Test
    public void execute_duplicateUserException() throws Exception {
        User invalidUser = new User("a", "555555555555555555", "e");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LockCommand.MESSAGE_EXISTING_USER);

        prepareCommand(invalidUser, model).execute();
    }

    @Test
    public void execute_newUser_success() throws Exception {
        User validUser = new User("e", "555555555555555555", "e");

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(),
                model.getAccount(), new Config());
        expectedModel.persistUserAccount(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(LockCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private LockCommand prepareCommand(User user, Model model) {
        LockCommand command = new LockCommand(user.getUserId(), user.getPassword());
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/LoginCommandTest.java
``` java

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
```
###### /java/seedu/address/logic/commands/RemoveUserCommandTest.java
``` java

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
```
###### /java/seedu/address/logic/commands/LogoutCommandTest.java
``` java

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
```
###### /java/seedu/address/logic/commands/LockCommandTest.java
``` java

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
```
###### /java/seedu/address/model/user/UserTest.java
``` java

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test User
 */
public class UserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_compareUser() {
        User user = new User();
        user.setUserId("abc");
        user.setPassword("abc");
        assertTrue(user.sameAs(new User("abc", "abc", "abc")));
        assertTrue(user.equals(new User("abc", "", "")));
    }
}
```
