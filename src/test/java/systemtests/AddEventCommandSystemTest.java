package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_MEETING;
import static seedu.address.testutil.TypicalPersons.BIRTHDAY;
import static seedu.address.testutil.TypicalPersons.MEETING;
import static seedu.address.testutil.TypicalPersons.OUTING;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;

//@@author royceljh
public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add an event Birthday to a non-empty address book, command with leading spaces and trailing spaces
         * -> Birthday event added
         */
        ReadOnlyEvent toAdd = BIRTHDAY;
        String command = "   " + AddEventCommand.COMMAND_WORD + "  " + HEADER_DESC_BIRTHDAY + "  "
                + DESC_DESC_BIRTHDAY + " " + EVENT_DATE_DESC_BIRTHDAY + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Birthday to the list ->  Birthday deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Birthday to the list -> Birthday added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate event -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);


        /* Case: add an event with all fields same as another event in the address book except header -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_MEETING).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_MEETING + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except desc -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_MEETING)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_MEETING + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except event date -> added */
        toAdd = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_MEETING).build();
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getEventList().size() == 0;
        assertCommandSuccess(OUTING);

        /* Case: add an event with parameters in random order -> added */
        toAdd = MEETING;
        command = AddEventCommand.COMMAND_WORD + DESC_DESC_MEETING + HEADER_DESC_MEETING + EVENT_DATE_DESC_MEETING;
        assertCommandSuccess(command, toAdd);

        /* Case: missing header -> rejected */
        command = AddEventCommand.COMMAND_WORD + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing desc -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing event date -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addEv ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid header -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_HEADER_DESC + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, Header.MESSAGE_HEADER_CONSTRAINTS);

        /* Case: invalid desc -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + INVALID_DESC_DESC + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, Desc.MESSAGE_DESC_CONSTRAINTS);

        /* Case: invalid event date -> rejected */
        command = AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY + DESC_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC;
        assertCommandFailure(command, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddEventCommand} with the details of {@code toAdd}, and the model related components equal to
     * the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyEvent toAdd) {
        assertCommandSuccess(EventUtil.getAddEventCommand(toAdd), toAdd);
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
