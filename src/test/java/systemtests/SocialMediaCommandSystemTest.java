package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SocialMediaCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author kenpaxtonlim
public class SocialMediaCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void socialmedia() {
        /* Case: show facebook of first person in list -> shown */
        String command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_FACEBOOK
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);

        /* Case: show twitter of first person in list -> shown */
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_TWITTER
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_TWITTER);

        /* Case: show instagram of first person in list -> shown */
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_INSTAGRAM
                + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(command, INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_INSTAGRAM);

        /* Case: undo previous showing of social media -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo showing instagram of first person in list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        command = SocialMediaCommand.COMMAND_WORD + " " + SocialMediaCommand.TYPE_INSTAGRAM + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid social media type -> rejected */
        command = SocialMediaCommand.COMMAND_WORD + " " + "abc" + " " + invalidIndex;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index index, String type) {
        Model expectedModel = getModel();
        String expectedResultMessage = SocialMediaCommand.MESSAGE_SUCCESS;

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        String url = "";
        switch (type) {
        case SocialMediaCommand.TYPE_FACEBOOK:
            url = SocialMediaCommand.URL_FACEBOOK
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().facebook;
            break;
        case SocialMediaCommand.TYPE_TWITTER:
            url = SocialMediaCommand.URL_TWITTER
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().twitter;
            break;
        case SocialMediaCommand.TYPE_INSTAGRAM:
            url = SocialMediaCommand.URL_INSTAGRAM
                    + expectedModel.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().instagram;
            break;
        default:
            throw new AssertionError();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
