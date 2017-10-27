package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS;
import static seedu.address.logic.commands.DeleteTagCommand.MESSAGE_INVALID_DELETE_TAG_NOT_FOUND;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getParcel;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PARCEL;

import java.util.Iterator;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagInternalErrorException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class DeleteTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void deleteTag() throws IllegalValueException {
        /* ---------------- Performing deleteTag operation while an unfiltered list is being shown ---------------- */

        /* Case: delete the first parcel in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();

        ReadOnlyParcel targetParcel = getParcel(expectedModel, INDEX_FIRST_PARCEL);

        Iterator<Tag> targetTags = targetParcel.getTags().iterator();
        Tag targetTag = null;

        if (targetTags.hasNext()) {
            targetTag = targetTags.next();
        }

        String command = "     " + DeleteTagCommand.COMMAND_WORD + "      " + targetTag.tagName + "       ";

        Tag deletedTag = removeTag(expectedModel, targetTag);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, deletedTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last parcel in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastParcelIndex = getLastIndex(modelBeforeDeletingLast);
        targetParcel = getParcel(modelBeforeDeletingLast, lastParcelIndex);

        targetTags = targetParcel.getTags().iterator();

        if (targetTags.hasNext()) {
            targetTag = targetTags.next();
        }

        assertCommandSuccess(targetTag);

        /* Case: undo deleting the previous tag in the list -> deleted tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo deleting the last parcel in the list -> last tag deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeTag(modelBeforeDeletingLast, targetTag);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------------------- Performing invalid deleteTag operation ----------------------------------- */

        /* Case: invalid arguments (tag not founds) -> rejected */
        expectedResultMessage = String.format(MESSAGE_INVALID_DELETE_TAG_NOT_FOUND,
                new Tag("relatives"));
        assertCommandFailure(DeleteTagCommand.COMMAND_WORD + " relatives",
                expectedResultMessage);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEtAG friends", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyParcel} at the specified {@code index} in {@code model}'s address book.
     * @return the removed parcel
     */
    private Tag removeTag(Model model, Tag targetTag) {
        try {
            model.deleteTag(targetTag);
        } catch (TagNotFoundException | TagInternalErrorException e) {
            throw new AssertionError("targetTag is retrieved from model.");
        }
        return targetTag;
    }

    /**
     * Deletes the tag at {@code toDelete} by creating a default {@code DeleteTagCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Tag toDelete) {
        Model expectedModel = getModel();
        Tag deletedTag = removeTag(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS, deletedTag);

        assertCommandSuccess(
                DeleteTagCommand.COMMAND_WORD + " " + toDelete.tagName, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

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
