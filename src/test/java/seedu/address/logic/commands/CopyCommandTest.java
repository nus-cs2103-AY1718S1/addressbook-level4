package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author rushan-khor
public class CopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void testGetTargetEmail() {
        CopyCommand command = prepareCommand(INDEX_FIRST_PERSON);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String result = "";

        try {
            result = command.getTargetEmail();
        } catch (CommandException e) {
            fail();
        }

        assertTrue(result.equals("alice@example.com"));
    }

    /**
     * Returns a {@code CopyCommand} with the parameter {@code index}.
     */
    private CopyCommand prepareCommand(Index index) {
        CopyCommand copyCommand = new CopyCommand(index);
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }
}

