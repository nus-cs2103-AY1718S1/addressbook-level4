package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import org.junit.Test;

import seedu.address.TestApp;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.model.Model;

// @@author johnweikangong
public class ChangeThemeSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTheme() {
        String brightThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "BrightTheme.css").toString();
        String darkThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "DarkTheme.css").toString();
        String extensionsPath = TestApp.class.getResource(FXML_FILE_FOLDER + "Extensions.css").toString();

        final String expectedBrightThemeAllPaths = "[" + extensionsPath + ", " + brightThemePath + "]";
        final String expectedDarkThemeAllPaths = "[" + extensionsPath + ", " + darkThemePath + "]";

        /* Case: Change theme of non-empty address book using command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_WORD, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("   " + ThemeCommand.COMMAND_WORD + " 434@$   ", expectedBrightThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_ALIAS, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("  " + ThemeCommand.COMMAND_ALIAS + " 2#2 ", expectedBrightThemeAllPaths);

        /* Case: Mixed case command word -> rejected */
        assertCommandFailure("ChaNgEtHeme", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case command alias -> rejected */
        assertCommandFailure("Ct", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths) {
        assertCommandSuccess(command, expectedThemeAllPaths, ThemeCommand.MESSAGE_SUCCESS, getModel());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeThemeSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths,
                                      String expectedResultMessage, Model expectedModel) {
        assertThemeBeforeChangingNotSame(expectedThemeAllPaths);
        executeCommand(command);
        assertThemeAfterChangingSame(expectedThemeAllPaths);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
