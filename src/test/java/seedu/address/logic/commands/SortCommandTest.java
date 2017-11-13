//@@author siri99
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    final String invalidSortFilter = "random";
    final String sortFilterName = "name";
    final String sortFilterBirthday = "birthday";
    final String sortFilterBirthdayAlias = "b";
    final String sortFilterScore = "score";
    final String sortFilterDefault = "";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {

        // Default filter - sort by name
        assertCommandSuccess(prepareCommand(sortFilterDefault), model, SortCommand.MESSAGE_SUCCESS_NAME, model);

        // Sort in alphabhetical order of name
        assertCommandSuccess(prepareCommand(sortFilterName), model, SortCommand.MESSAGE_SUCCESS_NAME, model);

        // Sort in ascending order of birthdays
        assertCommandSuccess(prepareCommand(sortFilterBirthday), model, SortCommand.MESSAGE_SUCCESS_BIRTHDAY, model);

        // Sort in ascending order of birthdays (with shortcut 'b')
        assertCommandSuccess(prepareCommand(sortFilterBirthdayAlias), model, SortCommand.MESSAGE_SUCCESS_BIRTHDAY,
                model);

        // Sort in ascending order of birthdays (with shortcut 'b')
        assertCommandSuccess(prepareCommand(sortFilterScore), model, SortCommand.MESSAGE_SUCCESS_SCORE, model);
    }

    @Test
    public void equals() {

        final SortCommand sortNameCommand = new SortCommand(sortFilterName);
        final SortCommand sortBirthdayCommand = new SortCommand(sortFilterBirthday);
        final SortCommand sortBirthdayAliasCommand = new SortCommand(sortFilterBirthdayAlias);
        final SortCommand sortScoreCommand = new SortCommand(sortFilterScore);
        final SortCommand sortDefaultCommand = new SortCommand(sortFilterDefault);

        SortCommand commandExecuted;

        // same filterTypes (Name) -> returns true
        commandExecuted = new SortCommand(sortFilterName);
        assertTrue(sortNameCommand.equals(commandExecuted));

        // same filterTypes (Birthday) -> returns true
        commandExecuted = new SortCommand(sortFilterBirthday);
        assertTrue(sortBirthdayCommand.equals(commandExecuted));

        // same filterTypes (Birthday shortcut) -> returns true
        commandExecuted = new SortCommand(sortFilterBirthdayAlias);
        assertTrue(sortBirthdayAliasCommand.equals(commandExecuted));

        // same filterTypes (Score) -> returns true
        commandExecuted = new SortCommand(sortFilterScore);
        assertTrue(sortScoreCommand.equals(commandExecuted));

        // same filterTypes (No filter) -> returns true
        commandExecuted = new SortCommand(sortFilterDefault);
        assertTrue(sortDefaultCommand.equals(commandExecuted));

        // same object -> returns true
        assertTrue(sortBirthdayCommand.equals(sortBirthdayCommand));

        // null object-> returns false
        assertFalse(sortNameCommand.equals(null));

        // different filterTypes -> returns false
        assertFalse(sortNameCommand.equals(new SortCommand(invalidSortFilter)));

        // different command types -> returns false
        assertFalse(sortDefaultCommand.equals(new UndoCommand()));

    }

    /**
     * Returns a {@code SortCommand} with parameteres.
     */
    private SortCommand prepareCommand(String sortFilter) {
        SortCommand sortCommand = new SortCommand(sortFilter);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
//@@author siri99
