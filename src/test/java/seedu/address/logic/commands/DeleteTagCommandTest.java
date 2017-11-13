package seedu.address.logic.commands;

//@@author wangyiming1019
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.ArrayList;

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
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndexAndTagUnfilteredListSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagToDelete, indexes);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexAndTagFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.deleteTag(tagToDelete, indexes);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidTagUnfilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToDelete = new Tag("someone");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void executeInvalidTagFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("someone");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void executeValidTagNotInUnFilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("teacher");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("classmates");
        final DeleteTagCommand standardCommand = new DeleteTagCommand(firstTag, indexes1);

        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(firstTag, indexes1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(firstTag, indexes2)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(secondTag, indexes1)));
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code targetIndexes} and {@code tagToDelete}
     */
    private DeleteTagCommand prepareCommand(ArrayList<Index> targetIndexes, Tag tagToDelete) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagToDelete, targetIndexes);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}


