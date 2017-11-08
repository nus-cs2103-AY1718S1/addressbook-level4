//@@author qihao27
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXPORTED;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_EXPORT_TEST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_exportSuccess_throwsParseException() throws ParseException {
        ExportCommand command = prepareCommand(FILE_PATH_EXPORT_TEST);
        assertCommandSuccess(command, MESSAGE_FILE_EXPORTED + FILE_PATH_EXPORT_TEST);
    }

    /**
     * Parses {@code userInput} into a {@code LockCommand} in default mode.
     */
    private ExportCommand prepareCommand(String userInput) throws ParseException {
        ExportCommand command = new ExportCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(ExportCommand command, String expectedMessage) {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
