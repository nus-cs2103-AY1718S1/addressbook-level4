package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagOnePerson_success() throws Exception {
        String tagName = "owesMoney";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagTwoPersons_success() throws Exception {
        String tagName = "friends";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagDoesNotExist_throwsCommandException() throws Exception {
        String tagName = "robot";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED);
    }

    @Test
    public void execute_invalidTagFormat_throwsCommandException() throws Exception {
        String tagName = "hi there";
        try {
            Tag newTag = new Tag(tagName);
            prepareCommand(newTag);
            fail("Expected IllegalValueException to be thrown");
        } catch (IllegalValueException ive) {
            assertEquals(null, ive.getMessage(), "Tags names should be alphanumeric");
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        Tag existingTag = tags.iterator().next();
        tags.remove(existingTag);
        Person editedPerson = new Person(readOnlyPerson);
        editedPerson.setTags(tags);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, existingTag.tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTag_throwsCommandException() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);
        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NO_TAG);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(personInFilteredList.getTags());
        Tag existingTag = tags.iterator().next();
        tags.remove(existingTag);
        Person editedPerson = new Person(personInFilteredList);
        editedPerson.setTags(tags);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, existingTag.tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Tag tagOne = new Tag("friend");
        Tag tagTwo = new Tag("husband");

        DeleteTagCommand deleteFirstCommand = prepareCommand(tagOne);
        DeleteTagCommand deleteSecondCommand = prepareCommand(tagTwo);
        DeleteTagCommand deleteFirstCommandIndex = prepareCommand(INDEX_FIRST_PERSON, tagOne);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = prepareCommand(tagOne);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        DeleteTagCommand deleteFirstCommandIndexCopy = prepareCommand(INDEX_FIRST_PERSON, tagOne);
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndexCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, tagOne)));
    }

    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tagName}.
     */
    private DeleteTagCommand prepareCommand(Tag tagName) throws Exception {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagName);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }

    private DeleteTagCommand prepareCommand(Index index, Tag tagName) throws Exception {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, tagName);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
