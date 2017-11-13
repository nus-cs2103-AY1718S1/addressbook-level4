//@@author ChenXiaoman
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_NAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.theme.Theme.DARK_THEME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.theme.Theme;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeThemeCommand}.
 */
public class ChangeThemeCommandTest {

    private UserPrefs userPrefs;
    private Model model;
    private GuiSettings guiSettings;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        userPrefs = new UserPrefs();
        guiSettings = userPrefs.getGuiSettings();
    }

    @Test
    public void executeChangeThemeCommandSuccess() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand(DARK_THEME);

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, DARK_THEME);

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                guiSettings.getFontSize(), DARK_THEME);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), expectedUserPrefs);
        assertCommandSuccess(changeThemeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeThemeCommandInvalidThemeNameFailure() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand(INVALID_THEME_NAME);

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_INVALID_THEME_NAME, INVALID_THEME_NAME);

        assertCommandFailure(changeThemeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeThemeCommand themeFirstCommand = new ChangeThemeCommand(Theme.DARK_THEME);
        ChangeThemeCommand themeSecondCommand = new ChangeThemeCommand(Theme.BRIGHT_THEME);

        // same object -> returns true
        assertTrue(themeFirstCommand.equals(themeFirstCommand));

        // same values -> returns true
        ChangeThemeCommand themeFirstCommandCopy = new ChangeThemeCommand(Theme.DARK_THEME);
        assertTrue(themeFirstCommand.equals(themeFirstCommandCopy));

        // different types -> returns false
        assertFalse(themeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(themeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(themeFirstCommand.equals(themeSecondCommand));
    }

    /**
     * Returns an {@code ChangeThemeCommand} with parameters {@code theme}
     */
    private ChangeThemeCommand prepareCommand(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        changeThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeThemeCommand;
    }

}
