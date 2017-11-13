//@@author majunting
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LocateCommand}.
 */
public class LocateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String startAddress = "NUS";

    @Test
    public void equals() {
        Index first = INDEX_FIRST_PERSON;
        Index second = INDEX_SECOND_PERSON;

        LocateCommand locateFirstCommand = new LocateCommand(first, startAddress);
        LocateCommand locateSecondCommand = new LocateCommand(second, startAddress);

        assertTrue(locateFirstCommand.equals(locateFirstCommand));

        LocateCommand locateFirstCommandCopy = new LocateCommand(first, startAddress);
        assertTrue(locateFirstCommand.equals(locateFirstCommandCopy));

        assertFalse(locateFirstCommand.equals(1));
        assertFalse(locateFirstCommand.equals(null));
        assertFalse(locateFirstCommand.equals(locateSecondCommand));
    }

    @Test
    public void validIndexInput_executionSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        LocateCommand command = new LocateCommand(lastPersonIndex, startAddress);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        try {
            assertEquals(String.format(LocateCommand.MESSAGE_SUCCESS, lastPersonIndex.getOneBased()),
                    command.execute().feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("command exception");
        }
    }

    @Test
    public void invalidIndexInput_executionFail() {
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocateCommand command = new LocateCommand(invalidIndex, startAddress);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        try {
            command.execute();
        } catch (CommandException ce) {
            assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ce.getMessage());
        }
    }
}
