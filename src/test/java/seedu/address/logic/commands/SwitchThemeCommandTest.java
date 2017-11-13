package seedu.address.logic.commands;

// @@author itsdickson

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

public class SwitchThemeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexInvalidSwitch_throwsCommandException() throws Exception {
        SwitchThemeCommand switchThemeCommand = prepareCommand(INDEX_FIRST_PERSON);

        try {
            switchThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_SWITCH, e.getMessage());
        }
    }

    @Test
    public void execute_validIndexValidSwitch_success() throws Exception {
        SwitchThemeCommand switchThemeCommand = prepareCommand(INDEX_SECOND_PERSON);

        String expectedMessage = SwitchThemeCommand.MESSAGE_LIGHT_THEME_SUCCESS;
        CommandResult commandResult = switchThemeCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getThemesList().size() + 1);
        SwitchThemeCommand switchThemeCommand = prepareCommand(outOfBoundIndex);

        try {
            switchThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        SwitchThemeCommand switchThemeFirstCommand = new SwitchThemeCommand(INDEX_FIRST_PERSON);
        SwitchThemeCommand switchThemeSecondCommand = new SwitchThemeCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(switchThemeFirstCommand.equals(switchThemeFirstCommand));

        // same values -> returns true
        SwitchThemeCommand switchThemeFirstCommandCopy = new SwitchThemeCommand(INDEX_FIRST_PERSON);
        assertTrue(switchThemeFirstCommand.equals(switchThemeFirstCommandCopy));

        // different types -> returns false
        assertFalse(switchThemeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(switchThemeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(switchThemeFirstCommand.equals(switchThemeSecondCommand));
    }

    /**
     * Returns a {@code SwitchThemeCommand} with the parameter {@code index}.
     */
    private SwitchThemeCommand prepareCommand(Index index) {
        SwitchThemeCommand switchThemeCommand = new SwitchThemeCommand(index);
        switchThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return switchThemeCommand;
    }
}
// @@author
