package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH;
import static seedu.address.testutil.TypicalProfilePicture.SECOND_PERFECT_PROFILE_PICTURE_PATH;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeProfilePictureCommand}.
 */
public class ChangeProfilePictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SECOND_PERFECT_PROFILE_PICTURE_PATH);
        assertExecutionSuccess(lastPersonIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);
        assertExecutionSuccess(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ChangeProfilePictureCommand changeProfilePictureFirstCommand =
                new ChangeProfilePictureCommand(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        ChangeProfilePictureCommand changeProfilePictureSecondCommand =
                new ChangeProfilePictureCommand(INDEX_SECOND_PERSON, SECOND_PERFECT_PROFILE_PICTURE_PATH);

        // same object -> returns true
        assertTrue(changeProfilePictureFirstCommand.equals(changeProfilePictureFirstCommand));

        // same values -> returns true
        ChangeProfilePictureCommand changeProfilePictureFirstCommandCopy =
                new ChangeProfilePictureCommand(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        assertTrue(changeProfilePictureFirstCommand.equals(changeProfilePictureFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(null));

        // different picture -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(changeProfilePictureSecondCommand));
    }

    /**
     * Executes a {@code ChangeProfilePictureCommand} with the given {@code index, picturePath}
     * is raised with the correct index and picture path
     */
    private void assertExecutionSuccess(Index index, String picturePath) {
        ChangeProfilePictureCommand changeProfilePictureCommand = prepareCommand(index, picturePath);

        try {
            CommandResult commandResult = changeProfilePictureCommand.execute();
            assertEquals(String.format(ChangeProfilePictureCommand.MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS,
                    index.getOneBased()), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code ChangeProfilePictureCommand} with the given {@code index, picturePath},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String picturePath, String expectedMessage) {
        ChangeProfilePictureCommand changeProfilePictureCommand = prepareCommand(index, picturePath);

        try {
            changeProfilePictureCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ChangeProfilePictureCommand} with the parameter {@code index, picturePath}.
     */
    private ChangeProfilePictureCommand prepareCommand(Index index, String picturePath) {
        ChangeProfilePictureCommand changeProfilePictureCommand = new ChangeProfilePictureCommand(index, picturePath);
        changeProfilePictureCommand.setData(model, new CommandHistory(),
                new UndoRedoStack(), new RecentlyDeletedQueue());
        return changeProfilePictureCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

}
