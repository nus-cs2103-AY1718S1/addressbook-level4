//@@author cqhchan
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Database;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;



/**
 *
 */
public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());


    @Test
    public void equals() throws Exception {


        LoginCommand findFirstCommand = prepareCommand("private", "password");
        LoginCommand findSecondCommand = prepareCommand("Bro", "password");
        LoginCommand findThirdCommand = prepareCommand("Bro", "123");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findSecondCommand.equals(findSecondCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));

        // different objects -> returns false
        LoginCommand findFirstCommandCopy = prepareCommand("private", "password");
        assertFalse(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findSecondCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_invalidLogin() throws Exception {
        String expectedMessage = String.format(LoginCommand.MESSAGE_FAILURE, 0);
        LoginCommand command = prepareCommand("unknown", "unknown");
        CommandResult commandResult = command.execute();
        assertTrue(commandResult.feedbackToUser.equals(expectedMessage));
    }

    @Test
    public void execute_validLogin() throws Exception {
        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS, 0);
        LoginCommand command = prepareCommand("private", "password");
        CommandResult commandResult = command.execute();
        assertTrue(commandResult.feedbackToUser.equals(expectedMessage));
    }


    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private LoginCommand prepareCommand(String userInput, String userPassword) throws Exception {

        LoginCommand command =
                new LoginCommand(new Account(new Username(userInput), new Password(userPassword)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;

    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(LoginCommand command,
                                      String expectedMessage, List<ReadOnlyAccount> expectedList) {
        Database expectedDatabase = new Database(model.getDatabase());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredAccountList());
        assertEquals(expectedDatabase, model.getDatabase());
    }

}
