package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.UserPrefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;



/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class LocateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToLocate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LocateCommand locateCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(LocateCommand.MESSAGE_LOCATE_PERSON_SUCCESS, personToLocate);


        assertEquals(expectedMessage, locateCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocateCommand LocateCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(LocateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LocateCommand LocateCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(LocateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private LocateCommand prepareCommand(Index index) {
        LocateCommand locateCommand;
        locateCommand = new LocateCommand(index);
        locateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locateCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}

