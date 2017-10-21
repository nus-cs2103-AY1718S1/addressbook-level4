package systemtests;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.LessonUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LECTURER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA1101R;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.KEYWORD_MATCHING_MA1101R;
import static seedu.address.testutil.TypicalLessons.TYPICAL_MA1101R;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();
        String command;
        Index index;


        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited


        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + CODE_DESC_MA1101R + "  "
                + CLASSTYPE_DESC_MA1101R + " " + VENUE_DESC_MA1101R + "  " + GROUP_DESC_MA1101R + " "
                + TIMESLOT_DESC_MA1101R + " " + LECTURER_DESC_MA1101R + " ";
        Lesson editedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);
*/
        /* Case: undo editing the last lesson in the list -> last lesson restored
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);
        */
        /* Case: redo editing the last lesson in the list -> last lesson edited again
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateLesson(
                getModel().getFilteredLessonList().get(INDEX_FIRST_LESSON.getZeroBased()), editedLesson);
        assertCommandSuccess(command, model, expectedResultMessage);
        */
        /* Case: edit a lesson with new values same as existing values -> edited
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101;
        assertCommandSuccess(command, index, TYPICAL_MA1101R);
        */

        /* Case: edit some fields -> edited
        index = INDEX_FIRST_LESSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + LECTURER_DESC_MA1101R;
        ReadOnlyLesson lessonToEdit = getModel().getFilteredLessonList().get(index.getZeroBased());
        editedLesson = new LessonBuilder(lessonToEdit).withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);
*/
        /* Case: clear tags -> cleared
        index = INDEX_FIRST_LESSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_LECTURER.getPrefix();
        editedLesson = new LessonBuilder(lessonToEdit).withLecturers().build();
        assertCommandSuccess(command, index, editedLesson);
*/
        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered lesson list, edit index within bounds of address book and lesson list -> edited
        showLessonsWithName(KEYWORD_MATCHING_MA1101R);
        index = INDEX_FIRST_LESSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredLessonList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + CODE_DESC_MA1101R;
        lessonToEdit = getModel().getFilteredLessonList().get(index.getZeroBased());
        editedLesson = new LessonBuilder(lessonToEdit).withCode(VALID_CODE_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);
*/
        /* Case: filtered lesson list, edit index within bounds of address book but out of bounds of lesson list
         * -> rejected*/

        showLessonsWithName(KEYWORD_MATCHING_MA1101R);
        int invalidIndex = getModel().getAddressBook().getLessonList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + CODE_DESC_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a lesson card is selected -------------------------- */

        /* Case: selects first card in the lesson list, edit a lesson -> edited, card selection remains unchanged but
         * browser url changes

        showAllLessons();
        index = INDEX_FIRST_LESSON;
        selectLesson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new lesson's name
        assertCommandSuccess(command, index, TYPICAL_MA1101R, index);
        */
        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + CODE_DESC_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected*/
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased(),
                Code.MESSAGE_CODE_CONSTRAINTS);

        /* Case: invalid name -> rejected*/
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CODE_DESC,
                Code.MESSAGE_CODE_CONSTRAINTS);


        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_VENUE_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS);

            /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_TIMESLOT_DESC,
                TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_LECTURER_DESC,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        /* Case: edit a lesson with new values same as another lesson's values -> rejected */
        executeCommand(LessonUtil.getAddCommand(TYPICAL_MA1101R));
        assertTrue(getModel().getAddressBook().getLessonList().contains(TYPICAL_MA1101R));
        index = INDEX_FIRST_LESSON;
        assertFalse(getModel().getFilteredLessonList().get(index.getZeroBased()).equals(TYPICAL_MA1101R));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LESSON);

        /* Case: edit a lesson with new values same as another lesson's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()  + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LESSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyLesson, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyLesson, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyLesson editedLesson) {
        assertCommandSuccess(command, toEdit, editedLesson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyLesson editedLesson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateLesson(
                    expectedModel.getFilteredLessonList().get(toEdit.getZeroBased()), editedLesson);
            expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
        //assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
