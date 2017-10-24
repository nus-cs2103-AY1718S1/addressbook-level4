package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RestoreBackupCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND, "common");
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, "common");
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model, "common");
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void executeAfterUserPermission_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND, "permission");
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void executeAfterUserPermission_validCommand_success() {
        String permission = "no";
        assertCommandSuccess(RestoreBackupCommand.COMMAND_WORD,
                RestoreBackupCommand.MESSAGE_NO_BACKUP_FILE, model, "common");
        assertCommandSuccess(permission, RestoreBackupCommand.MESSAGE_FAILURE, model, "permission");
        assertHistoryCorrect(permission, RestoreBackupCommand.COMMAND_WORD);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPersonList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model, String)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel, String commandType) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel, commandType);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model, String)
     */
    private void assertParseException(String inputCommand, String expectedMessage, String commandType) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage, commandType);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model, String)
     */
    private void assertCommandException(String inputCommand, String expectedMessage, String commandType) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage, commandType);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model, String)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException,
                                      String expectedMessage, String commandType) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel, commandType);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel, String commandType) {
        if (commandType.equals("common")) {
            executeCommonCommand(expectedException, inputCommand, expectedMessage, expectedModel);
        } else { //if commandType.equals("permission")
            executePermissionCommandAfterUserPermission(expectedException, inputCommand,
                    expectedMessage, expectedModel);
        }
    }

    /**
     * Executes the previous command after user enters permission
     * and check for correct exception thrown and correct feedback returned.
     */
    private void executePermissionCommandAfterUserPermission(Class<?> expectedException, String inputCommand,
                                                             String expectedMessage, Model expectedModel) {
        try {
            CommandResult result = logic.executeAfterUserPermission(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command and check for correct exception thrown and correct feedback returned.
     */
    private void executeCommonCommand(Class<?> expectedException, String inputCommand,
                                      String expectedMessage, Model expectedModel) {
        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }
}
