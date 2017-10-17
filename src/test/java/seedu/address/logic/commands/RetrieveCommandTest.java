package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.RetrieveCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code RetrieveCommand}.
 */
public class RetrieveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        assertCommandFailure(prepareCommand("friends"), model, MESSAGE_SUCCESS);
    }

    @Test
    public void equals() {
        final RetrieveCommand command = new RetrieveCommand("friends");

        // same value -> returns true
        assertTrue(command.equals(new RetrieveCommand("friends")));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different tag name -> returns false
        assertFalse(command.equals(new RetrieveCommand("family")));

    }

    /**
     * Parses {@code userInput} into a {@code RetrieveCommand}.
     */
    private RetrieveCommand prepareCommand(String userInput) {
        RetrieveCommand command = new RetrieveCommand(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
