package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList(" "));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS);
//        List<ReadOnlyPerson> expectedModel = model.getFilteredPersonList();
        RemoveTagCommand command = prepareCommand(prepareTagList("colleagues criminal"));
        assertCommandSuccess(command, model, expectedMessage,expectedModel);
    }

    @Test
    public void execute_tagsNotExist_noTagsRemoved() throws Exception {
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList("nothing"));
//        List<ReadOnlyPerson> expectedModel = model.getFilteredPersonList();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        RemoveTagCommand firstCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        RemoveTagCommand secondCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_HUSBAND));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        RemoveTagCommand firstCommandCopy = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns an {@code ArrayList<Tag>}
     */
    private ArrayList<Tag> prepareTagList(String userInput) throws Exception {
        ArrayList<Tag> tagToRemove = new ArrayList<>();
        String[] tagKeywords = userInput.split("\\s+");

        for (int i = 0; i < tagKeywords.length; i++) {
            tagToRemove.add(new Tag(tagKeywords[i]));
        }

        return tagToRemove;
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code arraylist of tags}
     */
    private RemoveTagCommand prepareCommand(ArrayList<Tag> tagToRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

}
