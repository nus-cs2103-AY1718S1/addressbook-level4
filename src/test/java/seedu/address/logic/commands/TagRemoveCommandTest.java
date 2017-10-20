package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESCR_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.DESCR_LUCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.TagAddCommand.TagAddDescriptor;
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagRemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /*
    @Test
    public void executeTagRemoveSinglePersonSuccess() throws Exception {
        showFirstPersonOnly(model);

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> existingTagSet = personInFilteredList.getTags();
        Set<Tag> singleTagSet = new HashSet<Tag>();
        String toRemoveS;
        Tag toRemove;

        if (existingTagSet.size() == 0) {
            Tag onlyTag = new Tag(VALID_TAG_HUSBAND);
            singleTagSet.add(onlyTag);
            TagAddDescriptor tagAddDescriptor = new TagAddDescriptor(personInFilteredList);
            tagAddDescriptor.setTags(singleTagSet);

            Person editedPerson = new PersonBuilder(personInFilteredList).withATags(VALID_TAG_HUSBAND).build();
            TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList, tagAddDescriptor);
            tagAddCommand.executeUndoableCommand();
            toRemove = onlyTag;
            toRemoveS = VALID_TAG_HUSBAND;
        } else {
            toRemove = (Tag) existingTagSet.toArray()[0];
            toRemoveS = toRemove.tagName;
        }
        singleTagSet.add(toRemove);

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withOutTag(toRemoveS).build();
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList, tagRemoveDescriptor);

        String expectedMessage = String.format(TagRemoveCommand.MESSAGE_REMOVE_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagRemoveCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList,
                new TagRemoveDescriptor(new PersonBuilder().withOutTag(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Remove a tag to a person in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList,
                new TagRemoveDescriptor(new PersonBuilder().withOutTag(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArrayList<Index> singlePersonIndexList1 = new ArrayList<>();
        singlePersonIndexList1.add(INDEX_FIRST_PERSON);
        ArrayList<Index> singlePersonIndexList2 = new ArrayList<>();
        singlePersonIndexList2.add(INDEX_SECOND_PERSON);
        final TagRemoveCommand standardCommand = new TagRemoveCommand(singlePersonIndexList1, DESCR_JAMES);

        // same values -> returns true
        TagRemoveDescriptor copyDescriptor = new TagRemoveDescriptor(DESCR_JAMES);
        TagRemoveCommand commandWithSameValues = new TagRemoveCommand(singlePersonIndexList1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagRemoveCommand(singlePersonIndexList2, DESCR_JAMES)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TagRemoveCommand(singlePersonIndexList1, DESCR_LUCY)));
    }

    @Test
    public void tagRemoveDescriptorTest()throws Exception {
        PersonBuilder personBuilder = new PersonBuilder();
        personBuilder.withName("Name").withAddress("Address").withEmail("Email@email.com")
                .withPhone("123").withTags("Tags");
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor(personBuilder.build());
        TagRemoveDescriptor toCopy = new TagRemoveDescriptor(tagRemoveDescriptor);

        assertTrue(tagRemoveDescriptor.equals(toCopy));

        assertTrue(tagRemoveDescriptor.getName().equals(toCopy.getName()));

        assertTrue(tagRemoveDescriptor.getPhone().equals(toCopy.getPhone()));

        assertTrue(tagRemoveDescriptor.getAddress().equals(toCopy.getAddress()));

        assertTrue(tagRemoveDescriptor.getEmail().equals(toCopy.getEmail()));

        assertTrue(tagRemoveDescriptor.getTags().equals(toCopy.getTags()));
    }


    /**
     * Returns an {@code TagAddCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagAddCommand prepareCommand(ArrayList<Index> index, TagAddDescriptor descriptor) {
        TagAddCommand tagAddCommand = new TagAddCommand(index, descriptor);
        tagAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagAddCommand;
    }

    /**
     * Returns an {@code TagAddCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagRemoveCommand prepareCommand(ArrayList<Index> index, TagRemoveDescriptor descriptor) {
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(index, descriptor);
        tagRemoveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagRemoveCommand;
    }

}
