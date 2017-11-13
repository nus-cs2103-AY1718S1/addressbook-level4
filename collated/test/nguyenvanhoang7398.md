# nguyenvanhoang7398
###### \java\seedu\address\logic\commands\AddMultipleByTsvCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;

public class AddMultipleByTsvCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTsvFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_tsvFileAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS), commandResult.feedbackToUser);
        assertEquals(toAddPeople, modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED),
                commandResult.feedbackToUser);
    }

    private AddMultipleByTsvCommand getAddMultipleByTsvCommandForPerson(ArrayList<ReadOnlyPerson> toAddPeople,
                                                                        ArrayList<Integer> failedEntries,
                                                                        boolean isFileFound, Model model) {
        AddMultipleByTsvCommand command = new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
        command.setData(model, new CommandHistory(),
                new UndoRedoStack(), new RecentlyDeletedQueue(), new String());
        return command;
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\ChangeProfilePictureCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH;
import static seedu.address.testutil.TypicalProfilePicture.SECOND_PERFECT_PROFILE_PICTURE_PATH;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code ChangeProfilePictureCommand}.
 */
public class ChangeProfilePictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SECOND_PERFECT_PROFILE_PICTURE_PATH);
        assertExecutionSuccess(lastPersonIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);
        assertExecutionSuccess(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundIndex, FIRST_PERFECT_PROFILE_PICTURE_PATH,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ChangeProfilePictureCommand changeProfilePictureFirstCommand =
                new ChangeProfilePictureCommand(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        ChangeProfilePictureCommand changeProfilePictureSecondCommand =
                new ChangeProfilePictureCommand(INDEX_SECOND_PERSON, SECOND_PERFECT_PROFILE_PICTURE_PATH);

        // same object -> returns true
        assertTrue(changeProfilePictureFirstCommand.equals(changeProfilePictureFirstCommand));

        // same values -> returns true
        ChangeProfilePictureCommand changeProfilePictureFirstCommandCopy =
                new ChangeProfilePictureCommand(INDEX_FIRST_PERSON, FIRST_PERFECT_PROFILE_PICTURE_PATH);
        assertTrue(changeProfilePictureFirstCommand.equals(changeProfilePictureFirstCommandCopy));

        // different types -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(1));

        // null -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(null));

        // different picture -> returns false
        assertFalse(changeProfilePictureFirstCommand.equals(changeProfilePictureSecondCommand));
    }

    /**
     * Executes a {@code ChangeProfilePictureCommand} with the given {@code index, picturePath}
     * is raised with the correct index and picture path
     */
    private void assertExecutionSuccess(Index index, String picturePath) {
        ChangeProfilePictureCommand changeProfilePictureCommand = prepareCommand(index, picturePath);

        try {
            CommandResult commandResult = changeProfilePictureCommand.execute();
            assertEquals(String.format(ChangeProfilePictureCommand.MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS,
                    index.getOneBased()), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code ChangeProfilePictureCommand} with the given {@code index, picturePath},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String picturePath, String expectedMessage) {
        ChangeProfilePictureCommand changeProfilePictureCommand = prepareCommand(index, picturePath);

        try {
            changeProfilePictureCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ChangeProfilePictureCommand} with the parameter {@code index, picturePath}.
     */
    private ChangeProfilePictureCommand prepareCommand(Index index, String picturePath) {
        ChangeProfilePictureCommand changeProfilePictureCommand = new ChangeProfilePictureCommand(index, picturePath);
        changeProfilePictureCommand.setData(model, new CommandHistory(),
                new UndoRedoStack(), new RecentlyDeletedQueue(), new String());
        return changeProfilePictureCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

}
```
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsContainKeywordsPredicate firstPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("first"));
        TagsContainKeywordsPredicate secondPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("second"));

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
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        FindTagCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE));
    }

    /**
     *
     * @param userInput
     * @return
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(),
                new UndoRedoStack(), new RecentlyDeletedQueue(), new String());
        return command;
    }

    /**
     *
     * @param command
     * @param expectMessage
     * @param expectedList
     */
    private void assertCommandSuccess(FindTagCommand command, String expectMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\testutil\TsvFileBuilder.java
``` java
package seedu.address.testutil;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building TsvFile objects.
 */
public class TsvFileBuilder {
    public static final String DEFAULT_TSV_FILE_PATH = TypicalTsvFiles.PERFECT_TSV_FILE_PATH;

    private boolean isFileFound;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public TsvFileBuilder() {
        ContactTsvReader contactTsvReader = new ContactTsvReader(DEFAULT_TSV_FILE_PATH);
        toAddPeople = new ArrayList<>();
        failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        } catch (ParseException pe) {
            throw new AssertionError("Default tsv file is invalid.");
        }
    }

    public boolean getIsFileFound() {
        return isFileFound;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }
}
```
###### \java\seedu\address\testutil\TypicalProfilePicture.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of Profile Picture paths as string to be used in tests.
 */
public class TypicalProfilePicture {
    public static final String FIRST_PERFECT_PROFILE_PICTURE_PATH =
            "src/test/resources/images/firstPerfectProfilePicture.jpg";
    public static final String SECOND_PERFECT_PROFILE_PICTURE_PATH =
            "src/test/resources/images/secondPerfectProfilePicture.jpg";
    public static final String THIRD_PERFECT_PROFILE_PICTURE_PATH =
            "src/test/resources/images/thirdPerfectProfilePicture.jpg";
    public static final String FORTH_PERFECT_PROFILE_PICTURE_PATH =
            "src/test/resources/images/forthPerfectProfilePicture.jpg";
    public static final String FIFTH_PERFECT_PROFILE_PICTURE_PATH =
            "src/test/resources/images/fifthPerfectProfilePicture.jpg";
    public static final String FILE_NOT_FOUND_PATH = "D:/pleaseDontNameYourPictureLikeThis.jpg";
}
```
###### \java\seedu\address\testutil\TypicalTsvFiles.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of TsvFile objects to be used in tests.
 */
public class TypicalTsvFiles {
    public static final String PERFECT_TSV_FILE_PATH = "src/test/resources/docs/perfectTsvFile.txt";
    public static final String PERFECT_TSV_FILE_MESSAGE_SUCCESS = "2 new person (people) added, "
            + "0 new person (people) duplicated, 0 entry (entries) failed: ";
    public static final String PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED = "0 new person (people) added, "
            + "2 new person (people) duplicated, 0 entry (entries) failed: ";
}
```
###### \java\systemtests\AddMultipleByTsvCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_NUMBER_OF_ENTRIES_FAILED;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_SUCCESS;

import java.util.ArrayList;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;


public class AddMultipleByTsvCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void addMultipleByTsv() {
        Model model = getModel();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        String command = AddMultipleByTsvCommand.COMMAND_WORD + " " + TypicalTsvFiles.PERFECT_TSV_FILE_PATH;
        String expectedResultMessage = TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS;
        assertCommandSuccess(command, validTsvFile);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, TsvFileBuilder)}. Executes {@code command}
     * instead.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, TsvFileBuilder)
     */
    private void assertCommandSuccess(String command, TsvFileBuilder tsvFile) {
        Model expectedModel = getModel();
        boolean isFileFound = tsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = tsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = tsvFile.getFailedEntries();

        if (!isFileFound) {
            throw new IllegalArgumentException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;
        ArrayList<Person> toAdd = new ArrayList<Person>();

        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
        for (Person person: toAdd) {
            try {
                expectedModel.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }
        String expectedResultMessage = String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Join list of failed entries as integers to a single string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
        String selectedCardWebsite = getPersonListPanel().getHandleToSelectedCard().getWebsite();
```
###### \java\systemtests\ChangeProfilePictureCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeProfilePictureCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.testutil.TypicalProfilePicture;

public class ChangeProfilePictureCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select() {
        /* Case: change profile picture of the first card in the person list,
         * command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + "   " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: change profile picture of the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + personCount.getOneBased()
                + " " + TypicalProfilePicture.SECOND_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: change profile picture of the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + middleIndex.getOneBased()
                + " " + TypicalProfilePicture.THIRD_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + invalidIndex
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: change profile picture of the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: filtered person list, change profile picture of index within bounds of
         * address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + invalidIndex
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, change profile picture of index within bounds of address book
         * and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredPersonList().size();
        command = ChangeProfilePictureCommand.COMMAND_WORD + " " + validIndex.getOneBased()
                + " " + TypicalProfilePicture.FIFTH_PERFECT_PROFILE_PICTURE_PATH;
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + 0
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + -1
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + "abc"
                + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeProfilePictureCommand.MESSAGE_USAGE));

        /* Case: file not found -> rejected */
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + TypicalProfilePicture.FILE_NOT_FOUND_PATH,
                MESSAGE_FILE_NOT_FOUND);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ChAngEpIC 1 D:/picture.png", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(ChangeProfilePictureCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + TypicalProfilePicture.FIRST_PERFECT_PROFILE_PICTURE_PATH,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                ChangeProfilePictureCommand.MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS,
                expectedSelectedCardIndex.getOneBased());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\FindTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_COLLEAGUES;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FAMILY;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FRIENDS;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_OWESMONEY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findtag() {

        /* Case: find multiple persons in address book with a single tag
         * -> 1 persons found
         */
        String command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY;
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book with single tag, command with leading spaces and trailing spaces
         * -> 5 persons found
         */
        command = "   " + FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS + "   ";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY + " " + KEYWORD_MATCHING_FAMILY;
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords, in reverse order -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + KEYWORD_MATCHING_FAMILY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + "NoneMatchingKeyword";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find tag command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find tag command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 4 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 4 person found */
        command = FindTagCommand.COMMAND_WORD + " FriEnds";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " frie";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " friendss";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " siblings";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find occupation of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getOccupation().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find website of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getWebsite().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_COLLEAGUES;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdtAg" + " " + KEYWORD_MATCHING_FRIENDS;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
