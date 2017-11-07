package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeProfilePictureCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalProfilePicture;

public class ChangeProfilePictureCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select() {
        /* Case: change profile picture of the first card in the person list,
         * command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + "   " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: change profile picture of the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + personCount.getOneBased()
                + " " + TypicalProfilePicture.SECOND_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: change profile picture of the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + middleIndex.getOneBased()
                + " " + TypicalProfilePicture.THIRD_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + invalidIndex
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: change profile picture of the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: filtered person list, change profile picture of index within bounds of
         * address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + invalidIndex
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, change profile picture of index within bounds of address book
         * and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredPersonList().size();
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + validIndex.getOneBased()
                + " " + TypicalProfilePicture.FIFTH_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + 0
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + -1
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + "abc"
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: file not found -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + TypicalProfilePicture.FILE_NOT_FOUND_PATH,
                MESSAGE_FILE_NOT_FOUND);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ChAngEpIC 1 D:/picture.png", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                ChangeProfilePictureCommand.MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS,
                expectedSelectedCardIndex.getOneBased());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
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
