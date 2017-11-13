package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ThemeCommand.MESSAGE_THEME_CHANGE_SUCCESS;
import static seedu.address.logic.commands.ThemeCommand.MESSAGE_THEME_IN_USE;
import static seedu.address.logic.commands.ThemeCommand.MESSAGE_THEME_NOT_AVAILABLE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ThemeCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ThemeCommand lightThemeCommand = new ThemeCommand("light");
        ThemeCommand darkThemeCommand = new ThemeCommand("Dark");

        // same object -> returns true
        assertTrue(lightThemeCommand.equals(lightThemeCommand));

        // same values -> returns true
        ThemeCommand lightThemeCommandCopy = new ThemeCommand("light");
        assertTrue(lightThemeCommand.equals(lightThemeCommandCopy));

        // different types -> returns false
        assertFalse(lightThemeCommand.equals(1));

        // null -> returns false
        assertFalse(lightThemeCommand.equals(null));

        // different theme -> returns false
        assertFalse(lightThemeCommand.equals(darkThemeCommand));
    }

    @Test
    public void execute_correctTheme_success() throws CommandException {
        model.setTheme(model.getStyleMap().get("Dark"));
        ThemeCommand command = prepareCommand("light");
        assertCommandSuccess(command, String.format(MESSAGE_THEME_CHANGE_SUCCESS, "light"));

        model.setTheme(model.getStyleMap().get("light"));
        ThemeCommand darkCommand = prepareCommand("dark");
        assertCommandSuccess(darkCommand, String.format(MESSAGE_THEME_CHANGE_SUCCESS, "dark"));

    }

    @Test
    public void execute_wrongArguments_failure() {
        ThemeCommand command = prepareCommand("notATheme");
        assertCommandFailure(command, model, String.format(MESSAGE_THEME_NOT_AVAILABLE, "notATheme"));
    }

    @Test
    public void execute_themeAlreadyLoaded_failure() {
        model.setTheme(model.getStyleMap().get("Dark"));
        ThemeCommand command = prepareCommand("dark");
        assertCommandFailure(command, model, String.format(MESSAGE_THEME_IN_USE, "dark"));

        model.setTheme(model.getStyleMap().get("light"));
        ThemeCommand lightCommand = prepareCommand("light");
        assertCommandFailure(lightCommand, model, String.format(MESSAGE_THEME_IN_USE, "light"));
    }



    /**
     * Parses {@code userInput} into a {@code ThemeCommand}.
     */
    private ThemeCommand prepareCommand(String userInput) {
        ThemeCommand command = new ThemeCommand(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed
     */
    private void assertCommandSuccess(ThemeCommand command, String expectedMessage) throws CommandException {
        AddressBook ab = new AddressBook();
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(ab.getStyleMap().get(command.toString()), model.getTheme());
        assertEquals(ab.getStyleMap().get(command.toString()), model.getUserPrefs().getTheme());
    }
}
