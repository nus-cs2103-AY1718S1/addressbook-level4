package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_BIRTHDAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalPersons.BIRTHDAY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;

//@@author royceljh
public class EditEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> event edited
         */
        Index index = INDEX_FIRST_EVENT;
        String command = " " + EditEventCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + HEADER_DESC_BIRTHDAY
                + "  " + DESC_DESC_BIRTHDAY + " " + EVENT_DATE_DESC_BIRTHDAY + " ";
        Event editedEvent = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        assertCommandSuccess(command, index, editedEvent);

        /* Case: undo editing the last event in the list -> last event restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last event in the list -> last event edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateEvent(
                getModel().getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_EVENT;
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + DESC_DESC_MEETING;
        ReadOnlyEvent eventToEdit = getModel().getFilteredEventList().get(index.getZeroBased());
        editedEvent = new EventBuilder(eventToEdit).withDesc(VALID_DESC_MEETING).build();
        assertCommandSuccess(command, index, editedEvent);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " 0" + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " -1" + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredEventList().size() + 1;
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + invalidIndex + HEADER_DESC_MEETING,
                Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + HEADER_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased(),
                EditEventCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid header -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_HEADER_DESC, Header.MESSAGE_HEADER_CONSTRAINTS);

        /* Case: invalid desc -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_DESC_DESC, Desc.MESSAGE_DESC_CONSTRAINTS);

        /* Case: invalid event date -> rejected */
        assertCommandFailure(EditEventCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased()
                        + INVALID_EVENT_DATE_DESC, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);

        /* Case: edit an event with new values same as another event's values -> rejected */
        executeCommand(EventUtil.getAddEventCommand(BIRTHDAY));
        assertTrue(getModel().getAddressBook().getEventList().contains(BIRTHDAY));
        index = INDEX_FIRST_EVENT;
        assertFalse(getModel().getFilteredEventList().get(index.getZeroBased()).equals(BIRTHDAY));
        command = EditEventCommand.COMMAND_WORD + " " + index.getOneBased() + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY;
        assertCommandFailure(command, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the event at index {@code toEdit} being
     * updated to values specified {@code editedEvent}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyEvent editedEvent) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateEvent(
                    expectedModel.getFilteredEventList().get(toEdit.getZeroBased()), editedEvent);
            expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        } catch (DuplicateEventException | EventNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedEvent is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the command box has the error style.<br>
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
