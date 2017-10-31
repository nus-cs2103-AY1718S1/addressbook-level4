package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAscendingNameCommand;
import seedu.address.logic.commands.ListByTagCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListDescendingNameCommand;
import seedu.address.logic.commands.ListFailureCommand;
import seedu.address.logic.commands.ListReverseCommand;
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
    public void executeInvalidCommandFormatThrowsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void executeCommandExecutionErrorThrowsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void executeValidCommandSuccess() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    //@@author Jeremy
    @Test
    public void executeValidListByTagTest() {
        String listByTagCommand = ListByTagCommand.COMMAND_WORD + " colleagues";
        assertCommandSuccess(listByTagCommand, ListByTagCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listByTagCommand);


        //Existing feature do not check if tag is present or not.
        // Potential enhancement to account for this
        listByTagCommand = ListByTagCommand.COMMAND_WORD + " invalidTag";
        assertCommandSuccess(listByTagCommand, ListByTagCommand.MESSAGE_SUCCESS, model);

    }

    @Test
    public void executeInvalidListByTagTest() {
        String listByTagCommand = ListCommand.COMMAND_ALIAS + " t";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listByTagCommand);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " colleagues";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_WORD + " colleagues tag";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " tags";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " t/colleagues";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidAscendingListTest() {
        String listAscendingCommand = ListAscendingNameCommand.COMPILED_COMMAND;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listAscendingCommand);
        listAscendingCommand = ListAscendingNameCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_ALIAS;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_WORD;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidAscendingListTest() {
        String listAscendingCommand = ListAscendingNameCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listAscendingCommand);
        listAscendingCommand = ListAscendingNameCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_WORD + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidDescendingListTest() {
        String listDescendingCommand = ListDescendingNameCommand.COMPILED_COMMAND;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listDescendingCommand);
        listDescendingCommand = ListDescendingNameCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_ALIAS;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_WORD;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidDescendingListTest() {
        String listDescendingCommand = ListDescendingNameCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listDescendingCommand);
        listDescendingCommand = ListDescendingNameCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_WORD + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidReverseListTest() {
        String reverseCommand = ListReverseCommand.COMPILED_COMMAND;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(reverseCommand);
        reverseCommand = ListReverseCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_ALIAS;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_WORD;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidReverseListTest() {
        String reverseCommand = ListReverseCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(reverseCommand);
        reverseCommand = ListReverseCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_WORD + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }
    //@@author

    @Test
    public void getFilteredPersonListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPersonList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     * - the internal model manager data are same as those in the {@code expectedModel} <br>
     * - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
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
