package seedu.address.logic.commands.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dennaloh
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code GMapCommand}.
 */
public class GMapCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToGetDirectionsTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapCommand gMapCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(GMapCommand.MESSAGE_SUCCESS, personToGetDirectionsTo);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.getGMapUrl(personToGetDirectionsTo);

        assertCommandSuccess(gMapCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GMapCommand gMapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(gMapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToGetDirectionsTo = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapCommand gMapCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(GMapCommand.MESSAGE_SUCCESS, personToGetDirectionsTo);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.getGMapUrl(personToGetDirectionsTo);

        assertCommandSuccess(gMapCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GMapCommand gMapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(gMapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GMapCommand gMapFirstCommand = new GMapCommand(INDEX_FIRST_PERSON);
        GMapCommand gMapSecondCommand = new GMapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(gMapFirstCommand.equals(gMapFirstCommand));

        // same values -> returns true
        GMapCommand gMapFirstCommandCopy = new GMapCommand(INDEX_FIRST_PERSON);
        assertTrue(gMapFirstCommand.equals(gMapFirstCommandCopy));

        // different types -> returns false
        assertFalse(gMapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gMapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(gMapFirstCommand.equals(gMapSecondCommand));
    }

    /**
     * Returns a {@code GMapCommand} with the parameter {@code index}.
     */
    private GMapCommand prepareCommand(Index index) {
        GMapCommand gMapCommand = new GMapCommand(index);
        gMapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return gMapCommand;
    }
}
