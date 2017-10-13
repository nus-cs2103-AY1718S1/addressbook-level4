package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ParserUtil;
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
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {
    private static final String INVALID_TAG = "#friend";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indices;
    private Set<Tag> tags;

    /*@Test
    public void execute_unfilteredList_success() throws Exception {
        indices = new Index[]{INDEX_FIRST_PERSON, INDEX_SECOND_PERSON};
        tags = SampleDataUtil.getTagSet(VALID_TAG_FRIEND);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS);
        TagCommand tagCommand = prepareCommand(indices, tags);
        for (Index index : indices) {
            ReadOnlyPerson lastPerson = model.getFilteredPersonList().get();

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        }

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }*/
    
    /*@Test
    public void execute_filteredList_success() throws Exception {
        
    }*/

    @Test
    public void execute_unFilteredList_success() throws Exception {
        indices = new Index[]{INDEX_FIRST_PERSON};
        TagCommand tagCommand = prepareCommand(indices, tags);
        ReadOnlyPerson personInUnfilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        tags.addAll(personInUnfilteredList.getTags());
        //ReadOnlyPerson editedPersonTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInUnfilteredList).build();
        model.updatePersonTags(editedPerson, tags);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSONS_SUCCESS, editedPersonOne);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPersonOne);

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
}
