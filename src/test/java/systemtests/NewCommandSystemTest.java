package systemtests;

import static seedu.address.TestApp.SAVE_LOCATION_FOR_TESTING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.StringUtil.replaceBackslashes;
import static seedu.address.logic.commands.NewCommand.MESSAGE_ALREADY_EXISTS;
import static seedu.address.logic.commands.NewCommand.MESSAGE_CREATING;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.testutil.TestUtil.generateRandomSandboxDirectory;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.commands.NewCommand;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;
import seedu.address.storage.XmlRolodexStorage;

public class NewCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    @Test
    public void newFile() {
        /* Case: Create a new directory with different random valid filepath,
         * command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        String commandString = "         " + NewCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: Create a different random directory but with single constructor test
         * -> opened
         */
        newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        assertCommandSuccess(newFilePath);

        /* Case: try Creating a new file that already exists
         * -> fail. Remain on current file, prompts to use `open` command.
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = NewCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_ALREADY_EXISTS, newFilePath));

        /*Case: try Creating file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = NewCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
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
        assertCommandSuccess(NewCommand.COMMAND_WORD + " " + filePath, filePath);
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
        String expectedResultMessage = String.format(MESSAGE_CREATING, filePath);

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
