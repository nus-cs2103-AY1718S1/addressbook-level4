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
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndTagUnfilteredListSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToAdd = new Tag("teacher");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagToAdd);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagToAdd, indexes);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void executeValidIndexAndTagFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToAdd = new Tag("stranger");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagToAdd);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.addTag(tagToAdd, indexes);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidTagUnfilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void executeInvalidTagFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
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
        final AddTagCommand standardCommand = new AddTagCommand(firstTag, indexes1);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(firstTag, indexes1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(firstTag, indexes2)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(secondTag, indexes1)));
    }

    /**
     * Returns an {@code AddTagCommand} with parameters {@code targetIndexes} and {@code tagToAdd}
     */
    private AddTagCommand prepareCommand(Tag tagToAdd, ArrayList<Index> targetIndexes) {
        AddTagCommand addTagCommand = new AddTagCommand(tagToAdd, targetIndexes);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}


