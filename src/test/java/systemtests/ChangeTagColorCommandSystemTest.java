//@@author ChenXiaoman
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ChangeTagColorCommand.MESSAGE_CHANGE_TAG_COLOR_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAGLIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAGLIST_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAGLIST_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAGLIST_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_NAME_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_NAME_YELLOW;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

public class ChangeTagColorCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void changeTagColor() throws Exception {
        Model model = getModel();

        /* Case: change the tag color of one tag to one color -> tag color changed */
        String changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends c/red";
        String expectedMessage = String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, VALID_TAGLIST_1,
                VALID_TAG_COLOR_NAME_RED);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST_1, new TagColor(VALID_TAG_COLOR_NAME_RED));
        assertCommandSuccess(changeTagColorCommand, expectedModel, expectedMessage);

        /* Case: undo changing tag color  -> tag color changes back to default orange */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, getModel(), expectedResultMessage);

        /* Case: undo changing tag color  -> tag color changes back to red again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: change the tag color of more than one tag to one color -> tag color changed */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends t/family c/yellow";
        expectedMessage = String.format(MESSAGE_CHANGE_TAG_COLOR_SUCCESS, VALID_TAGLIST_2,
                VALID_TAG_COLOR_NAME_YELLOW);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTagColorPair(VALID_TAGLIST_2, new TagColor(VALID_TAG_COLOR_NAME_YELLOW));
        assertCommandSuccess(changeTagColorCommand, expectedModel, expectedMessage);

        /* Case: change the tag color of a tag with invalid tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/invalid_tag_name c/yellow";
        expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: change the tag color of a tag with non-existing tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/yellow" + " t/" + INVALID_TAG;
        expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_NOT_EXISTING_TAGS, INVALID_TAGLIST);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: change the tag color of a list of tags consist of non-existing tag name-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/yellow" + " t/" + INVALID_TAG
                + " t/" + INVALID_TAG_2;
        expectedMessage = String.format(ChangeTagColorCommand.MESSAGE_NOT_EXISTING_TAGS, INVALID_TAGLIST_2);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: input tag color name is not valid-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends" + " c/" + INVALID_TAG_COLOR_NAME;
        expectedMessage = TagColor.MESSAGE_TAG_COLOR_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: missing tag color field-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friends";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeTagColorCommand.MESSAGE_USAGE);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: missing tag field-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/red";
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeTagColorCommand.MESSAGE_USAGE);
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: tag name is null-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " c/red t/";
        expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);

        /* Case: color is null-> rejected */
        changeTagColorCommand = ChangeTagColorCommand.COMMAND_WORD + " t/friend c/";
        expectedMessage = TagColor.MESSAGE_TAG_COLOR_CONSTRAINTS;
        assertCommandFailure(changeTagColorCommand, expectedMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
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
