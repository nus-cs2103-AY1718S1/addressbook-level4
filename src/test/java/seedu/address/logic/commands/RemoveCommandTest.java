package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveCommand}.
 */
public class RemoveCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeValidTagAll_success() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Index index = null;

        RemoveCommand removeCommand = prepareCommand(tagToRemove, index);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_SUCCESS
                        + " from address book.", tagToRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagToRemove, index);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidTagAll_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("enemy");
        Index index = null;

        RemoveCommand removeCommand = prepareCommand(tagToRemove, index );

        String expectedMessage =  "Tag: " + tagToRemove.toString() + RemoveCommand.MESSAGE_TAG_NOT_FOUND
                + " address book.";

        assertCommandFailure(removeCommand, model, expectedMessage);
    }

    @Test
    public void execute_validTagValidIndexUnfilteredList_success() throws Exception {
        Tag tagToRemove = new Tag("friends");

        RemoveCommand removeCommand = prepareCommand(tagToRemove, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_SUCCESS + " from index "
                + INDEX_FIRST_PERSON.getOneBased() + ".", tagToRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagToRemove, INDEX_FIRST_PERSON);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagInvalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        RemoveCommand removeCommand = prepareCommand(tagToRemove, outOfBoundIndex);
        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTagValidIndexUnfilteredList_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("enemy");

        RemoveCommand removeCommand = prepareCommand(tagToRemove, INDEX_FIRST_PERSON);
        assertCommandFailure(removeCommand, model, "Tag: " + tagToRemove.toString()
                + RemoveCommand.MESSAGE_TAG_NOT_FOUND + " index " + INDEX_FIRST_PERSON.getOneBased() + ".");
    }

    @Test
    public void equals() throws Exception {
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("owesMoney");
        RemoveCommand removeFirstCommand = new RemoveCommand(firstTag,null);
        RemoveCommand removeSecondCommand = new RemoveCommand(secondTag,null);
        RemoveCommand removeThirdCommand = new RemoveCommand(firstTag, INDEX_FIRST_PERSON);
        RemoveCommand removeFourthCommand = new RemoveCommand(firstTag,INDEX_SECOND_PERSON);
        RemoveCommand removeFifthCommand = new RemoveCommand(secondTag,INDEX_FIRST_PERSON);

        // same object -> returns true (no Index)
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same object -> returns true (with Index)
        assertTrue(removeThirdCommand.equals(removeThirdCommand));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different tag -> returns false (no Index)
        assertFalse(removeFirstCommand.equals(removeSecondCommand));

        // different tag -> returns false (with Index)
        assertFalse(removeThirdCommand.equals(removeFifthCommand));

        // different index -> returns false (same Tag)
        assertFalse(removeThirdCommand.equals(removeFourthCommand));

        // different index -> returns false (different Tag)
        assertFalse(removeFourthCommand.equals(removeFifthCommand));
    }


    /**
     * Returns a {@code RemoveCommand} with the parameter {@code tag}.
     */
    private RemoveCommand prepareCommand(Tag tag, Index index) {
        RemoveCommand removeCommand = new RemoveCommand(tag, index);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
