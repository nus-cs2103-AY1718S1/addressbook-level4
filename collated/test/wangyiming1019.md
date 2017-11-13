# wangyiming1019
###### \java\seedu\address\logic\commands\AddTagCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndTagUnfilteredListSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToAdd = new Tag("teacher");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagToAdd);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(tagToAdd, indexes);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void executeValidIndexAndTagFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToAdd = new Tag("stranger");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagToAdd);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.addTag(tagToAdd, indexes);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidTagUnfilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void executeInvalidTagFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(tagToAdd, indexes);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("classmates");
        final AddTagCommand standardCommand = new AddTagCommand(firstTag, indexes1);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(firstTag, indexes1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(firstTag, indexes2)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(secondTag, indexes1)));
    }

    /**
     * Returns an {@code AddTagCommand} with parameters {@code targetIndexes} and {@code tagToAdd}
     */
    private AddTagCommand prepareCommand(Tag tagToAdd, ArrayList<Index> targetIndexes) {
        AddTagCommand addTagCommand = new AddTagCommand(tagToAdd, targetIndexes);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}


```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Favourites the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void favouriteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getAddressBook().getPersonList().get(0);
        try {
            model.favouritePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
```
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndexAndTagUnfilteredListSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(tagToDelete, indexes);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexAndTagFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.deleteTag(tagToDelete, indexes);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag tagToDelete = new Tag("friends");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidTagUnfilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag tagToDelete = new Tag("someone");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void executeInvalidTagFilteredListThrowsCommandException() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("someone");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void executeValidTagNotInUnFilteredListThrowsCommandException() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag tagToDelete = new Tag("teacher");
        DeleteTagCommand deleteTagCommand = prepareCommand(indexes, tagToDelete);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NONEXISTENT_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("classmates");
        final DeleteTagCommand standardCommand = new DeleteTagCommand(firstTag, indexes1);

        // same values -> returns true
        DeleteTagCommand commandWithSameValues = new DeleteTagCommand(firstTag, indexes1);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(firstTag, indexes2)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(secondTag, indexes1)));
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code targetIndexes} and {@code tagToDelete}
     */
    private DeleteTagCommand prepareCommand(ArrayList<Index> targetIndexes, Tag tagToDelete) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagToDelete, targetIndexes);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}


