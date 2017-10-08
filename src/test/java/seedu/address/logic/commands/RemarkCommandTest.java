package seedu.address.logic.commands;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.logic.commands.RemarkCommand.MESSAGE_WORK_IN_PROGRESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;


public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndoableCommand() throws Exception{
        assertCommandFailure(getRemarkCommandForPerson(),model,MESSAGE_WORK_IN_PROGRESS);
    }

    /**
     * Generates a new RemarkCommand with the remarks of the given person.
     */
    private RemarkCommand getRemarkCommandForPerson() {
        RemarkCommand command = new RemarkCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
