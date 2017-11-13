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
import seedu.address.logic.parser.UnlockCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.security.SecurityManager;
import seedu.address.security.SecurityStubUtil;

public class UnlockCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        UnlockCommand firstCommand = new UnlockCommand("first");
        UnlockCommand secondCommand = new UnlockCommand("second");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        UnlockCommand firstCopy = new UnlockCommand("first");
        assertTrue(firstCommand.equals(firstCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void test_execute_whenSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecuredSecurity();

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void test_execute_whenUnSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialUnSecuredSecurity();

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_DUPLICATED_UNLOCK);
    }

    @Test
    public void test_execute_whenIoexception() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithIoException(true);

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_ERROR_STORAGE_ERROR);
    }

    @Test
    public void test_execute_whenEncryptOrDecryptException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithEncryptOrDecryptException(true);

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_ERROR_LOCK_PASSWORD);
    }

    @After
    public void after() {
        SecurityManager.setInstance(null);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} in default mode.
     */
    private UnlockCommand prepareCommand(String userInput) throws ParseException {
        UnlockCommand command = new UnlockCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(UnlockCommand command, String expectedMessage) throws CommandException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
