package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayStatisticsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.TagStatisticsCommand;
import seedu.address.model.Model;

// @@author johnweikangong
public class ChangeInformationPanelSystemTest extends AddressBookSystemTest {

    @Test
    public void changeInformationPanel() {
        final String homePanelId = "[StackPane[id=homePanel]]";
        final String personInformationPanelId = "[SplitPane[id=personInformationPanel, styleClass=split-pane]]";
        final String helpPanelId = "[StackPane[id=helpPanel]]";
        final String birthdayStatisticsPanelId = "[StackPane[id=birthdayStatisticsPanel]]";
        final String tagStatisticsPanelId = "[StackPane[id=tagStatisticsPanel]]";

        assertHandleSuccess("helpPanel", helpPanelId, "");

        assertHandleSuccess("homePanel", homePanelId, "");

        assertHandleSuccess("birthdayStatisticsPanel", birthdayStatisticsPanelId, "");

        assertHandleSuccess("tagStatisticsPanel", tagStatisticsPanelId, "");

        /* Case: Changes information panel of address book using select command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to person information panel.
         */
        String selectCommand = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_FIRST_PERSON);

        /* Case: Change information panel of address book using help command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel.
         */
        assertCommandSuccess(HelpCommand.COMMAND_WORD, helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using home command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel.
        */
        assertCommandSuccess(HomeCommand.COMMAND_WORD, homePanelId, HomeCommand.MESSAGE_SUCCESS);

        //@@author Valerieyue
        /* Case: Change information panel of address book using birthday statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_WORD, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using tag statistics command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_WORD, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
        //@@author

        /* Case: Changes information panel of address book using select command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to person information panel.
        */
        selectCommand = SelectCommand.COMMAND_WORD + " " + INDEX_SECOND_PERSON.getOneBased();
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_SECOND_PERSON);

        /* Case: Changes information panel of address book using help command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel.
        */
        assertCommandSuccess("   " + HelpCommand.COMMAND_WORD + " 232##$$% ",
                helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel.
        */
        assertCommandSuccess("       " + HomeCommand.COMMAND_WORD + "  $#%@   ",
                homePanelId, HomeCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using select command word, with leading spaces
         * and trailing spaces -> information panel changed to person information panel.
        */
        selectCommand = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " ";
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_FIRST_PERSON);

        /* Case: Changes information panel of address book using help command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel.
        */
        assertCommandSuccess(HelpCommand.COMMAND_ALIAS, helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel.
        */
        assertCommandSuccess(HomeCommand.COMMAND_ALIAS, homePanelId, HomeCommand.MESSAGE_SUCCESS);

        //@@author Valerieyue
        /* Case: Changes information panel of address book using birthday statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess(BirthdayStatisticsCommand.COMMAND_ALIAS, birthdayStatisticsPanelId,
                BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess(TagStatisticsCommand.COMMAND_ALIAS, tagStatisticsPanelId,
                TagStatisticsCommand.MESSAGE_SUCCESS);
        //@@author

        /* Case: Changes information panel of address book using help command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel.
        */
        assertCommandSuccess("      " + HelpCommand.COMMAND_ALIAS + " 2##$$% ",
                helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using home command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel.
        */
        assertCommandSuccess("  " + HomeCommand.COMMAND_WORD + "  $#%323@21   ",
                homePanelId, HomeCommand.MESSAGE_SUCCESS);

        //@@author Valerieyue
        /* Case: Changes information panel of address book using birthday statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to birthday statistics panel.
        */
        assertCommandSuccess("  " + BirthdayStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                birthdayStatisticsPanelId, BirthdayStatisticsCommand.MESSAGE_SUCCESS);

        /* Case: Changes information panel of address book using tag statistics command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to tag statistics panel.
        */
        assertCommandSuccess("  " + TagStatisticsCommand.COMMAND_WORD + "  $#%543@$   ",
                tagStatisticsPanelId, TagStatisticsCommand.MESSAGE_SUCCESS);
        //@@author

        /* Case: Mixed case home command word -> rejected. */
        assertCommandFailure("HoME", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case home command alias -> rejected. */
        assertCommandFailure("hO", MESSAGE_UNKNOWN_COMMAND);

        //@@author Valerieyue
        /* Case: Mixed case birthday statistics command word -> rejected. */
        assertCommandFailure("birTHDaystaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case birthday statistics command alias -> rejected. */
        assertCommandFailure("bstATs", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command word -> rejected. */
        assertCommandFailure("tagstaTIStics", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case tag statistics command alias -> rejected. */
        assertCommandFailure("tstATs", MESSAGE_UNKNOWN_COMMAND);
        //@@author

        /* Case: Mixed case help command word -> rejected. */
        assertCommandFailure("hElP", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case help command alias -> rejected. */
        assertCommandFailure("sOs", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId,
                                      Index expectedSelectedCardIndex) {
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        assertCommandSuccess(command, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the information panel ID
     * displays the correct panel, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId, String expectedResultMessage) {
        assertCommandSuccess(command, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the
     * information panel displays {@code expectedInformationPanel}, result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeInformationPanelSystemTest#assertCommandSuccess(String, String, String)
     */
    private void assertCommandSuccess(String command, String expectedInformationPanelId,
                                      String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertInformationPanelShowsCorrectPanel(expectedInformationPanelId);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code handle} and verifies that the command box displays an empty string, the information panel ID
     * displays the correct panel, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the initial model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertHandleSuccess(String handle, String expectedInformationPanelId,
                                     String expectedResultMessage) {
        assertHandleSuccess(handle, expectedInformationPanelId, expectedResultMessage, getModel());
    }

    /**
     * Performs the same verification as {@code assertHandleSuccess(String, String, String)} except that the
     * information panel displays {@code expectedInformationPanel}, result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeInformationPanelSystemTest#assertHandleSuccess(String, String, String, Model)
     */
    private void assertHandleSuccess(String handle, String expectedInformationPanelId,
                                     String expectedResultMessage, Model expectedModel) {
        executeHandle(handle);
        assertInformationPanelShowsCorrectPanel(expectedInformationPanelId);
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
