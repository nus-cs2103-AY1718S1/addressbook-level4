package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstAndSecondPersonsOnly;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_INVALID_INDEXES;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_SUCCESS_ALL_TAGS;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_SUCCESS_ALL_TAGS_IN_LIST;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST;
import static seedu.address.logic.commands.UntagCommand.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.PersonBuilder;

public class UntagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstUntaggedPerson = firstPersonInList.withTags().build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondUntaggedPerson = secondPersonInList.withTags("owesMoney").build();
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("retrieveTester");
        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), Arrays.asList(firstTag, secondTag));

        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, 2,
                firstTag.toString() + ", " + secondTag.toString()) + " "
                + firstUntaggedPerson.getName().toString() + ", " + secondUntaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(firstTag);
        expectedModel.deleteUnusedTag(secondTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListContainsPersonsWithoutTag_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstUntaggedPerson = firstPersonInList.withTags("friends", "retrieveTester").build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondUntaggedPerson = secondPersonInList.withTags("friends", "retrieveTester").build();
        Tag firstTag = new Tag("owesMoney");
        Tag secondTag = new Tag("retrieveTester");
        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), Arrays.asList(firstTag));

        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, 1, firstTag.toString()) + " "
                + secondUntaggedPerson.getName().toString() + "\n"
                + String.format(UntagCommand.MESSAGE_PERSONS_DO_NOT_HAVE_TAGS, 1) + " "
                + firstUntaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(firstTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstUntaggedPerson = new PersonBuilder(firstPersonInList).withTags().build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondUntaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney").build();
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("retrieveTester");
        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), Arrays.asList(firstTag, secondTag));

        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, 2,
                firstTag.toString() + ", " + secondTag.toString()) + " "
                + firstUntaggedPerson.getName().toString() + ", " + secondUntaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        showFirstAndSecondPersonsOnly(expectedModel);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(firstTag);
        expectedModel.deleteUnusedTag(secondTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListContainsPersonsWithoutTag_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstUntaggedPerson = new PersonBuilder(firstPersonInList).withTags("friends",
                "retrieveTester").build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondUntaggedPerson = new PersonBuilder(secondPersonInList).withTags("friends",
                "retrieveTester").build();
        Tag firstTag = new Tag("owesMoney");
        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), Arrays.asList(firstTag));

        String expectedMessage = String.format(UntagCommand.MESSAGE_SUCCESS, 1, firstTag.toString()) + " "
                + secondUntaggedPerson.getName().toString() + "\n"
                + String.format(UntagCommand.MESSAGE_PERSONS_DO_NOT_HAVE_TAGS, 1) + " "
                + firstUntaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        showFirstAndSecondPersonsOnly(expectedModel);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(firstTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allTagsInUnfilteredList_success() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());

        for (ReadOnlyPerson person : model.getFilteredPersonList()) {
            Person untaggedPerson = new PersonBuilder(person).withTags().build();
            expectedModel.updatePerson(person, untaggedPerson);
            for (Tag tag : person.getTags()) {
                expectedModel.deleteUnusedTag(tag);
            }
        }

        UntagCommand command = prepareCommand(true, Collections.emptyList(), Collections.emptyList());

        String expectedMessage = MESSAGE_SUCCESS_ALL_TAGS_IN_LIST;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_severalTagsInUnfilteredList_success() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());

        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("retrieveTester");
        for (ReadOnlyPerson person : model.getFilteredPersonList()) {
            Person untaggedPerson = new PersonBuilder(person).build();
            UniqueTagList updatedTags = new UniqueTagList(untaggedPerson.getTags());
            updatedTags.remove(firstTag);
            updatedTags.remove(secondTag);
            untaggedPerson.setTags(updatedTags.toSet());
            expectedModel.updatePerson(person, untaggedPerson);
        }

        expectedModel.deleteUnusedTag(firstTag);
        expectedModel.deleteUnusedTag(secondTag);

        UntagCommand command = prepareCommand(true,
                Collections.emptyList(), Arrays.asList(firstTag, secondTag));

        String expectedMessage = String.format(MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST,
                firstTag.toString() + ", " + secondTag.toString());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allTagsInFilteredList_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstUntaggedPerson = new PersonBuilder(firstPersonInList).withTags().build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondUntaggedPerson = new PersonBuilder(secondPersonInList).withTags().build();
        UntagCommand command = prepareCommand(true, Collections.emptyList(), Collections.emptyList());

        String expectedMessage = MESSAGE_SUCCESS_ALL_TAGS_IN_LIST;

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        showFirstAndSecondPersonsOnly(expectedModel);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(new Tag("friends"));
        expectedModel.deleteUnusedTag(new Tag("owesMoney"));
        expectedModel.deleteUnusedTag(new Tag("retrieveTester"));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_severalTagsInFilteredList_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstUntaggedPerson = new PersonBuilder(firstPersonInList).withTags().build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondUntaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney").build();
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("retrieveTester");
        UntagCommand command = prepareCommand(true, Collections.emptyList(),
                Arrays.asList(firstTag, secondTag));

        String expectedMessage = String.format(MESSAGE_SUCCESS_MULTIPLE_TAGS_IN_LIST,
                firstTag.toString() + ", " + secondTag.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        showFirstAndSecondPersonsOnly(expectedModel);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(new Tag("friends"));
        expectedModel.deleteUnusedTag(new Tag("retrieveTester"));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allTagsOfSelectedPersonsInList_success() throws Exception {
        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstUntaggedPerson = new PersonBuilder(firstPersonInList).withTags().build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondUntaggedPerson = new PersonBuilder(secondPersonInList).withTags().build();
        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), Collections.emptyList());

        String expectedMessage = String.format(MESSAGE_SUCCESS_ALL_TAGS, 2) + " "
                + firstUntaggedPerson.getName().toString() + ", " + secondUntaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstUntaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondUntaggedPerson);
        expectedModel.deleteUnusedTag(new Tag("friends"));
        expectedModel.deleteUnusedTag(new Tag("owesMoney"));
        expectedModel.deleteUnusedTag(new Tag("retrieveTester"));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexesUnfilteredList_failure() throws Exception {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UntagCommand command = prepareCommand(false, Arrays.asList(INDEX_FIRST_PERSON, outOfBound),
                Arrays.asList(new Tag("tagOne"), new Tag("tagTwo")));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidPersonIndexesFilteredList_failure() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        Index outOfBoundIndex = INDEX_THIRD_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UntagCommand command = prepareCommand(false, Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex),
                Arrays.asList(new Tag("friends"), new Tag("randomTag")));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_tagsNotFound_failure() throws Exception {
        Tag firstNotFoundTag = new Tag("tagOne");
        Tag secondNotFoundTag = new Tag("tagTwo");

        UntagCommand command = prepareCommand(false,
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                Arrays.asList(firstNotFoundTag, secondNotFoundTag));

        String expectedMessage = String.format(MESSAGE_TAG_NOT_FOUND,
                firstNotFoundTag.toString() + ", " + secondNotFoundTag.toString());

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() throws Exception {
        final List<Index> indexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final List<Tag> tagList = Arrays.asList(new Tag("friends"), new Tag("enemies"));
        final UntagCommand firstCommandCase = new UntagCommand(false,
                indexList, tagList);
        final UntagCommand secondCommandCase = new UntagCommand(false,
                indexList, Collections.emptyList());
        final UntagCommand thirdCommandCase = new UntagCommand(true,
                Collections.emptyList(), tagList);
        final UntagCommand fourthCommandCase = new UntagCommand(true,
                Collections.emptyList(), Collections.emptyList());

        // same values -> returns true
        final List<Index> indexListCopy = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final List<Tag> tagListCopy = Arrays.asList(new Tag("friends"), new Tag("enemies"));
        assertTrue(firstCommandCase.equals(new UntagCommand(false,
                indexListCopy, tagListCopy)));
        assertTrue(secondCommandCase.equals(new UntagCommand(false,
                indexListCopy, Collections.emptyList())));
        assertTrue(thirdCommandCase.equals(new UntagCommand(true,
                Collections.emptyList(), tagListCopy)));
        assertTrue(fourthCommandCase.equals(new UntagCommand(true,
                Collections.emptyList(), Collections.emptyList())));

        // same object -> returns true
        assertTrue(firstCommandCase.equals(firstCommandCase));
        assertTrue(secondCommandCase.equals(secondCommandCase));
        assertTrue(thirdCommandCase.equals(thirdCommandCase));
        assertTrue(fourthCommandCase.equals(fourthCommandCase));

        // null -> returns false
        assertFalse(firstCommandCase.equals(null));
        assertFalse(secondCommandCase.equals(null));
        assertFalse(thirdCommandCase.equals(null));
        assertFalse(fourthCommandCase.equals(null));

        // different types -> returns false
        assertFalse(firstCommandCase.equals(new ClearCommand()));
        assertFalse(secondCommandCase.equals(new ClearCommand()));
        assertFalse(thirdCommandCase.equals(new ClearCommand()));
        assertFalse(fourthCommandCase.equals(new ClearCommand()));

        // different index list -> returns false
        final List<Index> anotherIndexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        assertFalse(firstCommandCase.equals(new UntagCommand(false,
                anotherIndexList, tagList)));
        assertFalse(secondCommandCase.equals(new UntagCommand(false,
                anotherIndexList, Collections.emptyList())));

        // different tag -> returns false
        final List<Tag> anotherTagList = Arrays.asList(new Tag("friends"), new Tag("owesMoney"));
        assertFalse(firstCommandCase.equals(new UntagCommand(false,
                indexList, anotherTagList)));
        assertFalse(thirdCommandCase.equals(new UntagCommand(false,
                Collections.emptyList(), anotherTagList)));
    }

    /**
     * Returns an {@code UntagCommand}.
     */
    private UntagCommand prepareCommand(Boolean toAllPersonsInFilteredList, List<Index> indexes, List<Tag> tags) {
        UntagCommand command = new UntagCommand(toAllPersonsInFilteredList, indexes, tags);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
