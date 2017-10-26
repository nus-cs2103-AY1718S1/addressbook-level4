package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.MULTIPLE_TAG_DELETION;
import static seedu.address.testutil.TypicalTags.SINGLE_TAG_DELETION;
import static seedu.address.testutil.TypicalTags.SINGLE_TAG_DELETION_ALT;
import static seedu.address.testutil.TypicalTags.TAG_DOES_NOT_EXIST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/***
 * @author Sri-vatsa
 * Focuses tests on model's deleteTag method, assumes DeleteTagCommandParser test handles tests for converting User
 * input into type suitable for deleteTag method (i.e. String Array)
 */

public class DeleteTagCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteTagCommand(null);
    }

    @Test
    public void execute_deleteSingleTagSuccessful() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(SINGLE_TAG_DELETION, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_deleteMultipleTagSuccessful() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(MULTIPLE_TAG_DELETION, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_deleteSingleTag_tagDoesNotExist() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(TAG_DOES_NOT_EXIST, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_NO_TAGS_DELETED), commandResult.feedbackToUser);
    }

    /**
     * Generates a new DeleteTagCommand for test
     */
    private DeleteTagCommand getDeleteTagCommand(String [] arg, Model model) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(arg);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }


    @Test
    public void equals() {
        DeleteTagCommand deleteTagCommandOne = new DeleteTagCommand(SINGLE_TAG_DELETION);
        DeleteTagCommand deleteTagCommandTwo = new DeleteTagCommand(SINGLE_TAG_DELETION_ALT);

        // same object -> returns true
        assertTrue(deleteTagCommandOne.equals(deleteTagCommandOne));

        // same values -> returns true
        DeleteTagCommand deleteTagCommandOneCopy = new DeleteTagCommand(SINGLE_TAG_DELETION);
        assertTrue(deleteTagCommandOne.equals(deleteTagCommandOneCopy));

        // different types -> returns false
        assertFalse(deleteTagCommandOne.equals(1));

        // null -> returns false
        assertFalse(deleteTagCommandOne.equals(null));

        // different person -> returns false
        assertFalse(deleteTagCommandOne.equals(deleteTagCommandTwo));
    }

}
