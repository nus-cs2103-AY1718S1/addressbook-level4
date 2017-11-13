package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

//@@author royceljh
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, new EditEventDescriptor());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withHeader(VALID_HEADER_BIRTHDAY).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showFirstEventOnly(model);

        // edit event in filtered list into a duplicate in address book
        ReadOnlyEvent eventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build();
        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showFirstEventOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EditEventCommand editEventCommand = prepareCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY).build());

        assertCommandFailure(editEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_MEETING);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_MEETING);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_MEETING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_BIRTHDAY)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventDescriptor descriptor) {
        EditEventCommand editEventCommand = new EditEventCommand(index, descriptor);
        editEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editEventCommand;
    }
}
