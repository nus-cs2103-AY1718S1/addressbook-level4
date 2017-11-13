package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddPictureCommand}.
 */
public class AddPictureCommandTest extends CommandTest {

    @Test
    public void execute_validIndexUnfilteredListInvalidPath_failure() throws Exception {
        try {
            ReadOnlyPerson personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            AddPictureCommand addPictureCommand = prepareCommand(INDEX_FIRST_PERSON);

            String expectedMessage = String.format(AddPictureCommand.MESSAGE_ADDPIC_FAILURE, personToUpdate.getName());

            assertCommandFailure(addPictureCommand, model, expectedMessage);
            assertTrue(personToUpdate.getAsText().equals(model.getFilteredPersonList()
                    .get(INDEX_FIRST_PERSON.getZeroBased()).getAsText()));
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_validIndexUnfilteredListValidPath_success() throws Exception {
        try {
            ReadOnlyPerson personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            AddPictureCommand addPictureCommand = prepareCommand(INDEX_FIRST_PERSON);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(AddPictureCommand.MESSAGE_ADDPIC_SUCCESS, personToUpdate.getName());

            SetPathCommand setPathCommand = prepareSetPathCommand("src/test/resources/TestProfilePics/");
            setPathCommand.execute();

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.addProfilePicture(personToUpdate);

            assertCommandSuccess(addPictureCommand, model, expectedMessage, expectedModel);
            assertTrue(personToUpdate.getAsText().equals(model.getFilteredPersonList()
                    .get(INDEX_FIRST_PERSON.getZeroBased()).getAsText()));
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        prepareCommand(outOfBoundIndex);
        fail(UNEXPECTED_EXECTION);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        prepareCommand(outOfBoundIndex);
        fail(UNEXPECTED_EXECTION);
    }

    @Test
    public void equals() throws Exception {
        AddPictureCommand addPictureFirstCommand = new AddPictureCommand(INDEX_FIRST_PERSON);
        AddPictureCommand addPictureSecondCommand = new AddPictureCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(addPictureFirstCommand.equals(addPictureFirstCommand));

        // same values -> returns true
        AddPictureCommand addPictureFirstCommandCopy = new AddPictureCommand(INDEX_FIRST_PERSON);
        assertTrue(addPictureFirstCommand.equals(addPictureFirstCommandCopy));

        // different types -> returns false
        assertFalse(addPictureFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addPictureFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addPictureFirstCommand.equals(addPictureSecondCommand));
    }

    /**
     * Returns a {@code AddPictureCommand} with the parameter {@code index}.
     */
    private AddPictureCommand prepareCommand(Index index) throws CommandException {
        AddPictureCommand addPictureCommand = new AddPictureCommand(index);
        addPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPictureCommand;
    }

    /**
     * Returns a {@code SetPathCommand} with the parameter {@code path}.
     */
    private SetPathCommand prepareSetPathCommand(String path) {
        SetPathCommand setPathCommand = new SetPathCommand(path);
        setPathCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setPathCommand;
    }

}
