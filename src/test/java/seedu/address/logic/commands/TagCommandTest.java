//@@author duyson98

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstAndSecondPersonsOnly;
import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEXES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
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
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstTaggedPerson = firstPersonInList.withTags("friends", "retrieveTester", "tagTester").build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondTaggedPerson = secondPersonInList.withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        Tag tag = new Tag("tagTester");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 2, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + ", "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListContainsPersonsAlreadyTagged_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstTaggedPerson = firstPersonInList.withTags("friends", "retrieveTester", "owesMoney").build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondTaggedPerson = secondPersonInList.withTags("owesMoney", "friends", "retrieveTester").build();
        Tag tag = new Tag("owesMoney");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 1, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + "\n"
                + String.format(TagCommand.MESSAGE_PERSONS_ALREADY_HAVE_TAG, 1) + " "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstTaggedPerson = new PersonBuilder(firstPersonInList).withTags("friends",
                "retrieveTester", "tagTester").build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondTaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        Tag tag = new Tag("tagTester");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 2, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + ", "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListContainsPersonsAlreadyTagged_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstTaggedPerson = new PersonBuilder(firstPersonInList).withTags("friends",
                "retrieveTester", "owesMoney").build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondTaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney", "friends",
                "retrieveTester", "friends").build();
        Tag tag = new Tag("owesMoney");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 1, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + "\n"
                + String.format(TagCommand.MESSAGE_PERSONS_ALREADY_HAVE_TAG, 1) + " "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexesUnfilteredList_failure() throws Exception {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, outOfBound), new Tag("tagTester"));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidPersonIndexesFilteredList_failure() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        Index outOfBoundIndex = INDEX_THIRD_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex), new Tag("tagTester"));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void equals() throws Exception {
        final List<Index> indexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final Tag tag = new Tag("dummyTag");
        final TagCommand command = new TagCommand(indexList, tag);

        // same values -> returns true
        final List<Index> indexListCopy = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final Tag tagCopy = new Tag("dummyTag");
        assertTrue(command.equals(new TagCommand(indexListCopy, tagCopy)));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different types -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different index list -> returns false
        final List<Index> anotherIndexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        assertFalse(command.equals(new TagCommand(anotherIndexList, tag)));

        // different tag -> returns false
        final Tag anotherTag = new Tag("anotherTag");
        assertFalse(command.equals(new TagCommand(indexList, anotherTag)));
    }

    /**
     * Returns an {@code TagCommand}.
     */
    private TagCommand prepareCommand(List<Index> indexes, Tag tag) {
        TagCommand command = new TagCommand(indexes, tag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
