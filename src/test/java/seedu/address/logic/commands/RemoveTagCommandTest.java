package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests success of an unfiltered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagUnfilteredList_success() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("owesMoney");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(indexes, toRemove);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests success of a filtered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagFilteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.removeTag(indexes, toRemove);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests failure of an unfiltered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    /**
     * Tests failure of a filtered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests failure of an unfiltered persons list with valid input indexes but a tag that doesn't exist
     */
    @Test
    public void execute_invalidTagUnfilteredList_failure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("hello");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_NO_SUCH_TAG);
    }

    /**
     * Tests failure of a filtered persons list with valid input indexes but a tag that doesn't exist
     */
    @Test
    public void execute_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toRemove = new Tag("hello");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_NO_SUCH_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("lecturer");
        final RemoveTagCommand standardCommand = new RemoveTagCommand(indexes1, firstTag);

        // same values -> returns true
        RemoveTagCommand commandWithSameValues = new RemoveTagCommand(indexes1, firstTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(indexes2, firstTag)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(indexes1, secondTag)));
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code targetIndexes} and {@code toRemove}
     */
    private RemoveTagCommand prepareCommand(ArrayList<Index> targetIndexes, Tag toRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(targetIndexes, toRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }
}
