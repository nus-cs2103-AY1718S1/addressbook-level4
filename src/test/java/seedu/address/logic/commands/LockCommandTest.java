//@@author Hailinx
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.LockCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.security.SecurityManager;
import seedu.address.security.SecurityStubUtil;

public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LockCommand firstCommand = new LockCommand("first");
        LockCommand secondCommand = new LockCommand("second");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        LockCommand firstCopy = new LockCommand("first");
        assertTrue(firstCommand.equals(firstCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void test_execute_whenUnSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialUnSecuredSecurity();

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void test_execute_whenSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecuredSecurity();

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_DUPLICATED_LOCK);
    }

    @Test
    public void test_execute_whenIoException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithIoException(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_STORAGE_ERROR);
    }

    @Test
    public void test_execute_whenEncryptOrDecryptException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithEncryptOrDecryptException(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_LOCK_PASSWORD);
    }

    @After
    public void after() {
        SecurityManager.setInstance(null);
    }

    /**
     * Parses {@code userInput} into a {@code LockCommand} in default mode.
     */
    private LockCommand prepareCommand(String userInput) throws ParseException {
        LockCommand command = new LockCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(LockCommand command, String expectedMessage) throws CommandException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
