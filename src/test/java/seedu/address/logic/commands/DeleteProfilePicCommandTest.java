package seedu.address.logic.commands;

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
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.ReadOnlyPerson;

//@@author soonwj
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteProfilePicCommand}.
 */
public class DeleteProfilePicCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON);

        Person deletedProfilePicPerson = new Person(personToDelete);
        deletedProfilePicPerson.setProfilePic(new ProfilePic());

        String expectedMessage = String.format(DeleteProfilePicCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToDelete, deletedProfilePicPerson);

        assertCommandSuccess(deleteProfilePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteProfilePicCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                personToDelete);

        Person deletedProfilePicPerson = new Person(personToDelete);
        deletedProfilePicPerson.setProfilePic(new ProfilePic());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToDelete, deletedProfilePicPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteProfilePicCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteProfilePicCommand deleteProfilePicCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteProfilePicCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteProfilePicCommand deleteFirstCommand = new DeleteProfilePicCommand(INDEX_FIRST_PERSON);
        DeleteProfilePicCommand deleteSecondCommand = new DeleteProfilePicCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteProfilePicCommand deleteFirstCommandCopy = new DeleteProfilePicCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteProfilePicCommand} with the parameter {@code index}.
     */
    private DeleteProfilePicCommand prepareCommand(Index index) {
        DeleteProfilePicCommand deleteProfilePicCommand = new DeleteProfilePicCommand(index);
        deleteProfilePicCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteProfilePicCommand;
    }
}
