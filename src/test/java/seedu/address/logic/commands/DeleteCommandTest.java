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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //@@author April0616
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();
        deletePersonList.add(personToDelete);

        String expectedMessage = deleteCommand.generateSuccessfulResultMsgForPerson(deletePersonList);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author nbriannl
    @Test
    public void execute_validTagUnfilteredList_success() throws Exception {
        Set<Tag> tagsToDelete = Stream.of(new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(tagsToDelete);

        ArrayList<Tag> arrayTags = new ArrayList<>();
        arrayTags.addAll(tagsToDelete);

        String expectedMessage = DeleteCommand.generateResultMsgForTag(arrayTags);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(new Tag("friends"));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author nbriannl
    @Test
    public void execute_invalidTagUnfilteredList_throwsCommandException() throws Exception {
        Set<Tag> wrongTag = Stream.of(new Tag("nonexistent")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(wrongTag);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);

        Set<Tag> someWrongTags = Stream.of(new Tag("wrongtag"), new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand2 = prepareCommand(someWrongTags);
        assertCommandFailure(deleteCommand2, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);
    }

    //@@author April0616
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();
        deletePersonList.add(personToDelete);

        String expectedMessage = deleteCommand.generateSuccessfulResultMsgForPerson(deletePersonList);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author nbriannl
    @Test
    public void execute_validTagFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> tagsToDelete = Stream.of(new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(tagsToDelete);

        ArrayList<Tag> arrayTags = new ArrayList<>();
        arrayTags.addAll(tagsToDelete);

        String expectedMessage = DeleteCommand.generateResultMsgForTag(arrayTags);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.deleteTag(new Tag("friends"));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //@@author nbriannl
    @Test
    public void execute_invalidTagFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> wrongTag = Stream.of(new Tag("nonexistent")).collect(Collectors.toSet());
        DeleteCommand deleteCommand = prepareCommand(wrongTag);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);

        Set<Tag> someWrongTags = Stream.of(new Tag("wrongtag"), new Tag("friends")).collect(Collectors.toSet());
        DeleteCommand deleteCommand2 = prepareCommand(someWrongTags);
        assertCommandFailure(deleteCommand2, model, Messages.MESSAGE_INVALID_TAG_PROVIDED);
    }

    //@@author nbriannl
    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand == null);

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        Set<Tag> firstSet = Stream.of(new Tag("word")).collect(Collectors.toSet());
        Set<Tag> secondSet = Stream.of(new Tag("other")).collect(Collectors.toSet());
        Set<Tag> thirdSet = Stream.of(new Tag("multiple"), new Tag("words")).collect(Collectors.toSet());

        DeleteCommand deleteFirstCommandTags = new DeleteCommand(firstSet);
        DeleteCommand deleteSecondCommandTags = new DeleteCommand(secondSet);
        DeleteCommand deleteThirdCommandTags = new DeleteCommand(thirdSet);

        // same object -> returns true
        assertTrue(deleteFirstCommandTags.equals(deleteFirstCommandTags));

        // same values -> returns true
        DeleteCommand deleteFirstCommandTagCopy = new DeleteCommand(firstSet);
        assertTrue(deleteFirstCommandTags.equals(deleteFirstCommandTagCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandTags.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandTags == null);

        // different tag -> returns false
        assertFalse(deleteFirstCommandTags.equals(deleteSecondCommandTags));
        assertFalse(deleteFirstCommandTags.equals(deleteThirdCommandTags));
    }

    //@@author
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    //@@author nbriannl
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code tagSet}.
     */
    private DeleteCommand prepareCommand(Set<Tag> tagSet) {
        DeleteCommand deleteCommand = new DeleteCommand(tagSet);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    //@@author
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
