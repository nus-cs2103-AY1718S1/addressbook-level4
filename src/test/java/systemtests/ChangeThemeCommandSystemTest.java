//@@author ChenXiaoman
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.theme.Theme;

public class ChangeThemeCommandSystemTest extends AddressBookSystemTest {
    private Model modelBrightTheme;
    private Model modelDarkTheme;

    @Test
    public void changeTheme() throws Exception {
        prepareExpectedModels();

        /* Case: change the theme to Dark -> them changed to dark */
        String changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + Theme.DARK_THEME;
        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, Theme.DARK_THEME);
        assertCommandSuccess(changeThemeCommand, modelDarkTheme, expectedMessage);

        /* Case: undo changing theme  -> theme changes back to bright */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBrightTheme, expectedResultMessage);

        /* Case: undo changing theme  -> theme changes back to dark again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelDarkTheme, expectedResultMessage);

        /* Case: change the theme to bright -> theme changed */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + Theme.BRIGHT_THEME;
        expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, Theme.BRIGHT_THEME);
        assertCommandSuccess(changeThemeCommand, modelBrightTheme, expectedMessage);

        /* Case: input theme name is invalid -> rejected */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + " " + "invalid_theme_name";
        assertCommandFailure(changeThemeCommand,
                String.format(ChangeThemeCommand.MESSAGE_INVALID_THEME_NAME, "invalid_theme_name"));

        /* Case: input theme name is null -> rejected */
        changeThemeCommand = ChangeThemeCommand.COMMAND_WORD + "  ";
        assertCommandFailure(changeThemeCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Generates different models with different themes
     */
    private void prepareExpectedModels() {

        modelBrightTheme = prepareExpectedModelGivenThemeName(Theme.BRIGHT_THEME);
        modelDarkTheme = prepareExpectedModelGivenThemeName(Theme.DARK_THEME);

    }

    /**
     * Generate models with different theme
     * @param theme
     * @return new model with given theme
     */
    private Model prepareExpectedModelGivenThemeName(String theme) {
        assert (Theme.isValidThemeName(theme));

        // Generate new user preference with given theme
        UserPrefs newUserPrefs = new UserPrefs();
        GuiSettings guiSettings = newUserPrefs.getGuiSettings();
        newUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                guiSettings.getFontSize(), theme);

        return new ModelManager(new AddressBook(getModel().getAddressBook()), newUserPrefs);
    }

}
