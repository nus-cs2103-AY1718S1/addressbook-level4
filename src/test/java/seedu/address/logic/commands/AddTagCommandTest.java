package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        tags.add(newTag);
        Person editedPerson = new Person(readOnlyPerson);
        editedPerson.setTags(tags);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        Tag existingTag = tags.iterator().next();
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);
        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_invalidTagName_throwsCommandException() throws Exception {
        String tagName = "buddy!";
        try {
            Tag newTag = new Tag(tagName);
            AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);
        } catch (IllegalValueException ive) {
            assertEquals(null, ive.getMessage(), Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(personInFilteredList.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        tags.add(newTag);
        Person editedPerson = new Person(personInFilteredList);
        editedPerson.setTags(tags);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        String tagName = "buddy";
        // ensures that outOfBoundIndex is still in bounds of address book list
        Tag newTag = new Tag(tagName);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Tag tagOne = new Tag("friend");
        Tag tagTwo = new Tag("husband");
        AddTagCommand firstTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, tagOne);
        AddTagCommand secondTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, tagTwo);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, tagOne);
        assertTrue(firstTagCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(firstTagCommand.equals(firstTagCommand));

        // null -> returns false
        assertFalse(firstTagCommand.equals(null));

        // different types -> returns false
        assertFalse(firstTagCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(firstTagCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tagOne)));

        // different descriptor -> returns false
        assertFalse(firstTagCommand.equals(secondTagCommand));
    }


    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tagName}.
     */
    private AddTagCommand prepareCommand(Index index, Tag tagName) throws Exception {
        AddTagCommand addTagCommand = new AddTagCommand(index, tagName);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}
