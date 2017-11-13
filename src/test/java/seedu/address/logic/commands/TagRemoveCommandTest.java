package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESCR_JAMES;
import static seedu.address.logic.commands.CommandTestUtil.DESCR_LUCY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.TagRemoveCommand.TagRemoveDescriptor;
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
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class TagRemoveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void executeTagRemoveSinglePersonFailure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> singleTagSet = new HashSet<>();
        Tag onlyTag = new Tag(VALID_TAG_HUSBAND);
        singleTagSet.add(onlyTag);

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(singleTagSet);

        Person editedPerson = new PersonBuilder(personInFilteredList).withOutTag(VALID_TAG_HUSBAND).build();
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList, tagRemoveDescriptor);

        String expectedMessage = String.format(TagRemoveCommand.MESSAGE_TAG_NOT_FOUND, onlyTag.toString());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new AddressBook(),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(tagRemoveCommand, model, expectedMessage);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(outOfBoundIndex);
        TagRemoveCommand tagRemoveCommand = prepareCommand(singlePersonIndexList,
                new TagRemoveDescriptor(new PersonBuilder().withOutTag(VALID_TAG_HUSBAND).build()));

        assertCommandFailure(tagRemoveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

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
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag("Tags"));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setName(new Name("Name"));
        tagRemoveDescriptor.setAddress(new Address("Address"));
        tagRemoveDescriptor.setEmail(new Email("Email@email.com"));
        tagRemoveDescriptor.setPhone(new Phone("123"));
        tagRemoveDescriptor.setTags(tagSet);

        TagRemoveDescriptor toCopy = new TagRemoveDescriptor(tagRemoveDescriptor);

        assertTrue(tagRemoveDescriptor.equals(tagRemoveDescriptor));

        assertTrue(tagRemoveDescriptor.equals(toCopy));

        assertTrue(tagRemoveDescriptor.getName().equals(toCopy.getName()));

        assertTrue(tagRemoveDescriptor.getPhone().equals(toCopy.getPhone()));

        assertTrue(tagRemoveDescriptor.getAddress().equals(toCopy.getAddress()));

        assertTrue(tagRemoveDescriptor.getEmail().equals(toCopy.getEmail()));

        assertTrue(tagRemoveDescriptor.getTags().equals(toCopy.getTags()));

        assertFalse(tagRemoveDescriptor == null);
    }

    @Test
    public void createEditedPersonTestSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> singleTagSet = new HashSet<Tag>();
        Tag onlyTag = new Tag(VALID_TAG_HUSBAND);
        singleTagSet.add(onlyTag);

        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor(personInFilteredList);
        tagRemoveDescriptor.setTags(singleTagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        Person person = tagRemoveCommand.createEditedPerson(personInFilteredList, tagRemoveDescriptor);

        assertTrue(person.getName().equals(personInFilteredList.getName()));
        assertTrue(person.getPhone().equals(personInFilteredList.getPhone()));
        assertTrue(person.getAddress().equals(personInFilteredList.getAddress()));
        assertTrue(person.getEmail().equals(personInFilteredList.getEmail()));
        assertTrue(person.getTags().equals(tagRemoveDescriptor.getTags()));
    }

    @Test
    public void containsTagTestSuccess()throws Exception {
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(VALID_TAG_FRIEND));
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        assertTrue(tagRemoveCommand.containsTag(tagList));
    }

    @Test
    public void containsTagTestFail()throws Exception {
        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        assertFalse(tagRemoveCommand.containsTag(tagList));
    }

    @Test
    public void makeFullIndexListTestSuccess()throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(personInFilteredList);

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        ArrayList<Index> indexList = tagRemoveCommand.makeFullIndexList(personList.size());
        assertTrue(indexList.size() == personList.size());
    }

    @Test
    public void createModifiableTagSetTest()throws Exception {
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> originalTagSet = personInFilteredList.getTags();
        List<ReadOnlyPerson> personList = new ArrayList<>();
        personList.add(personInFilteredList);

        ArrayList<Index> singlePersonIndexList = new ArrayList<>();
        singlePersonIndexList.add(INDEX_FIRST_PERSON);
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));
        TagRemoveDescriptor tagRemoveDescriptor = new TagRemoveDescriptor();
        tagRemoveDescriptor.setTags(tagSet);
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(singlePersonIndexList, tagRemoveDescriptor);
        Set<Tag> tagSetCopy = tagRemoveCommand.createModifiableTagSet(originalTagSet, new Tag("tag"));
        assertTrue(tagSetCopy.equals(originalTagSet));
    }

    /**
     * Returns an {@code TagRemoveCommand} with parameters {@code index} and {@code descriptor}
     */
    private TagRemoveCommand prepareCommand(ArrayList<Index> index, TagRemoveDescriptor descriptor) {
        TagRemoveCommand tagRemoveCommand = new TagRemoveCommand(index, descriptor);
        tagRemoveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagRemoveCommand;
    }
}
