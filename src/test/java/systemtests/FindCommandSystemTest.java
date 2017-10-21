package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_LESSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalLessons.CS2101_L1;
import static seedu.address.testutil.TypicalLessons.KEYWORD_MATCHING_MA1101R;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.MA1101R_T1;
import static seedu.address.testutil.TypicalLessons.MA1101R_T2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.lecturer.Lecturer;


public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        String command;
        Model expectedModel = getModel();
        /* Case: find multiple lessons in address book, command with leading spaces and trailing spaces
         * -> 2 lessons found

        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MA1101R + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, MA1101R_T1, MA1101R_T2); // Both mod code are "MA1101R"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: repeat previous find command where lesson list is displaying the lessons we are finding
         * -> 2 lessons found

        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MA1101R;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        */
        /* Case: find lesson where lesson list is not displaying the lesson we are finding -> 1 lesson found
        command = FindCommand.COMMAND_WORD + " CS2101";
        ModelHelper.setFilteredList(expectedModel, CS2101_L1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        */
        /* Case: find multiple lessons in address book, 1 keywords -> 2 lessons found
        command = FindCommand.COMMAND_WORD + " MA1101R";
        ModelHelper.setFilteredList(expectedModel, MA1101R_L1, MA1101R_T1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find multiple lessons in address book, 1 keyword in small letter -> 2 lessons found
        command = FindCommand.COMMAND_WORD + " ma1101r";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find multiple lessons in address book, 1 keyword in mixed letter -> 2 lessons found
        command = FindCommand.COMMAND_WORD + " ma1101R";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find multiple lessons in address book, 1 matching keyword and 2 non-matching keyword
         * -> 2 lessons found

        command = FindCommand.COMMAND_WORD + " MA1101R GEH1004 GET1020";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same lessons in address book after deleting 1 of them -> 1 lesson found
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getLessonList().contains(MA1101R_L1);
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MA1101R;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, MA1101R_L1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find lesson in address book, keyword is same as name but of different case -> 1 lesson found
        command = FindCommand.COMMAND_WORD + " MA1101R";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find lesson in address book, keyword is substring of name -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " MA";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find lesson in address book, name is substring of keyword -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " 1101";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
 */
        /* Case: find lesson not in address book -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " BA1105";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find phone number of lesson in address book -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " " + MA1101R_L1.getLocation().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find address of lesson in address book -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " " + MA1101R_L1.getClassType().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find email of lesson in address book -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " " + MA1101R_L1.getTimeSlot().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
                /* Case: find email of lesson in address book -> 0 lessons found
        command = FindCommand.COMMAND_WORD + " " + MA1101R_L1.getGroup().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find tags of lesson in address book -> 0 lessons found
        List<Lecturer> lecturers = new ArrayList<>(MA1101R_L1.getLecturers());
        command = FindCommand.COMMAND_WORD + " " + lecturers.get(0).lecturerName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: find while a lesson is selected -> selected card deselected
        showAllLessons();
        selectLesson(Index.fromOneBased(1));
        assert !getLessonListPanel().getHandleToSelectedCard().getCode().equals(MA1101R_L1.getCode().fullCodeName);
        command = FindCommand.COMMAND_WORD + " MA1101R";
        ModelHelper.setFilteredList(expectedModel, MA1101R_L1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();
*/
        /* Case: find lesson in empty address book -> 0 lessons found
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getLessonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MA1101R;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, MA1101R_L1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
*/
        /* Case: mixed case command word -> rejected */
        command = "FiNd MA1101R";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_LESSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_LESSONS_LISTED_OVERVIEW, expectedModel.getFilteredLessonList().size());

        executeCommand(command);
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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
