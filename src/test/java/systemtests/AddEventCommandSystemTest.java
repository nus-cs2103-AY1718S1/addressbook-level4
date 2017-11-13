package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_EVENT2;
import static seedu.address.testutil.TypicalEvents.EV1;
import static seedu.address.testutil.TypicalEvents.EVENT1;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.property.PropertyManager;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;

//@@author junyango

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        showAllEvents();
        Model model = getModel();
        /* Case: add a event without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyEvent toAdd = EV1;
        String command = "   " + AddEventCommand.COMMAND_WORD + "  " + NAME_DESC_EVENT1 + "  " + DATE_DESC_EVENT1
                + " " + VENUE_DESC_EVENT1;
        String inputCommand = command;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding EV1 to the list -> EV1 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(UndoCommand.MESSAGE_SUCCESS, inputCommand);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding EV1 to the list -> EV1 added again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = String.format(RedoCommand.MESSAGE_SUCCESS, inputCommand);
        model.addEvent(toAdd);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate event -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a event with all fields same as another event in the address book except name -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT2).withDateTime(VALID_DATE_EVENT1)
                .withAddress(VALID_VENUE_EVENT1).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT2 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book except date -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT2)
                .withAddress(VALID_VENUE_EVENT1).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT2 + VENUE_DESC_EVENT1;
        assertCommandSuccess(command, toAdd);


        /* Case: add a event with all fields same as another event in the address book except address -> added */
        toAdd = new EventBuilder().withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT1)
                .withAddress(VALID_VENUE_EVENT2).build();
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + VENUE_DESC_EVENT2;
        assertCommandSuccess(command, toAdd);


        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getEventList().size() == 0;
        assertCommandSuccess(EVENT1);

        /* Case: missing name -> rejected */
        command = AddEventCommand.COMMAND_WORD + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addEs " + EventUtil.getEventDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1;
        assertCommandFailure(command, PropertyManager.getPropertyConstraintMessage("n"));

        /* Case: invalid address -> rejected */
        command = AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1 + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, PropertyManager.getPropertyConstraintMessage("a"));
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddEventCommand} with the of {@code toAdd}, and the model related components equal to the current model
     * added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyEvent toAdd) {
        assertCommandSuccess(EventUtil.getAddEvent(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyEvent)}. Executes {@code command}
     * instead.
     * @see AddEventCommandSystemTest#assertCommandSuccess(ReadOnlyEvent)
     */
    private void assertCommandSuccess(String command, ReadOnlyEvent toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyEvent)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddEventCommandSystemTest#assertCommandSuccess(String, ReadOnlyEvent)
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
