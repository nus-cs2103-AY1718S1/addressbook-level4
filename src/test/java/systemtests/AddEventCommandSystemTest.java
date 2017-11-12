package systemtests;
//@@author chernghann
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_NAME;
import static seedu.address.testutil.TypicalEvents.EVENT_A;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Address;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        executeCommand("events");
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

        /* Case: invalid name -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + INVALID_DATE_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}. Executes {@code command} instead.
     */
    private void assertCommandSuccess(String command, ReadOnlyEvent toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        executeCommand(command);
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
