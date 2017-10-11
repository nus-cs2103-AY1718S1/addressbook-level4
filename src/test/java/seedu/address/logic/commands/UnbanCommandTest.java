package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnbanCommand}.
 */
public class UnbanCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUnban = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnbanCommand unbanCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnbanCommand.MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeBlacklistedPerson(personToUnban);

        assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBlacklistedPersonList().size() + 1);
        UnbanCommand unbanCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unbanCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstBlacklistedPersonOnly(model);

        ReadOnlyPerson personToUnban = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnbanCommand unbanCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(unbanCommand.MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeBlacklistedPerson(personToUnban);
        showNoBlacklistedPerson(expectedModel);

        assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstBlacklistedPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getBlacklistedPersonList().size());

        UnbanCommand unbanCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unbanCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnbanCommand unbanFirstCommand = new UnbanCommand(INDEX_FIRST_PERSON);
        UnbanCommand unbanSecondCommand = new UnbanCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unbanFirstCommand.equals(unbanFirstCommand));

        // same values -> returns true
        UnbanCommand unbanFirstCommandCopy = new UnbanCommand(INDEX_FIRST_PERSON);
        assertTrue(unbanFirstCommand.equals(unbanFirstCommandCopy));

        // different types -> returns false
        assertFalse(unbanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unbanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unbanFirstCommand.equals(unbanSecondCommand));
    }

    /**
     * Returns a {@code UnbanCommand} with the parameter {@code index}.
     */
    private UnbanCommand prepareCommand(Index index) {
        UnbanCommand unbanCommand = new UnbanCommand(index);
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }

    /**
     * Updates {@code model}'s filtered blacklist to show no one.
     */
    private void showNoBlacklistedPerson(Model model) {
        model.updateFilteredBlacklistedPersonList(p -> false);

        assert model.getFilteredBlacklistedPersonList().isEmpty();
    }
}
