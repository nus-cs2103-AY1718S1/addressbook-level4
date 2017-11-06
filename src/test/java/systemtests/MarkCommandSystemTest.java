package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.logic.commands.MarkCommand.MESSAGE_BOOKMARK_LESSON_SUCCESS;
import static seedu.address.logic.commands.UnmarkCommand.MESSAGE_UNBOOKMARK_LESSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.Model;
import seedu.address.model.module.ReadOnlyLesson;

//@@author junming403
public class MarkCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_DUPLICATE_LESSON_FAILURE = "Operation would result in duplicate lesson";
    private static final String MESSAGE_INVALID_MARK_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);

    @Test
    public void mark() throws Exception {

        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /*
         * View all lessons of the module indexed with 1.
         */
        String listLessonCommand = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(listLessonCommand);

        /* Case: Unmark the first lesson in the list, command with leading spaces and trailing spaces -> unmarked */
        Model expectedModel = getModel();
        String command = "     " + UnmarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ReadOnlyLesson lessonToUnmark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.unBookmarkLesson(lessonToUnmark);
        String expectedResultMessage = String.format(MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Mark the first lesson in the list, command with leading spaces and trailing spaces -> marked */
        expectedModel = getModel();
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ReadOnlyLesson lessonToMark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.bookmarkLesson(lessonToMark);
        expectedResultMessage = String.format(MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Unmark the first lesson in the marked list,
         * command with leading spaces and trailing spaces -> unmarked
         */
        /* list marked lessons */
        String listMarkedCommand = ListCommand.COMMAND_WORD + " marked";
        executeCommand(listMarkedCommand);

        expectedModel = getModel();
        command = "     " + UnmarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        lessonToUnmark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.unBookmarkLesson(lessonToUnmark);
        expectedResultMessage = String.format(MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnmark);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */


        executeCommand(listModuleCommand);
        executeCommand(listLessonCommand);

        command = MarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: Mark the first lesson in the list again, duplicate mark -> rejected */
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        assertCommandFailure(command, MESSAGE_DUPLICATE_LESSON_FAILURE);

        /* Case: invalid index (0) -> rejected */
        command = MarkCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = MarkCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = MarkCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        command = UnmarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: wrong listing type, list by module -> rejected */
        executeCommand(listModuleCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: wrong listing type, list by location -> rejected */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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

}
