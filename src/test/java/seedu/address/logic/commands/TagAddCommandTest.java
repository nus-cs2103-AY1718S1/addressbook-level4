package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LUCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author ZhangH795

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagAddCommand.
 */
public class TagAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void executeTagAddSinglePerson() throws Exception {
        showFirstPersonOnly(model);

        Set<Tag> singleTagSet = new HashSet<>();
        singleTagSet.add(new Tag(VALID_TAG_HUSBAND));

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor(personInFilteredList);
        tagAddDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withATags(VALID_TAG_HUSBAND).build();
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList, tagAddDescriptor);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(tagAddCommand, model, createTagListDisplay(editedPerson), expectedModel);

        tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));
        assertCommandFailure(tagAddCommand, model, String.format(TagAddCommand.MESSAGE_TAG_ALREADY_EXISTS,
                "[" + VALID_TAG_HUSBAND + "]"));
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagAddCommand tagAddCommand = prepareCommand(singlePersonIndexList,
                new TagAddDescriptor(new PersonBuilder().withATags(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds tag to a person in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() {
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

        TagAddDescriptor copyDescriptor = new TagAddDescriptor(DESC_JAMES);
        TagAddDescriptor copyDescriptor1 = new TagAddDescriptor(DESC_LUCY);
        TagAddCommand commandWithSameValues = new TagAddCommand(singlePersonIndexList1, copyDescriptor);
        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same object -> returns true
        assertTrue(copyDescriptor.equals(copyDescriptor));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList2, DESC_JAMES)));

        // different command -> returns false
        assertFalse(standardCommand.equals(new TagAddCommand(singlePersonIndexList1, DESC_LUCY)));

        // different object -> returns false
        assertFalse(copyDescriptor.equals(standardCommand));

        // different descriptor -> returns false
        assertFalse(copyDescriptor.equals(copyDescriptor1));
    }

    @Test
    public void tagAddDescriptorTest()throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Tags"));
        TagAddDescriptor tagAddDescriptor = new TagAddDescriptor();
        tagAddDescriptor.setName(new Name("Name"));
        tagAddDescriptor.setAddress(new Address("Address"));
        tagAddDescriptor.setEmail(new Email("Email@email.com"));
        tagAddDescriptor.setPhone(new Phone("123"));
        tagAddDescriptor.setTags(tagSet);

        TagAddDescriptor toCopy = new TagAddDescriptor(tagAddDescriptor);

        assertTrue(tagAddDescriptor.equals(toCopy));

        assertTrue(tagAddDescriptor.getName().equals(toCopy.getName()));

        assertTrue(tagAddDescriptor.getPhone().equals(toCopy.getPhone()));

        assertTrue(tagAddDescriptor.getAddress().equals(toCopy.getAddress()));

        assertTrue(tagAddDescriptor.getEmail().equals(toCopy.getEmail()));

        assertTrue(tagAddDescriptor.getTags().equals(toCopy.getTags()));
    }

    /**
     * Creates string for edited tag list.
     * @param editedPerson edited person to show tag list
     * Returns formatted string to indicate edited tag list.
     */
    public String createTagListDisplay(Person editedPerson) {
        int tagListStringStartIndex = 1;
        int tagListStringEndIndex;
        String tagChangedDisplayRaw = editedPerson.getTags().toString();
        tagListStringEndIndex = tagChangedDisplayRaw.length() - 1;
        String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);
        return String.format(TagAddCommand.MESSAGE_ADD_TAG_SUCCESS, tagChangedDisplay);
    }

    /**
     * Returns an {@code TagAddCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagAddCommand prepareCommand(ArrayList<Index> index, TagAddDescriptor descriptor) {
        TagAddCommand tagAddCommand = new TagAddCommand(index, descriptor);
        tagAddCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagAddCommand;
    }

}
