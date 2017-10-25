package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
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


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidTagValidIndex_success() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);

        AddTagCommand addTagCommand = prepareCommand(tagSet, indexSet);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADDED_SUCCESS + " to index "
                + "1.", tagSet);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagSet, indexSet);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addValidOutOfBoundsIndex_throwsCommandException() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(Index.fromOneBased(model.getFilteredPersonList().size() + 1));

        AddTagCommand addTagCommand = prepareCommand(tagSet, indexSet);

        String expectedMessage =  Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(addTagCommand, model, expectedMessage);
    }

    /* @Test
    public void execute_validTagMultipleIndexes_success() throws Exception {
        Tag tagToAdd = new Tag("enemy");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);
        indexSet.add(INDEX_SECOND_PERSON);

        AddTagCommand addTagCommand = prepareCommand(tagSet, indexSet);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADDED_SUCCESS + " to index "
                + "1, 2.", tagSet);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagSet, indexSet);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_multipleTagsValidIndex_success() throws Exception {
        Tag tagToAdd1 = new Tag("enemy");
        Tag tagToAdd2 = new Tag("brother");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd1);
        tagSet.add(tagToAdd2);
        indexSet.add(INDEX_FIRST_PERSON);

        AddTagCommand addTagCommand = prepareCommand(tagSet, indexSet);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADDED_SUCCESS + " to index "
                + "1.", tagSet);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagSet, indexSet);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTag_throwsCommandException() throws Exception {
        Tag tagToAdd = new Tag("friends");
        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tagToAdd);
        indexSet.add(INDEX_FIRST_PERSON);

        AddTagCommand addTagCommand = prepareCommand(tagSet, indexSet);
        assertCommandFailure(addTagCommand, model, String.format(
                AddTagCommand.MESSAGE_DUPLICATE_TAG + " index: " + INDEX_FIRST_PERSON.getOneBased()
                        + ".", tagSet));
    }

    @Test
    public void equals() throws Exception {
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("owesMoney");
        Set<Index> indexSet1 = new HashSet<>();
        Set<Index> indexSet2 = new HashSet<>();
        Set<Tag> tagSet1 = new HashSet<>();
        Set<Tag> tagSet2 = new HashSet<>();
        tagSet1.add(firstTag);
        tagSet2.add(secondTag);
        indexSet1.add(INDEX_FIRST_PERSON);
        indexSet2.add(INDEX_SECOND_PERSON);

        AddTagCommand addTagCommand1 = new AddTagCommand(tagSet1, indexSet1);
        AddTagCommand addTagCommand2 = new AddTagCommand(tagSet1, indexSet2);
        AddTagCommand addTagCommand3 = new AddTagCommand(tagSet2, indexSet1);

        // same object -> returns true
        assertTrue(addTagCommand1.equals(addTagCommand1));

        // different types -> returns false
        assertFalse(addTagCommand1.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(addTagCommand1.equals(null));

        // different tag -> returns false (same Index)
        assertFalse(addTagCommand1.equals(addTagCommand3));

        // different index -> returns false (same Tag)
        assertFalse(addTagCommand1.equals(addTagCommand2));

        // different index -> returns false (different Tag)
        assertFalse(addTagCommand2.equals(addTagCommand3));
    }


    /**
     * Returns a {@code AddTagCommand}.
     */
    private AddTagCommand prepareCommand(Set<Tag> tag, Set<Index> index) {
        AddTagCommand addTagCommand = new AddTagCommand(tag, index);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}
