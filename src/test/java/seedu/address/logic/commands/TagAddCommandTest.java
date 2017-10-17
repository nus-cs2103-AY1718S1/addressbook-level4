package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LUCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.TagAddCommand.TagAddDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_tagAdd_singlePerson_success() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> singleTagSet = new HashSet<Tag>();
        singleTagSet.add(new Tag(VALID_TAG_HUSBAND));

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor(personInFilteredList);
        tagAddDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withATags(VALID_TAG_HUSBAND).build();
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList, tagAddDescriptor);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_ADD_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagAddCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Add tag to a person in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> singlePersonIndexList1 = new ArrayList<>();
        singlePersonIndexList1.add(INDEX_FIRST_PERSON);
        ArrayList<Index> singlePersonIndexList2 = new ArrayList<>();
        singlePersonIndexList2.add(INDEX_SECOND_PERSON);
        final TagAddCommand standardCommand = new TagAddCommand(singlePersonIndexList1, DESC_JAMES);

        // same values -> returns true
        TagAddDescriptor copyDescriptor = new TagAddDescriptor(DESC_JAMES);
        TagAddCommand commandWithSameValues = new TagAddCommand(singlePersonIndexList1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList2, DESC_JAMES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList1, DESC_LUCY)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagAddCommand prepareCommand(ArrayList<Index> index, TagAddDescriptor descriptor) {
        TagAddCommand tagAddCommand = new TagAddCommand(index, descriptor);
        tagAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagAddCommand;
    }

}
