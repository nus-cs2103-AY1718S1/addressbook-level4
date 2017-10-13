package seedu.address.logic.commands;
;
import static org.junit.Assert.assertTrue;;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indices;
    private Set<Tag> tags;

    @Test
    public void execute_unfilteredList_success() throws Exception {
        indices = new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        //testing for change in person tags in first index
        ReadOnlyPerson personInUnfilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tags = combineTags(personInUnfilteredList, VALID_TAG_FRIEND);
        Person editedPerson = new PersonBuilder(personInUnfilteredList).build();
        model.updatePersonTags(editedPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);

        //testing for change in person tags in second index
        ReadOnlyPerson personInUnfilteredListTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        tags = combineTags(personInUnfilteredListTwo, VALID_TAG_FRIEND);
        Person editedPersonTwo = new PersonBuilder(personInUnfilteredListTwo).build();
        model.updatePersonTags(editedPersonTwo, tags);

        Model expectedModelTwo = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModelTwo.updatePerson(model.getFilteredPersonList().get(1), editedPersonTwo);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModelTwo);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        indices = new Index[]{INDEX_FIRST_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tags = combineTags(personInFilteredList, VALID_TAG_FRIEND);
        Person editedPerson = new PersonBuilder(personInFilteredList).build();
        model.updatePersonTags(editedPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        indices = new Index[]{outOfBoundIndex, INDEX_FIRST_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        indices = new Index[]{outOfBoundIndex};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);
        TagCommand tagCommand = prepareCommand(indices, tags);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code TagCommand} with parameters {@code indices} and {@code tagList}
     */
    private TagCommand prepareCommand(Index[] indices, Set<Tag> tagList) {
        TagCommand tagCommand = new TagCommand(indices, tagList);
        tagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagCommand;
    }

    /**
     * Returns a combined set of tags from old tags of a {@code person} and {@code newTags}
     */
    private Set<Tag> combineTags(ReadOnlyPerson person, String... newTags) throws Exception {
        Set<Tag> allTags = new HashSet<>();
        for (String tag : newTags) {
            allTags = SampleDataUtil.getTagSet(tag);
        }
        allTags.addAll(person.getTags());
        return allTags;
    }
}
