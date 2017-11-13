package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    private static final String VALID_TAG_1 = "goodFriends";
    private static final String VALID_TAG_2 = "classmates";
    private static final String VALID_TAG_3 = "friends";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code index} and {@code tagsToRemove}
     */
    private RemoveTagCommand prepareCommand(Index index, Set<Tag> tagsToRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, tagsToRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(lastPerson).withTags("neighbours").build();
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2, VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(indexLastPerson, tags);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withTags("neighbours").build();
        Set<Tag> tags = ParserUtil.parseTags(Collections.singletonList(VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(INDEX_FIRST_PERSON, tags);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNotFoundUnfilteredList_failure() throws Exception {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2, VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND + VALID_TAG_2);
    }

    @Test
    public void execute_tagNotFoundFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2, VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(INDEX_FIRST_PERSON, tags);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND + VALID_TAG_2);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2, VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(outOfBoundIndex, tags);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2, VALID_TAG_3));
        RemoveTagCommand removeTagCommand = prepareCommand(outOfBoundIndex, tags);
        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Set<Tag> tags = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> tagsOther = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_3));

        final RemoveTagCommand standardCommand = new RemoveTagCommand(INDEX_FIRST_PERSON, tags);

        // same values -> returns true
        Set<Tag> copyTags = tags;
        RemoveTagCommand commandWithSameValues = new RemoveTagCommand(INDEX_FIRST_PERSON, copyTags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tags)));

        // different tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, tagsOther)));
    }
}
