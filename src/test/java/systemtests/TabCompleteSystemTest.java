package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;

//@@author newalter
public class TabCompleteSystemTest extends AddressBookSystemTest {

    @Test
    public void tab_complete() {
        /* Case: partial name in find command, TAB Pressed
         * -> 2 persons found
         */
        String command = FindCommand.COMMAND_WORD + " Mei";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();

        /* Case: partial name in find command, DOWN, ENTER Pressed
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " Be";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel, KeyCode.DOWN, KeyCode.ENTER);
        assertSelectedCardUnchanged();

        /* Case: partial name in find command, DOWN, DOWN, ENTER Pressed
         * -> 1 person found
         */
        command = FindCommand.COMMAND_WORD + " Be";
        ModelHelper.setFilteredList(expectedModel, GEORGE);
        assertCommandSuccess(command, expectedModel, KeyCode.DOWN, KeyCode.DOWN, KeyCode.ENTER);
        assertSelectedCardUnchanged();

        /* Case: Non-matching keywords in find command, no suggestions
         * -> 0 persons found
         */
        command = FindCommand.COMMAND_WORD + " Bee";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();

        /* Case: partial name in second argument, TAB Pressed
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Benson Da";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();
    }


    /**
     * Presses the keys {@code keyPresses} and then Executes {@code command}
     * and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, KeyCode... keyPresses) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        pressAndExecuteCommand(command, keyPresses);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

}
