package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.LockCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.security.SecurityStubUtil;

public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
    public void test_execute_whenIoexception() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithIoexception(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_STORAGE_ERROR);
    }

    @Test
    public void test_execute_whenEncryptOrDecryptException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithEncryptOrDecryptException(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_LOCK_PASSWORD);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} in default mode.
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
