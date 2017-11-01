package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import seedu.address.model.tag.Tag;

//@@author NabeelZaheer
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class RemoveCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeValidTagAll_success() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        List<String> indexDisplay = new ArrayList<>();

        RemoveTagCommand removeCommand = prepareCommand(tagSet, indexSet, indexDisplay);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_SUCCESS
                        + " from address book.", tagSet);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagSet, indexDisplay);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeInvalidTagAll_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        List<String> indexDisplay = new ArrayList<>();

        RemoveTagCommand removeCommand = prepareCommand(tagSet, indexSet, indexDisplay);

        String expectedMessage =  String.format(
                RemoveTagCommand.MESSAGE_TAG_NOT_FOUND + " the address book.", tagSet);

        assertCommandFailure(removeCommand, model, expectedMessage);
    }

    @Test
    public void execute_validTagValidIndexUnfilteredList_success() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        indexSet.add(INDEX_FIRST_PERSON);
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add("1");

        RemoveTagCommand removeCommand = prepareCommand(tagSet, indexSet, indexDisplay);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_SUCCESS + " from index "
                + INDEX_FIRST_PERSON.getOneBased() + ".", tagSet);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagSet, indexDisplay);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagInvalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        indexSet.add(outOfBoundIndex);
        String indexString = String.valueOf(model.getFilteredPersonList().size() + 1);
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add(indexString);

        RemoveTagCommand removeCommand = prepareCommand(tagSet, indexSet, indexDisplay);
        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTagValidIndexUnfilteredList_throwsCommandException() throws Exception {
        Tag tagToRemove = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToRemove);
        indexSet.add(INDEX_FIRST_PERSON);
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add("1");

        RemoveTagCommand removeCommand = prepareCommand(tagSet, indexSet, indexDisplay);
        assertCommandFailure(removeCommand, model, String.format(
                RemoveTagCommand.MESSAGE_TAG_NOT_FOUND + " index: " + INDEX_FIRST_PERSON.getOneBased() + ".", tagSet));
    }

    @Test
    public void equals() throws Exception {
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("owesMoney");
        Set<Index> indexSet1 = new HashSet<>();
        Set<Tag> tagSet1 = new HashSet<>();
        tagSet1.add(firstTag);
        indexSet1.add(INDEX_FIRST_PERSON);
        Set<Index> indexSet2 = new HashSet<>();
        Set<Tag> tagSet2 = new HashSet<>();
        tagSet2.add(secondTag);
        indexSet2.add(INDEX_SECOND_PERSON);
        Set<Index> emptySet = new HashSet<>();
        List<String> indexDisplay = new ArrayList<>();
        indexDisplay.add("");
        List<String> indexDisplay1 = new ArrayList<>();
        indexDisplay1.add("1");
        List<String> indexDisplay2 = new ArrayList<>();
        indexDisplay2.add("2");
        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(tagSet1, emptySet, indexDisplay);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(tagSet2, emptySet, indexDisplay);
        RemoveTagCommand removeThirdCommand = new RemoveTagCommand(tagSet1, indexSet1, indexDisplay1);
        RemoveTagCommand removeFourthCommand = new RemoveTagCommand(tagSet1, indexSet2, indexDisplay2);
        RemoveTagCommand removeFifthCommand = new RemoveTagCommand(tagSet2, indexSet1, indexDisplay1);

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
     * Returns a {@code RemoveTagCommand}.
     */
    private RemoveTagCommand prepareCommand(Set<Tag> tag, Set<Index> index, List<String> indexDisplay) {
        RemoveTagCommand removeCommand = new RemoveTagCommand(tag, index, indexDisplay);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
