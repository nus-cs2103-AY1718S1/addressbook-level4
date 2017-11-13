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

//@@author nadhira15
/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */

public class DeleteTagCommandTest {

    public static final String VALID_TAG = "friends";
    public static final String VALID_TAG_2 = "neighbours";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withTags("friends").build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand deleteTagCommand = prepareCommand(indexFirstPerson, tag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withTags(VALID_TAG).build();
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagUnfilteredList_failure() throws Exception {
        Index indexFirstPerson = INDEX_FIRST_PERSON;
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2));
        DeleteTagCommand deleteTagCommand = prepareCommand(indexFirstPerson, tag);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXIST_TAG);
    }

    @Test
    public void execute_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG_2));
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, tag);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXIST_TAG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, tag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Set<Tag> tag = ParserUtil.parseTags(Arrays.asList(VALID_TAG));
        Set<Tag> otherTag = ParserUtil.parseTags(Arrays.asList("husband"));
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, tag);

        // same values -> returns true
        Set<Tag> copyTag = tag;
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(INDEX_FIRST_PERSON, copyTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, tag)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON, otherTag)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteTagCommand prepareCommand(Index index, Set<Tag> tagToDelete) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, tagToDelete);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
