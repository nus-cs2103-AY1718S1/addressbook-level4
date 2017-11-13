//@@author shuang-yang
package seedu.address.logic.commands.event;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.Period;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;

public class RepeatCommandTest {
    private Model model = new ModelManager(getTypicalEventAddressBook(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        RepeatCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, Optional.empty());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(RepeatCommand.MESSAGE_REPEAT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withPeriod(VALID_PERIOD_MIDTERM).build();
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, period);

        String expectedMessage = String.format(RepeatCommand.MESSAGE_REPEAT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(outOfBoundIndex, period);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
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

        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(outOfBoundIndex, period);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        final RepeatCommand standardCommand = new RepeatCommand(INDEX_FIRST_EVENT, period);

        // same values -> returns true
        Optional<Period> copyPeriod = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand commandWithSameValues = new RepeatCommand(INDEX_FIRST_EVENT, copyPeriod);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RepeatCommand(INDEX_SECOND_EVENT, period)));

        // different descriptor -> returns false
        Optional<Period> newPeriod = Period.generatePeriod(VALID_PERIOD_SOCCER);
        assertFalse(standardCommand.equals(new RepeatCommand(INDEX_FIRST_EVENT, newPeriod)));
    }

    /**
     * Returns an {@code RepeatCommand} with parameters {@code index} and {@code descriptor}
     */
    private RepeatCommand prepareCommand(Index index, Optional<Period> period) {
        RepeatCommand editCommand = new RepeatCommand(index, period);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

}
