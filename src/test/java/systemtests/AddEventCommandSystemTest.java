package systemtests;
//@@author chernghann

import org.junit.Test;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Address;
import seedu.address.testutil.AddEventUtil;
import seedu.address.testutil.EventBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalEvents.EVENT_A;

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyEvent toAdd = EVENT_A;
        String command = " " + AddEventCommand.COMMAND_WORD + " " + EVENT_NAME_A_DESC + " "
                + EVENT_DATE_A_DESC + " " + EVENT_ADDRESS_A_DESC + " ";

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except name -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_B_NAME).withDate(VALID_EVENT_A_DATE)
                .withAddress(VALID_EVENT_A_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except date -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_A_NAME).withDate(VALID_EVENT_B_DATE)
                .withAddress(VALID_EVENT_A_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_B_DESC + EVENT_ADDRESS_A_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except address -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_A_NAME).withDate(VALID_EVENT_A_DATE)
                .withAddress(VALID_EVENT_B_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_B_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: missing name -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

//        /* Case: invalid name -> rejected */
//        command = AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC + VALID_EVENT_A_DATE + VALID_EVENT_A_ADDRESS;
//        assertCommandFailure(command, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
//
//        /* Case: invalid date -> rejected */
//        command = AddEventCommand.COMMAND_WORD + VALID_EVENT_A_NAME + INVALID_DATE_DESC + VALID_EVENT_A_ADDRESS;
//        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);
//
//        /* Case: invalid date -> rejected */
//        command = AddEventCommand.COMMAND_WORD + VALID_EVENT_A_NAME + VALID_EVENT_A_DATE + INVALID_ADDRESS_DESC;
//        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddEventCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyEvent toAdd) {
        assertCommandSuccess(AddEventUtil.getAddEventCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}. Executes {@code command} instead.
     * @see AddEventCommandSystemTest#assertCommandSuccess(ReadOnlyEvent)
     */
    private void assertCommandSuccess(String command, ReadOnlyEvent toAdd) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, toAdd);
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        executeCommand("events");
        executeCommand(command);
        assertEventDisplaysExpected("", expectedResultMessage);
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
        executeCommand(command);
        assertEventDisplaysExpected(command, expectedResultMessage);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