```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndexValidPersonSuccess() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withFavourite("True").build();
        ReadOnlyPerson personToFavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS, personToFavourite);
        CommandResult commandResult = favouriteCommand.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void executeInvalidIndexThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand favouriteFirstCommandCopy = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code FavouriteCommand} with the parameter {@code index}.
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteListCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FavouriteListCommandTest {

    private Model model;
    private Model expectedModel;
    private FavouriteListCommand favouriteListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        favouriteListCommand = new FavouriteListCommand();
        favouriteListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeEmptyFavouriteListShowsNothing() {
        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void executeFavouriteListNotEmptyShowsPerson() {
        favouriteFirstPerson(model);
        favouriteFirstPerson(expectedModel);
        assertEquals(model, expectedModel);

        CommandResult result = favouriteListCommand.execute();
        assertEquals(result.feedbackToUser, FavouriteListCommand.MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsTagsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsTagsPredicate firstPredicate =
                new NameContainsTagsPredicate(Collections.singletonList("first"));
        NameContainsTagsPredicate secondPredicate =
                new NameContainsTagsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindTagCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void executeOneKeywordMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindTagCommand command = prepareCommand("friends");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindTagCommand}.
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new NameContainsTagsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\TagListCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHRISTAG;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CHRIS;
import static seedu.address.testutil.TypicalPersons.CHRIS_WITH_NEW_TAG;
import static seedu.address.testutil.TypicalPersons.getTaglessAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class TagListCommandTest {
    private Model model;
    private Model expectedModel;
    private TagListCommand listTagsCommand;
    private String expectedMessage;

    @Before
    public void setUp() {
        model = new ModelManager(getTaglessAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Tag> tagList = new ArrayList<Tag>();

        listTagsCommand = new TagListCommand();
        listTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        StringBuilder expectedMessageString = new StringBuilder(listTagsCommand.MESSAGE_SUCCESS);
        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!tagList.contains(tag)) {
                    tagList.add(tag);
                    expectedMessageString.append("<").append(tag.getTagName()).append("> ");
                }
            }
        }
        expectedMessage = expectedMessageString.toString().trim();
    }

    @Test
    public void executeTagListNotEmpty() {
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeEmptyTagListShowNothing() throws DuplicatePersonException {
        Model expectedModelWithNoTags = new ModelManager();
        String expectedMessageWithNoTags = listTagsCommand.MESSAGE_FAILURE;
        TagListCommand tagListCommandWithNoTags = new TagListCommand();
        tagListCommandWithNoTags.setData(expectedModelWithNoTags, new CommandHistory(),
                new UndoRedoStack());
        assertCommandSuccess(tagListCommandWithNoTags, expectedModelWithNoTags,
                expectedMessageWithNoTags, expectedModelWithNoTags);
    }

    @Test
    public void executeUpdateTagListWithPersonAdd() throws DuplicatePersonException {
        model.addPerson(CHRIS);
        expectedModel.addPerson(CHRIS);
        String newExpectedMessage = expectedMessage + " <" + VALID_TAG_CHRISTAG + ">";
        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
    }

    @Test
    public void executeUpdateTagListWithTagEdited() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CHRIS);
        expectedModel.addPerson(CHRIS);
        String newExpectedMessage = expectedMessage + " <" + VALID_TAG_CHRISTAG + ">";
        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
        model.updatePerson(CHRIS, CHRIS_WITH_NEW_TAG);
        expectedModel.updatePerson(CHRIS, CHRIS_WITH_NEW_TAG);
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\UnFavouriteCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.favouriteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class UnFavouriteCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndexValidPersonSuccess() throws Exception {
        favouriteFirstPerson(model);
        ReadOnlyPerson personToUnfavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_SUCCESS,
                personToUnfavourite);
        CommandResult commandResult = unfavouriteCommand.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void executeInvalidIndexThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnfavouriteCommand favouriteFirstCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        UnfavouriteCommand favouriteSecondCommand = new UnfavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand deleteFirstCommandCopy = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(favouriteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

    /**
     * Returns a {@code UnfavouriteCommand} with the parameter {@code index}.
     */
    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandAddTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new AddTagCommand(toAdd, indexes), command);
    }

    @Test
    public void parseCommandAliasAddTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new AddTagCommand(toAdd, indexes), command);
    }

    @Test
    public void parseCommandDeleteTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toDelete = new Tag(tagName);
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(DeleteTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTagCommand(toDelete, indexes), command);
    }

    @Test
    public void parseCommandAliasDeleteTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toDelete = new Tag(tagName);
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(DeleteTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTagCommand(toDelete, indexes), command);
    }

    @Test
    public void parseCommandTagList() throws Exception {
        assertTrue(parser.parseCommand(TagListCommand.COMMAND_WORD,
                DEFAULT_STATE_LOCK) instanceof TagListCommand);
        assertTrue(parser.parseCommand(TagListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof TagListCommand);
    }

    @Test
    public void parseCommandAliasTagList() throws Exception {
        assertTrue(parser.parseCommand(TagListCommand.COMMAND_ALIAS,
                DEFAULT_STATE_LOCK) instanceof TagListCommand);
        assertTrue(parser.parseCommand(TagListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof TagListCommand);
    }

    @Test
    public void parseCommandFindTag() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_WORD + " " + PREFIX_TAG_FULL + " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTagCommand(
                new NameContainsTagsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandAliasFindTag() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_ALIAS + " " + PREFIX_TAG_FULL + " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTagCommand(
                new NameContainsTagsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandFavourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasFavourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandUnfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new UnfavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasUnfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new UnfavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandFavouriteList() throws Exception {
        assertTrue(parser.parseCommand(FavouriteListCommand.COMMAND_WORD,
                DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
        assertTrue(parser.parseCommand(FavouriteListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
    }

    @Test
    public void parseCommandAliasFavouriteList() throws Exception {
        assertTrue(parser.parseCommand(FavouriteListCommand.COMMAND_ALIAS,
                DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
        assertTrue(parser.parseCommand(FavouriteListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
    }
```
###### \java\seedu\address\logic\parser\AddTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parseValidIndexAndTagSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagName = "friends";
        Tag tagToAdd = new Tag(tagName);
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + tagName;
        assertParseSuccess(parser, userInput, new AddTagCommand(tagToAdd, indexes));
    }

    @Test
    public void parseInvalidTagFailure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagToAdd = "friends";
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;

        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString();
        assertParseFailure(parser, userInput, MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
    }
}


