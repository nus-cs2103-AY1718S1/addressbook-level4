package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EVENT_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_THIRD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventDescriptorBuilder;

// @@author Adoby7
/**
 * An integration test for edit event command and model
 */
public class EditEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void testLargeIndexFailure() {
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND).build();
        EditEventCommand command = prepareCommand(invalidLargeIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void testInvalidIndexInFilteredListFailure() {
        showFirstEventOnly(model);
        Index invalidIndex = INDEX_SECOND_PERSON;
        // ensures that the index is still in bounds of list
        assertTrue(invalidIndex.getZeroBased() < model.getEventList().getEventList().size());

        EditEventCommand command = prepareCommand(invalidIndex,
            new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST).build());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void testDuplicateEventFailure() {
        Event firstEvent = new Event(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        EditEventDescriptor descriptor = new EventDescriptorBuilder(firstEvent).build();
        EditEventCommand command = prepareCommand(INDEX_SECOND_EVENT, descriptor);

        assertCommandFailure(command, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void testDuplicateInFilteredListFailure() {
        showFirstEventOnly(model);

        ReadOnlyEvent event = model.getEventList().getEventList().get(INDEX_SECOND_EVENT.getZeroBased());
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT,
            new EventDescriptorBuilder(event).build());

        assertCommandFailure(command, model, EditEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void testEditAllFields() throws Exception {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EventDescriptorBuilder(editedEvent).build();
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEditSomeFields() throws Exception {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder event = new EventBuilder(lastEvent);
        Event editedEvent = event.withName(VALID_EVENT_NAME_SECOND).withDescription(VALID_EVENT_DESC_SECOND)
            .withTime(VALID_EVENT_TIME_THIRD).build();

        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND)
            .withDescription(VALID_EVENT_DESC_SECOND).withTime(VALID_EVENT_TIME_THIRD).build();
        EditEventCommand command = prepareCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(lastEvent, editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEditInFilteredList() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent personInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(personInFilteredList).withName(VALID_EVENT_NAME_SECOND).build();
        EditEventCommand command = prepareCommand(INDEX_FIRST_EVENT,
            new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(model.getAddressBook(), new EventList(model.getEventList()),
            new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()), editedEvent);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST_EVENT, DESC_EVENT_FIRST);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_EVENT_FIRST);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND_EVENT, DESC_EVENT_FIRST)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST_EVENT, DESC_EVENT_SECOND)));
    }

    /**
     * Returns an {@code EditEventCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditEventCommand prepareCommand(Index index, EditEventCommand.EditEventDescriptor descriptor) {
        EditEventCommand command = new EditEventCommand(index, descriptor);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
