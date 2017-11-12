package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CLASSTYPE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.CODE_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLASSTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LECTURER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.MA1101R_CODE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1102R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1102R;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA1101R;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.TYPICAL_CS2101;
import static seedu.address.testutil.TypicalLessons.TYPICAL_MA1101R;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.LessonUtil;

//@@author junming403
public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void editLesson() throws Exception {
        /* ----------------- Performing edit operation while an lesson list is being shown ---------------------- */

        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /*
         * View all lessons of the module indexed with 1.
         */
        String listLessonCommand = ViewCommand.COMMAND_WORD + " 1";
        executeCommand(listLessonCommand);
        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + CODE_DESC_MA1101R + "  "
                + CLASSTYPE_DESC_MA1101R + " " + VENUE_DESC_MA1101R + "  " + GROUP_DESC_MA1101R + " "
                + TIMESLOT_DESC_MA1101R + " " + LECTURER_DESC_MA1101R + " ";
        Lesson editedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);

        /* Case: edit a lesson with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, index, TYPICAL_MA1101R);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_LESSON;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + LECTURER_DESC_MA1101R;
        ReadOnlyLesson lessonToEdit = getModel().getFilteredLessonList().get(index.getZeroBased());
        editedLesson = new LessonBuilder(lessonToEdit).withLecturers(VALID_LECTURER_MA1101R).build();
        assertCommandSuccess(command, index, editedLesson);

        /* --------------------- Performing edit operation while a lesson card is selected -------------------------- */

        /* Case: selects first card in the lesson list, edit a lesson -> edited, card selection remains unchanged but
         * browser url changes
         */
        index = INDEX_FIRST_LESSON;
        selectLesson(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R;
        // this can be misleading: card selection actually remains unchanged.
        assertEditLessonSuccess(command, index, TYPICAL_MA1101R, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + CODE_DESC_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + CODE_DESC_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LESSON));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid module code -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CODE_DESC,
                Code.MESSAGE_CODE_CONSTRAINTS);

        /* Case: invalid class type -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_CLASSTYPE_DESC,
                ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        /* Case: invalid location -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_VENUE_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid group number -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_GROUP_DESC,
                Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: invalid time slot -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_TIMESLOT_DESC,
                TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        /* Case: invalid lecturer -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + INVALID_LECTURER_DESC,
                Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        /* Case: edit a lesson with new values same as another lesson's values -> rejected */
        executeCommand(LessonUtil.getAddCommand(TYPICAL_CS2101));
        assertTrue(getModel().getAddressBook().getLessonList().contains(TYPICAL_CS2101));
        index = INDEX_FIRST_LESSON;
        assertFalse(getModel().getFilteredLessonList().get(index.getZeroBased()).equals(TYPICAL_CS2101));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + CODE_DESC_CS2101 + CLASSTYPE_DESC_CS2101
                + VENUE_DESC_CS2101 +  TIMESLOT_DESC_CS2101 +  GROUP_DESC_CS2101 + LECTURER_DESC_CS2101;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: edit a lesson with new values same as another lesson's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased()  + CODE_DESC_CS2101 + CLASSTYPE_DESC_CS2101
                + VENUE_DESC_CS2101 + TIMESLOT_DESC_CS2101 + GROUP_DESC_CS2101 + LECTURER_DESC_CS2101;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);
    }

    /* ----------------- Performing edit operation while a module list is being shown ---------------------- */
    @Test
    public void editModule() throws Exception {
        /* ----------------- Performing edit operation while an module list is being shown ---------------------- */
        /* list by module */
        String listModuleCommand = ListCommand.COMMAND_WORD + " module";
        executeCommand(listModuleCommand);

        /* Case: edit module code, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + " 1 " + VALID_CODE_MA1102R;
        assertCommandSuccess(command, index, VALID_CODE_MA1102R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_CODE_MA1101R;
        assertCommandSuccess(command, index, VALID_CODE_MA1101R);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + VALID_CODE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_MODULE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1 " + VALID_CODE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_MODULE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_CODE_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid module code -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + " MA*",
                Code.MESSAGE_CODE_CONSTRAINTS);
    }

    /* ----------------- Performing edit operation while a module list is being shown ---------------------- */
    @Test
    public void editLocation() throws Exception {
        /* ----------------- Performing edit operation while an location list is being shown ---------------------- */
        /* list by location */
        String listLocationCommand = ListCommand.COMMAND_WORD + " location";
        executeCommand(listLocationCommand);

        /* Case: edit location, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LESSON;
        String command = " " + EditCommand.COMMAND_WORD + " 1 " + VALID_VENUE_MA1102R;
        assertCommandSuccess(command, index, VALID_VENUE_MA1102R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_VENUE_MA1101R;
        assertCommandSuccess(command, index, VALID_VENUE_MA1101R);

        command = " " + EditCommand.COMMAND_WORD + "    1     " + VALID_VENUE_CS2101;
        assertCommandSuccess(command, index, VALID_VENUE_CS2101);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + VALID_VENUE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LOCATION));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1 " + VALID_VENUE_MA1101R,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE_LOCATION));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + " " + VALID_VENUE_MA1101R,
                Messages.MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid location. -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + " ",
                Location.MESSAGE_LOCATION_CONSTRAINTS);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyLesson, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertEditLessonSuccess(String, Index, ReadOnlyLesson, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyLesson editedLesson)
            throws DuplicateBookedSlotException {
        assertEditLessonSuccess(command, toEdit, editedLesson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, String, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertEditModuleSuccess(String, Index, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, String attributeValue)
            throws DuplicateBookedSlotException {
        switch (ListingUnit.getCurrentListingUnit()) {
        case MODULE:
            assertEditModuleSuccess(command, toEdit, attributeValue, null);
            break;
        case LOCATION:
            assertEditLocationSuccess(command, toEdit, attributeValue, null);
            break;
        default:
            break;
        }
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
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
        assertStatusBarUnchangedExceptSyncStatus();
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertEditModuleSuccess(String command, Index toEdit, String attributeValue,
                                      Index expectedSelectedCardIndex) throws DuplicateBookedSlotException {
        Model expectedModel = getModel();
        Code codeToEdit = expectedModel.getFilteredLessonList().get(toEdit.getZeroBased()).getCode();

        try {
            Code editedCode = new Code(attributeValue);
            expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
            ObservableList<ReadOnlyLesson> lessonList = expectedModel.getFilteredLessonList();
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getCode().equals(codeToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), p.getLocation(), p.getGroup(),
                            p.getTimeSlot(), editedCode, p.getLecturers());
                    expectedModel.updateLesson(p, curEditedLesson);
                }
            }
            expectedModel.updateFilteredLessonList(new UniqueModuleCodePredicate(expectedModel.getUniqueCodeSet()));
            assertCommandSuccess(command, expectedModel,
                    String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedCode), expectedSelectedCardIndex);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(
                    "edited module code is not valid");
        }
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertEditLocationSuccess(String command, Index toEdit, String attributeValue,
                                         Index expectedSelectedCardIndex) throws DuplicateBookedSlotException {
        Model expectedModel = getModel();
        Location locationToEdit = expectedModel.getFilteredLessonList().get(toEdit.getZeroBased()).getLocation();

        try {
            Location editedLocation = new Location(attributeValue);
            expectedModel.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
            ObservableList<ReadOnlyLesson> lessonList = expectedModel.getFilteredLessonList();
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getLocation().equals(locationToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), editedLocation, p.getGroup(),
                            p.getTimeSlot(), p.getCode(), p.getLecturers());
                    BookedSlot bookedSlotToEdit = new BookedSlot(p.getLocation(), p.getTimeSlot());
                    BookedSlot editedBookedSlot = new BookedSlot(editedLocation, p.getTimeSlot());
                    expectedModel.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
                    expectedModel.updateLesson(p, curEditedLesson);
                }
            }
            expectedModel.updateFilteredLessonList(new UniqueLocationPredicate(expectedModel.getUniqueLocationSet()));
            assertCommandSuccess(command, expectedModel,
                    String.format(EditCommand.MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation),
                    expectedSelectedCardIndex);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(
                    "edited module code is not valid");
        }
    }
    //@@author

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the lesson at index {@code toEdit} being
     * updated to values specified {@code editedLesson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertEditLessonSuccess(String command, Index toEdit, ReadOnlyLesson editedLesson,
            Index expectedSelectedCardIndex) throws DuplicateBookedSlotException {
        Model expectedModel = getModel();
        ReadOnlyLesson lessonToEdit = expectedModel.getFilteredLessonList().get(toEdit.getZeroBased());
        BookedSlot bookedSlotToEdit = new BookedSlot(lessonToEdit.getLocation(), lessonToEdit.getTimeSlot());
        BookedSlot editedBookedSlot = new BookedSlot(editedLesson.getLocation(), editedLesson.getTimeSlot());
        try {
            expectedModel.updateLesson(lessonToEdit, editedLesson);
            expectedModel.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
            expectedModel.updateFilteredLessonList(MA1101R_CODE_PREDICATE);
        } catch (DuplicateLessonException | LessonNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedLesson is a duplicate in expectedModel, or it isn't found in the model.");
        }
        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson), expectedSelectedCardIndex);
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
