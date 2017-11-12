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
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Mark the first lesson in the list, command with leading spaces and trailing spaces -> marked */
        expectedModel = getModel();
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ReadOnlyLesson lessonToMark = expectedModel.getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased());
        expectedModel.bookmarkLesson(lessonToMark);
        expectedResultMessage = String.format(MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToMark);
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

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
        assertCommandExecuteSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */


        executeCommand(listModuleCommand);
        executeCommand(listLessonCommand);

        command = MarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: Mark the first lesson in the list again, duplicate mark -> rejected */
        command = "     " + MarkCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        assertCommandExecuteFailure(command, MESSAGE_DUPLICATE_LESSON_FAILURE);

        /* Case: invalid index (0) -> rejected */
        command = MarkCommand.COMMAND_WORD + " 0";
        assertCommandExecuteFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = MarkCommand.COMMAND_WORD + " -1";
        assertCommandExecuteFailure(command, MESSAGE_INVALID_MARK_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = MarkCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandExecuteFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        command = UnmarkCommand.COMMAND_WORD + " 1";
        executeCommand(command);

        /* Case: wrong listing type, list by module -> rejected */
        executeCommand(listModuleCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandExecuteFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

        /* Case: wrong listing type, list by location -> rejected */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);
        command = MarkCommand.COMMAND_WORD + " 1";
        assertCommandExecuteFailure(command, MarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE);

    }
}
