package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
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
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author kenpaxtonlim
public class AddRemoveTagsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND, "friends").build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);

        AddRemoveTagsCommand addTagsCommand = prepareCommandAdd(INDEX_FIRST_PERSON, ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTags_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())).withTags().build();

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add("friends");

        AddRemoveTagsCommand removeTagsCommand = prepareCommandRemove(INDEX_FIRST_PERSON,
                ParserUtil.parseTags(tagsList));

        String expectedMessage = String.format(AddRemoveTagsCommand.MESSAGE_REMOVE_TAGS_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(removeTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        AddRemoveTagsCommand addRemoveTagsCommand = prepareCommandAdd(outOfBoundIndex, ParserUtil.parseTags(tagsList));

        assertCommandFailure(addRemoveTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<String> tagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        tagsList.add(VALID_TAG_FRIEND);
        Set<Tag> tags = ParserUtil.parseTags(tagsList);
        final AddRemoveTagsCommand standardCommand = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        AddRemoveTagsCommand commandWithSameValues = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON, tags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Return false with different type
        AddRemoveTagsCommand commandWithDifferentType = new AddRemoveTagsCommand(false, INDEX_FIRST_PERSON, tags);
        assertFalse(standardCommand.equals(commandWithDifferentType));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        AddRemoveTagsCommand commandWithDifferentIndex = new AddRemoveTagsCommand(true, INDEX_SECOND_PERSON,
                ParserUtil.parseTags(tagsList));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different tags
        ArrayList<String> differentTagsList = new ArrayList<>();
        tagsList.add(VALID_TAG_HUSBAND);
        AddRemoveTagsCommand commandWithDifferentTags = new AddRemoveTagsCommand(true, INDEX_FIRST_PERSON,
                ParserUtil.parseTags(differentTagsList));
        assertFalse(standardCommand.equals(commandWithDifferentTags));
    }

    /**
     * Returns an add {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandAdd(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(true, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }

    /**
     * Returns a remove {@code AddRemoveTagsCommand} with parameters {@code index} and {@code tags}.
     */
    private AddRemoveTagsCommand prepareCommandRemove(Index index, Set<Tag> tags) {
        AddRemoveTagsCommand addRemoveTagsCommand = new AddRemoveTagsCommand(false, index, tags);
        addRemoveTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addRemoveTagsCommand;
    }
}
