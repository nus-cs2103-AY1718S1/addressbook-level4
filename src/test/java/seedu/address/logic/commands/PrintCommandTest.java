package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PrintCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author arnollim
/**
 * Contains Testcases for  {@code PrintCommand}.
 */
public class PrintCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_print_success () {
        String fileName = "fileName";
        PrintCommand printCommand = prepareCommand(fileName);

        String expectedMessage = String.format(MESSAGE_SUCCESS, fileName);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(printCommand, model, expectedMessage, expectedModel);

    }


    private PrintCommand prepareCommand(String fileName) {
        PrintCommand printCommand = new PrintCommand(fileName);
        printCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return printCommand;
    }
}
