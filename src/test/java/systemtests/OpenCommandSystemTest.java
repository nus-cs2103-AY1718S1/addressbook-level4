package systemtests;

import static seedu.address.TestApp.SAVE_LOCATION_FOR_TESTING;
import static seedu.address.TestApp.SECONDARY_SAVE_LOCATION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.StringUtil.replaceBackslashes;
import static seedu.address.logic.commands.OpenCommand.MESSAGE_NOT_EXIST;
import static seedu.address.logic.commands.OpenCommand.MESSAGE_OPENING;
import static seedu.address.testutil.TestUtil.getFilePathInSandboxFolder;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.storage.XmlRolodexStorage;

public class OpenCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    private static final String DIRECTORY_VALID_DIFFERENT = replaceBackslashes(SECONDARY_SAVE_LOCATION);

    @Test
    public void open() {
        /* Case: open a new directory with different valid filepath, command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = DIRECTORY_VALID_DIFFERENT;
        String commandString = "         " + OpenCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back original directory with valid filepath, normal command
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back different directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DIFFERENT;
        assertCommandSuccess(newFilePath);

        /* Case: open back default directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        assertCommandSuccess(newFilePath);

        /* Case: try opening file that does not exist
         * -> fail. Remain on current file, prompts to use `new` command.
         */
        newFilePath = replaceBackslashes(getFilePathInSandboxFolder("notFound.rldx"));
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_NOT_EXIST, newFilePath));

        /*Case: try opening file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }

    @After
    public void resetData() {
        /* End of test: Open original test file
         * -> opened
         * Otherwise, go to test/data/sandbox/pref_testing.json
         * and manually change the rolodexFilePath to the correct path
         */
        UserPrefs userPrefs = getUserPrefs();
        userPrefs.setRolodexFilePath(replaceBackslashes(SAVE_LOCATION_FOR_TESTING));
        Storage storage = getStorage();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code OpenCommand#MESSAGE_OPENING} with the valid filePath, and the model related components
     * equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar save directory changes to the new rolodex location, the command box has the
     * default style class, and the selected card updated accordingly, depending on {@code cardStatus}.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandSuccess(String filePath) {
        assertCommandSuccess(OpenCommand.COMMAND_WORD + " " + filePath, filePath);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)}. Executes {@code command}
     * instead.
     * @see OpenCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String filePath) {
        getStorage().setNewRolodexStorage(new XmlRolodexStorage(filePath));
        assertCommandSuccess(command, filePath, getStorage());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see OpenCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String filePath, Storage expectedStorage) {
        String expectedResultMessage = String.format(MESSAGE_OPENING, filePath);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedStorage);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarDirectoryChanged(filePath);
        assertUndoRedoStackCleared();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
