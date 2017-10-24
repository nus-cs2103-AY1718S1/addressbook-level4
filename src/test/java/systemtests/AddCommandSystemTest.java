package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import static seedu.address.logic.commands.CommandTestUtil.LECTURER_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSTYPE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LECTURER_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA1101R;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA1101R;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.testutil.TypicalLessons.TYPICAL_MA1101R;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.LessonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a lesson without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyLesson toAdd = TYPICAL_MA1101R;
        String command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R  + GROUP_DESC_MA1101R  + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addLesson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate lesson -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R  + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R  + GROUP_DESC_MA1101R  + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a duplicate lesson except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalLessons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addLesson(ReadOnlyLesson)
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + LECTURER_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + " " + PREFIX_LECTURER.getPrefix() + "Dr Wong";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Code -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_CS2101).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_CS2101 + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except ClassType -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_CS2101)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_CS2101 + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Locaiton -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_CS2101
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: add a lesson with all fields same as another lesson in the address book except Group -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_CS2101).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_CS2101 + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOKEDSLOT);

        /* Case: add a lesson with all fields same as another lesson in the address book except Time slot -> added */
        toAdd = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_CS2101)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_CS2101 + LECTURER_DESC_MA1101R;
        assertCommandSuccess(command, toAdd);

        /* Case: missing code -> rejected */
        command = AddCommand.COMMAND_WORD + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing class type -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing venue -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing group -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing time slot -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + LessonUtil.getLessonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid code -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_CODE_DESC + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Code.MESSAGE_CODE_CONSTRAINTS);

        /* Case: invalid class type -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + INVALID_CLASSTYPE_DESC + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        /* Case: invalid venue -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + INVALID_VENUE_DESC
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid group -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + INVALID_GROUP_DESC + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, Group.MESSAGE_GROUP_CONSTRAINTS);

        /* Case: invalid time slot -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC + LECTURER_DESC_MA1101R;
        assertCommandFailure(command, TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        /* Case: invalid lecturers -> rejected */
        command = AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R
                + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R + INVALID_LECTURER_DESC;
        assertCommandFailure(command, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyLesson toAdd) {
        assertCommandSuccess(LessonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyLesson)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyLesson)
     */
    private void assertCommandSuccess(String command, ReadOnlyLesson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addLesson(toAdd);
        } catch (DuplicateLessonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyLesson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyLesson)
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
