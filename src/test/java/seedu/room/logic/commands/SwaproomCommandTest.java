package seedu.room.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.room.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class SwaproomCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson person1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson person2 = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SwaproomCommand swapCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(SwaproomCommand.MESSAGE_SWAP_PERSONS_SUCCESS, person1.getName(),
                person2.getName());

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.swapRooms(person1, person2);

        assertCommandSuccess(swapCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex1 = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredPersonList().size() + 2);
        SwaproomCommand swapCommand = prepareCommand(outOfBoundIndex1, outOfBoundIndex2);

        assertCommandFailure(swapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex1 = INDEX_SECOND_PERSON;
        Index outOfBoundIndex2 = INDEX_THIRD_PERSON;

        // ensures that outOfBoundIndex is still in bounds of resident book list
        assertTrue(outOfBoundIndex1.getZeroBased() < model.getResidentBook().getPersonList().size());
        assertTrue(outOfBoundIndex2.getZeroBased() < model.getResidentBook().getPersonList().size());

        SwaproomCommand swapCommand = prepareCommand(outOfBoundIndex1, outOfBoundIndex2);

        assertCommandFailure(swapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SwaproomCommand swapFirstCommand = new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        SwaproomCommand swapSecondCommand = new SwaproomCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);
        SwaproomCommand swapThirdCommand = new SwaproomCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(swapFirstCommand.equals(swapFirstCommand));

        // same values -> returns true
        SwaproomCommand swapFirstCommandCopy = new SwaproomCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(swapFirstCommand.equals(swapFirstCommandCopy));

        // same values with swapped arguments -> returns true
        assertTrue(swapFirstCommand.equals(swapSecondCommand));

        // different types -> returns false
        assertFalse(swapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(swapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(swapFirstCommand.equals(swapThirdCommand));
    }

    /**
     * Returns a {@code SwaproomCommand} with the parameter {@code index}.
     */
    private SwaproomCommand prepareCommand(Index index1, Index index2) {
        SwaproomCommand swapCommand = new SwaproomCommand(index1, index2);
        swapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return swapCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
