package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteAltCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validName_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAltCommand deleteAltCommand = prepareCommand(NAME_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAltCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteAltCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() throws Exception {
        DeleteAltCommand deleteAltCommand = prepareCommand("invalid name");

        assertCommandFailure(deleteAltCommand, model, Messages.MESSAGE_PERSON_NAME_ABSENT);
    }

    @Test
    public void execute_insufficientInput_throwsCommandException() throws Exception {
        DeleteAltCommand deleteAltCommand = prepareCommand("al");

        assertCommandFailure(deleteAltCommand, model, Messages.MESSAGE_PERSON_NAME_INSUFFICIENT);
    }

    @Test
    public void equals() {
        DeleteAltCommand deleteFirstAltCommand = new DeleteAltCommand(NAME_FIRST_PERSON);
        DeleteAltCommand deleteSecondAltCommand = new DeleteAltCommand(NAME_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstAltCommand.equals(deleteFirstAltCommand));

        // same values -> returns true
        DeleteAltCommand deleteFirstAltCommandCopy = new DeleteAltCommand(NAME_FIRST_PERSON);
        assertTrue(deleteFirstAltCommand.equals(deleteFirstAltCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstAltCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstAltCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstAltCommand.equals(deleteSecondAltCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code name}.
     */
    private DeleteAltCommand prepareCommand(String name) {
        DeleteAltCommand deleteAltCommand = new DeleteAltCommand(name);
        deleteAltCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAltCommand;
    }
}