```
###### \java\seedu\address\logic\parser\DeleteTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parseValidIndexAndTagSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagName = "friends";
        Tag tagToDelete = new Tag(tagName);
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;
        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + tagName;
        assertParseSuccess(parser, userInput, new DeleteTagCommand(tagToDelete, indexes));

        final String nonExistentTagName = "hello";
        Tag nonExistentTag = new Tag(nonExistentTagName);
        userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString()
                + nonExistentTagName;
        assertParseSuccess(parser, userInput, new DeleteTagCommand(nonExistentTag, indexes));
    }

    @Test
    public void parseInvalidTagFailure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        final String tagToDelete = "friends";
        Index targetIndex1 = INDEX_FIRST_PERSON;
        Index targetIndex2 = INDEX_SECOND_PERSON;

        String userInput = targetIndex1.getOneBased() + " " + targetIndex2.getOneBased() + " " + PREFIX_TAG.toString();
        assertParseFailure(parser, userInput, MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        assertParseFailure(parser, DeleteTagCommand.COMMAND_WORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
    }
}


```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "f", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.NameContainsTagsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindTagCommand() {
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new NameContainsTagsPredicate(
                        Arrays.asList("friend", "colleague")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friend colleague", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t colleague  \t", expectedFindTagCommand);
    }


}
```
###### \java\seedu\address\logic\parser\UnfavouriteCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.UnfavouriteCommand;

public class UnfavouriteCommandParserTest {

    private UnfavouriteCommandParser parser = new UnfavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsUnfavouriteCommand() {
        assertParseSuccess(parser, "1", new UnfavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "u", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnfavouriteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    /*
     * Tests if the actual output of deleteTag is equals to the expected
     * output when given valid target indexes and a valid tag to delete.
     */
    @Test
    public void deleteTagValidIndexesAndTagSuccess() throws Exception {
        Person oldPerson1 = new PersonBuilder().withName("Someone").withTags("someTag", "friends").build();
        Person oldPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> oldPersonList = new ArrayList<ReadOnlyPerson>();
        oldPersonList.add(oldPerson1);
        oldPersonList.add(oldPerson2);
        AddressBook oldAddressBook = new AddressBook();
        oldAddressBook.setPersons(oldPersonList);

        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toDelete = new Tag("someTag");

        ModelManager expectedModel = new ModelManager(oldAddressBook, new UserPrefs());
        expectedModel.deleteTag(toDelete, indexes);

        Person newPerson1 = new PersonBuilder().withName("Someone").withTags("friends").build();
        Person newPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> newPersonList = new ArrayList<ReadOnlyPerson>();
        newPersonList.add(newPerson1);
        newPersonList.add(newPerson2);
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(newPersonList);
        ModelManager actualModel = new ModelManager(newAddressBook, new UserPrefs());

        assertEquals(expectedModel.getAddressBook().getPersonList().toString(),
                actualModel.getAddressBook().getPersonList().toString());
    }

    /*
     * Tests if the actual output of addTag is equals to the expected
     * output when given valid target indexes and a valid tag to add.
     */
    @Test
    public void addTagValidIndexesAndTagSuccess() throws Exception {
        Person oldPerson1 = new PersonBuilder().withName("someone").withTags("someTag", "friends").build();
        Person oldPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> oldPersonList = new ArrayList<ReadOnlyPerson>();
        oldPersonList.add(oldPerson1);
        oldPersonList.add(oldPerson2);
        AddressBook oldAddressBook = new AddressBook();
        oldAddressBook.setPersons(oldPersonList);

        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toAdd = new Tag("newTag");

        ModelManager expectedModel = new ModelManager(oldAddressBook, new UserPrefs());
        expectedModel.addTag(toAdd, indexes);

        Person newPerson1 = new PersonBuilder().withName("someone").withTags("someTag", "friends", "newTag").build();
        Person newPerson2 = new PersonBuilder().withTags("classmate", "newTag").build();
        List<ReadOnlyPerson> newPersonList = new ArrayList<ReadOnlyPerson>();
        newPersonList.add(newPerson1);
        newPersonList.add(newPerson2);
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(newPersonList);
        ModelManager actualModel = new ModelManager(newAddressBook, new UserPrefs());

        assertEquals(expectedModel.getAddressBook().getPersonList().toString(),
                actualModel.getAddressBook().getPersonList().toString());
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Favourite} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFavourite(String favourite) {
        descriptor.setFavourite(new Boolean(favourite));
        return this;
    }
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
    @Override
    public void addTag(Tag toAdd, ArrayList<Index> targetIndexes)  {
        fail("This method should not be called.");
    }
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
    @Override
    public void deleteTag(Tag toDelete, ArrayList<Index> targetIndexes) {
        fail("This method should not be called.");
    }
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
    @Override
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
    @Override
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Boolean} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite(String favourite) {
        this.person.setFavourite(new Boolean(favourite));
        return this;
    }
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson CHRIS = new PersonBuilder().withName(VALID_NAME_CHRIS)
            .withPhone(VALID_PHONE_CHRIS).withEmail(VALID_EMAIL_CHRIS)
            .withAddress(VALID_ADDRESS_CHRIS).withFavourite(VALID_FAVOURITE_ALICE)
            .withRemark(VALID_REMARK_CHRIS)
            .withTags(VALID_TAG_CHRISTAG).build();
    public static final ReadOnlyPerson CHRIS_WITH_NEW_TAG = new PersonBuilder().withName(VALID_NAME_CHRIS)
            .withPhone(VALID_PHONE_CHRIS)
            .withEmail(VALID_EMAIL_CHRIS).withAddress(VALID_ADDRESS_CHRIS)
            .withFavourite(VALID_FAVOURITE_ALICE).withRemark(VALID_REMARK_CHRIS)
            .withTags("friends").build();
```
