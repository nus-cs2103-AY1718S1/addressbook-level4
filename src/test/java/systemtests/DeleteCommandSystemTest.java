package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastModuleIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessonComponents.CS2101;
import static seedu.address.testutil.TypicalLessonComponents.GEQ1000;
import static seedu.address.testutil.TypicalLessonComponents.MA1101R;
import static seedu.address.testutil.TypicalLessons.KEYWORD_MATCHING_MA1101R;

import java.util.ArrayList;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.LessonNotFoundException;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first module in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_LESSON.getOneBased() + "       ";
        ArrayList<ReadOnlyLesson> lessonList = removeModule(expectedModel, MA1101R);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, MA1101R.fullCodeName);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: delete the last module in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Model modelNotDeleteYet = getModel(); //noDelete is for Undo
        ReadOnlyAddressBook addressBook = getModel().getAddressBook();
        command = DeleteCommand.COMMAND_WORD + " " + getLastModuleIndex(modelBeforeDeletingLast).getOneBased();
        lessonList = removeModule(modelBeforeDeletingLast, CS2101);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, CS2101.fullCodeName);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* Case: undo deleting the last lesson in the list -> last lesson restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);

        //        /* Case: redo deleting the last lesson in the list -> last lesson deleted again */
        //        command = RedoCommand.COMMAND_WORD;
        //        lessonList = removeModule(modelNotDeleteYet, CS2101);
        //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        //        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);

        /* Case: delete the middle lesson in the list -> deleted */
        addressBook = getModel().getAddressBook();
        command = DeleteCommand.COMMAND_WORD + " " + getLastModuleIndex(modelBeforeDeletingLast).getOneBased();
        lessonList = removeModule(modelNotDeleteYet, GEQ1000);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, GEQ1000.fullCodeName);
        assertCommandSuccess(command, modelNotDeleteYet, expectedResultMessage);


        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        executeCommand("undo");
        executeCommand("undo");

        /* Case: filtered lesson list, delete index within bounds of address book and lesson list -> deleted */
        showLessonsWithName(KEYWORD_MATCHING_MA1101R);
        Index index = INDEX_FIRST_LESSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredLessonList().size());
        assertCommandSuccess(index);

        /* Case: filtered lesson list, delete index within bounds of address book but out of bounds of lesson list
         * -> rejected
         */
        showLessonsWithName(KEYWORD_MATCHING_MA1101R);
        int invalidIndex = getModel().getAddressBook().getLessonList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a lesson card is selected ------------------------ */

        /* Case: delete the selected lesson -> lesson list panel selects the lesson before the deleted lesson */
        showAllLessons();
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        expectedModel = getModel();
        Index selectedIndex = getLastModuleIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        //selectLesson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        lessonList = removeModule(expectedModel, CS2101);
        expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, CS2101.fullCodeName);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);



        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getLessonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }


    /**
     * Removes the {@code ReadOnlyLesson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed lesson
     */
    private ArrayList<ReadOnlyLesson> removeModule(Model model, Code code) {
        ArrayList<ReadOnlyLesson> targetLessons = new ArrayList<ReadOnlyLesson>();
        ObservableList<ReadOnlyLesson> lessonList = model.getAddressBook().getLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson lesson = lessonList.get(i);
            if (lesson.getCode().equals(code)) {
                try {
                    targetLessons.add(lesson);
                    model.deleteLesson(lesson);
                } catch (LessonNotFoundException e) {
                    e.printStackTrace();
                    throw new AssertionError("targetModule is retrieved from model.");
                }
                i--;
            }
        }
        return targetLessons;
    }


    /**
     * Removes the {@code ReadOnlyLesson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed lesson
     */
    private ReadOnlyLesson removeLesson(Model model, Index index) {
        ObservableList<ReadOnlyLesson> lessonList = model.getAddressBook().getLessonList();
        ReadOnlyLesson targetLesson = lessonList.get(index.getZeroBased());

        try {
            model.deleteLesson(targetLesson);
        } catch (LessonNotFoundException e) {
            e.printStackTrace();
            throw new AssertionError("targetLesson is retrieved from model.");
        }
        return targetLesson;
    }

    /**
     * Deletes the lesson at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        ReadOnlyLesson deletedLesson = removeLesson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LESSON_SUCCESS, deletedLesson);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
