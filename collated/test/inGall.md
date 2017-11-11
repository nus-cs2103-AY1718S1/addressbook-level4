# inGall
###### \java\seedu\address\logic\commands\BirthdayCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayCommand.
 */
public class BirthdayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void execute_addBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday("01/01/1991"));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().value);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday(""));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().toString());

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_DELETE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_filteredList_success() throws Exception {
                showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withBirthday("01/01/1991").build();
        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().value);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, VALID_BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     * */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, VALID_BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        final BirthdayCommand standardCommand = new BirthdayCommand(INDEX_FIRST_PERSON,
                new Birthday(VALID_BIRTHDAY_AMY));

        // same values -> returns true
        BirthdayCommand commandWithSameValues = new BirthdayCommand(INDEX_FIRST_PERSON,
                new Birthday(VALID_BIRTHDAY_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_SECOND_PERSON, new Birthday(VALID_BIRTHDAY_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(VALID_BIRTHDAY_BOB))));
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayCommand prepareCommand(Index index, String birthday) throws IllegalValueException {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, new Birthday(birthday));
        birthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayCommand;
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * @param command
     * @param actualModel
     * @param expectedMessage
     * @param expectedModel
     */
    public static void assertSortSuccess(Command command, Model actualModel, String expectedMessage,
                                         Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertNotEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
```
###### \java\seedu\address\logic\commands\FindEmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EMAILS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEmailCommand}.
 */
public class FindEmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEmailCommand findFirstEmailCommand = new FindEmailCommand(firstPredicate);
        FindEmailCommand findSecondEmailCommand = new FindEmailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommand));

        // same values -> returns true
        FindEmailCommand findFirstEmailCommandCopy = new FindEmailCommand(firstPredicate);
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEmailCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEmailCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstEmailCommand.equals(findSecondEmailCommand));
    }

    @Test
    public void execute_zeroKeywords_noEmailFound() {
        String expectedMessage = String.format(MESSAGE_EMAILS_LISTED_OVERVIEW, 0);
        FindEmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEmailsFound() {
        String expectedMessage = String.format(MESSAGE_EMAILS_LISTED_OVERVIEW, 3);
        FindEmailCommand command = prepareCommand("alice@example.com cornelia@example.com anna@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, DANIEL, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code FindEmailCommand}.
     */
    private FindEmailCommand prepareCommand(String userInput) {
        FindEmailCommand command =
                new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEmailCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\FindPhoneCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PHONES_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPhoneCommand}.
 */
public class FindPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("first"));
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPhoneCommand findFirstPhoneCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findSecondPhoneCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommand));

        // same values -> returns true
        FindPhoneCommand findFirstPhoneCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommandCopy));

        // different types -> returns false
        assertFalse(findFirstPhoneCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstPhoneCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstPhoneCommand.equals(findSecondPhoneCommand));
    }

    @Test
    public void execute_zeroKeywords_noPhoneFound() {
        String expectedMessage = String.format(MESSAGE_PHONES_LISTED_OVERVIEW, 0);
        FindPhoneCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePhonesFound() {
        String expectedMessage = String.format(MESSAGE_PHONES_LISTED_OVERVIEW, 3);
        FindPhoneCommand command = prepareCommand("95352563 94822324 94828427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindPhoneCommand}.
     */
    private FindPhoneCommand prepareCommand(String userInput) {
        FindPhoneCommand command =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPhoneCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\FindPriorityCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PRIORITY_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

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
import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPriorityCommand}.
 */
public class FindPriorityCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        PriorityContainsKeywordsPredicate firstPredicate =
                new PriorityContainsKeywordsPredicate(Collections.singletonList("first"));
        PriorityContainsKeywordsPredicate secondPredicate =
                new PriorityContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPriorityCommand findFirstPriorityCommand = new FindPriorityCommand(firstPredicate);
        FindPriorityCommand findSecondPriorityCommand = new FindPriorityCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstPriorityCommand.equals(findFirstPriorityCommand));

        // same values -> returns true
        FindPriorityCommand findFirstPriorityCommandCopy = new FindPriorityCommand(firstPredicate);
        assertTrue(findFirstPriorityCommand.equals(findFirstPriorityCommandCopy));

        // different types -> returns false
        assertFalse(findFirstPriorityCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstPriorityCommand.equals(null));

        // different reminder -> returns false
        assertFalse(findFirstPriorityCommand.equals(findSecondPriorityCommand));
    }

    @Test
    public void execute_zeroKeywords_noPriorityFound() {
        String expectedMessage = String.format(MESSAGE_PRIORITY_LISTED_OVERVIEW, 0);
        FindPriorityCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindPriorityCommand}.
     */
    private FindPriorityCommand prepareCommand(String userInput) {
        FindPriorityCommand command =
                new FindPriorityCommand(new PriorityContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyReminder>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPriorityCommand command, String expectedMessage,
                                      List<ReadOnlyReminder> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredReminderList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\FindReminderCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.GATHERING;
import static seedu.address.testutil.TypicalReminders.MEETING;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

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
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindReminderCommand}.
 */
public class FindReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void equals() {
        TaskContainsKeywordsPredicate firstPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskContainsKeywordsPredicate secondPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("second"));

        FindReminderCommand findFirstReminderCommand = new FindReminderCommand(firstPredicate);
        FindReminderCommand findSecondReminderCommand = new FindReminderCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstReminderCommand.equals(findFirstReminderCommand));

        // same values -> returns true
        FindReminderCommand findFirstReminderCommandCopy = new FindReminderCommand(firstPredicate);
        assertTrue(findFirstReminderCommand.equals(findFirstReminderCommandCopy));

        // different types -> returns false
        assertFalse(findFirstReminderCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstReminderCommand.equals(null));

        // different reminder -> returns false
        assertFalse(findFirstReminderCommand.equals(findSecondReminderCommand));
    }

    @Test
    public void execute_zeroKeywords_noReminderFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 0);
        FindReminderCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleRemindersFound() {
        String expectedMessage = String.format(MESSAGE_REMINDERS_LISTED_OVERVIEW, 3);
        FindReminderCommand command = prepareCommand("birthday Gathering Meeting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BIRTHDAY, GATHERING, MEETING));
    }

    /**
     * Parses {@code userInput} into a {@code FindReminderCommand}.
     */
    private FindReminderCommand prepareCommand(String userInput) {
        FindReminderCommand command =
                new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyReminder>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindReminderCommand command, String expectedMessage,
                                      List<ReadOnlyReminder> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredReminderList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SortAgeCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortAgeCommandTest {

    public static final int FIRST_PERSON = 0;
    public static final int SECOND_PERSON = 1;
    public static final int THIRD_PERSON = 2;

    private Model model;
    private Model expectedModel;
    private SortAgeCommand sortAgeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortAgeCommand = new SortAgeCommand();
        sortAgeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameFirstPersonBeforeSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

    @Test
    public void execute_differentFirstPersonAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertSortSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Adeline));
    }

    @Test
    public void execute_listWithMultipleBirthdaysAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        Person Jamie = new PersonBuilder().withName("Jamie").withBirthday("08/02/1995").build();
        Person Tom = new PersonBuilder().withName("Tom").withBirthday("08/08/1992").build();
        model.addPerson(Adeline);
        model.addPerson(Jamie);
        model.addPerson(Tom);
        assertSortSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Tom));
        assertTrue(model.getFilteredPersonList().get(SECOND_PERSON).equals(Jamie));
        assertTrue(model.getFilteredPersonList().get(THIRD_PERSON).equals(Adeline));
    }

}
```
###### \java\seedu\address\logic\commands\SortBirthdayCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortBirthdayCommandTest {

    public static final int FIRST_PERSON = 0;
    public static final int SECOND_PERSON = 1;

    private Model model;
    private Model expectedModel;
    private SortBirthdayCommand sortBirthdayCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortBirthdayCommand = new SortBirthdayCommand();
        sortBirthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameFirstPersonBeforeSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

    @Test
    public void execute_differentFirstPersonAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertSortSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Adeline));
    }

    @Test
    public void execute_differentFirstPersonAfterSecondPersonAddedAndSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        Person Zoe = new PersonBuilder().withName("Zoe").withBirthday("01/01/1991").build();
        model.addPerson(Adeline);
        model.addPerson(Zoe);
        assertSortSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Zoe));
        assertTrue(model.getFilteredPersonList().get(SECOND_PERSON).equals(Adeline));
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    public static final int FIRST_PERSON = 0;

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameFirstPersonBeforeSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").build();
        model.addPerson(Adeline);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

    @Test
    public void execute_differentFirstPersonAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").build();
        model.addPerson(Adeline);
        assertSortSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

}
```
###### \java\seedu\address\logic\commands\SortPriorityCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class SortPriorityCommandTest {

    public static final int FIRST_REMINDER = 0;
    public static final int SECOND_REMINDER = 1;

    private Model model;
    private Model expectedModel;
    private SortPriorityCommand sortPriorityCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        sortPriorityCommand = new SortPriorityCommand();
        sortPriorityCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_firstReminderAdded_unsorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        model.addReminder(Breakfast);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Breakfast));
    }

    @Test
    public void execute_sameFirstReminderAfterAdded_unSorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        Reminder Lunch = new ReminderBuilder().withTask("Lunch").withPriority("High").build();
        model.addReminder(Breakfast);
        model.addReminder(Lunch);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Breakfast));
        assertTrue(model.getFilteredReminderList().get(SECOND_REMINDER).equals(Lunch));
    }


    @Test
    public void execute_differentFirstReminderAfterSorted() throws Exception {
        Reminder Breakfast = new ReminderBuilder().withTask("Breakfast").withPriority("Low").build();
        Reminder Lunch = new ReminderBuilder().withTask("Lunch").withPriority("High").build();
        model.addReminder(Breakfast);
        model.addReminder(Lunch);
        assertSortSuccess(sortPriorityCommand, model, SortPriorityCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredReminderList().get(FIRST_REMINDER).equals(Lunch));
        assertTrue(model.getFilteredReminderList().get(SECOND_REMINDER).equals(Breakfast));

    }

}
```
###### \java\seedu\address\logic\parser\BirthdayCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.model.person.Birthday;

public class BirthdayCommandParserTest {
    private BirthdayCommandParser parser = new BirthdayCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Birthday birthday = new Birthday("01/01/1991");

        // have birthday
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString() + " " + birthday;
        BirthdayCommand expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, birthday);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no birthday
        userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString();
        expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\FindEmailCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

public class FindEmailCommandParserTest {

    private FindEmailCommandParser parser = new FindEmailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEmailCommand() {
        // no leading and trailing whitespaces
        FindEmailCommand expectedFindEmailCommand = new FindEmailCommand(
                new EmailContainsKeywordsPredicate(Arrays.asList("Alice@example.com", "Bob@example.com")));
        assertParseSuccess(parser, "Alice@example.com Bob@example.com", expectedFindEmailCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice@example.com \n \t Bob@example.com  \t", expectedFindEmailCommand);
    }

}
```
###### \java\seedu\address\logic\parser\FindPhoneCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPhoneCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindPhoneCommand =
                new FindPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("87654321", "98765432")));
        assertParseSuccess(parser, "87654321 98765432", expectedFindPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 87654321 \n \t 98765432  \t", expectedFindPhoneCommand);
    }

}
```
###### \java\seedu\address\logic\parser\FindPriorityCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPriorityCommand;
import seedu.address.model.reminder.PriorityContainsKeywordsPredicate;

public class FindPriorityCommandParserTest {

    private FindPriorityCommandParser parser = new FindPriorityCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindPriorityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPriorityCommand() {
        // no leading and trailing whitespaces
        FindPriorityCommand expectedFindPriorityCommand =
                new FindPriorityCommand(new PriorityContainsKeywordsPredicate(Arrays.asList("Low", "High")));
        assertParseSuccess(parser, "Low High", expectedFindPriorityCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Low \n \t High  \t", expectedFindPriorityCommand);
    }

}
```
###### \java\seedu\address\logic\parser\FindReminderCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.model.reminder.TaskContainsKeywordsPredicate;

public class FindReminderCommandParserTest {

    private FindReminderCommandParser parser = new FindReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindReminderCommand() {
        // no leading and trailing whitespaces
        FindReminderCommand expectedFindReminderCommand =
                new FindReminderCommand(new TaskContainsKeywordsPredicate(Arrays.asList("Submission", "Meeting")));
        assertParseSuccess(parser, "Submission Meeting", expectedFindReminderCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Submission \n \t Meeting  \t", expectedFindReminderCommand);
    }

}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday("01/01/1991");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value);
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different person -> returns false
        Birthday differentBirthday = new Birthday("02/02/1992");
        assertFalse(birthday.equals(differentBirthday));
    }
}
```
###### \java\seedu\address\testutil\EditReminderDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(ReadOnlyReminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setTask(reminder.getTask());
        descriptor.setPriority(reminder.getPriority());
        descriptor.setDate(reminder.getDate());
        descriptor.setMessage(reminder.getMessage());
        descriptor.setTags(reminder.getTags());
    }

    /**
     * Sets the {@code Task} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTask(String task) {
        try {
            ParserUtil.parseTask(Optional.of(task)).ifPresent(descriptor::setTask);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDate(String date) {
        try {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Message} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withMessage(String message) {
        try {
            ParserUtil.parseMessage(Optional.of(message)).ifPresent(descriptor::setMessage);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("message is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditReminderDescriptor}
     * that we are building.
     */
    public EditReminderDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\ReminderBuilder.java
``` java
package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_TASK = "James birthday";
    public static final String DEFAULT_PRIORITY = "Low";
    public static final String DEFAULT_DATE = "02/02/2017 20:17";
    public static final String DEFAULT_MESSAGE = "Buy present with others";
    public static final String DEFAULT_TAGS = "Watch";

    private Reminder reminder;

    public ReminderBuilder() {
        try {
            Task defaultTask = new Task(DEFAULT_TASK);
            Priority defaultPriority = new Priority(DEFAULT_PRIORITY);
            Date defaultDate = new Date(DEFAULT_DATE);
            Message defaultMessage = new Message(DEFAULT_MESSAGE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.reminder = new Reminder(defaultTask, defaultPriority, defaultDate, defaultMessage, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default reminder's values are invalid.");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code Task} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTask(String task) {
        try {
            this.reminder.setTask(new Task(task));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTags(String ... tags) {
        try {
            this.reminder.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Message} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withMessage(String message) {
        this.reminder.setMessage(new Message(message));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withPriority(String priority) {
        try {
            this.reminder.setPriority(new Priority(priority));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        try {
            this.reminder.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Reminder build() {
        return this.reminder;
    }

}
```
###### \java\seedu\address\testutil\TypicalReminders.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_OFFICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SOFTCOPY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PROJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final ReadOnlyReminder BIRTHDAY = new ReminderBuilder().withTask("James birthday")
            .withPriority("Low").withDate("02/02/2017 16:00").withMessage("Buy present with others.")
            .withTags("Watch", "friends", "retrieveTester").build();
    public static final ReadOnlyReminder DATING = new ReminderBuilder().withTask("Dating with Joanne")
            .withPriority("Low").withDate("01/01/2017 15:00").withMessage("Meet at Clementi")
            .withTags("Present", "retrieveTester").build();
    public static final ReadOnlyReminder GATHERING = new ReminderBuilder().withTask("Gathering with friends")
            .withMessage("Gather at John's house").withDate("05/05/2017 12:00").withPriority("Medium").build();
    public static final ReadOnlyReminder LUNCH = new ReminderBuilder().withTask("Lunch with Joseph")
            .withMessage("Venue at JE").withDate("06/06/2017 12:00").withPriority("Medium").build();
    public static final ReadOnlyReminder MEETING = new ReminderBuilder().withTask("Group Meeting")
            .withMessage("Have all reports ready").withDate("04/04/2017 09:00").withPriority("High").build();
    public static final ReadOnlyReminder PARTY = new ReminderBuilder().withTask("Group Party")
            .withMessage("DressCode is black and white").withDate("03/03/2017 20:00").withPriority("High").build();

    // Manually added
    public static final ReadOnlyReminder DINNER = new ReminderBuilder().withTask("Dinner at home")
            .withMessage("Steamboat").withDate("07/07/2017 18:00").withPriority("Medium").build();
    public static final ReadOnlyReminder COLLECTION = new ReminderBuilder().withTask("Items Collection")
            .withMessage("Collect items at post office").withDate("08/08/2017 18:00").withPriority("Low").build();

    // Manually added - Reminder's details found in {@code CommandTestUtil}
    public static final ReadOnlyReminder PROJECT = new ReminderBuilder().withTask(VALID_TASK_PROJECT)
            .withMessage(VALID_MESSAGE_PROJECT).withDate(VALID_DATE_PROJECT).withPriority(VALID_PRIORITY_PROJECT)
            .withTags(VALID_TAG_OFFICE).build();
    public static final ReadOnlyReminder ASSIGNMENT = new ReminderBuilder().withTask(VALID_TASK_ASSIGNMENT)
            .withMessage(VALID_MESSAGE_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
            .withPriority(VALID_PRIORITY_ASSIGNMENT).withTags(VALID_TAG_SOFTCOPY, VALID_TAG_OFFICE).build();

    public static final String KEYWORD_MATCHING_GROUP = "Group"; // A keyword that matches GROUP

    // A keyword that matches RETRIEVETESTER
    public static final String KEYWORD_MATCHING_RETRIEVETESTER = "retrieveTester";

    private TypicalReminders() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyReminder reminder : getTypicalReminders()) {
            try {
                ab.addReminder(reminder);
            } catch (DuplicateReminderException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyReminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, DATING, GATHERING, LUNCH, MEETING, PARTY));
    }
}
```
###### \java\systemtests\FindEmailCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_EMAILS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindEmailCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        Model expectedModel = getModel();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        String command = FindEmailCommand.COMMAND_WORD + " heinz@example.com";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: repeat previous find email command where person list is displaying the persons we are finding
         * -> 1 person found
         */
        command = FindEmailCommand.COMMAND_WORD + " " + CARL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple emails in address book, 2 keywords -> 2 persons found */
        command = FindEmailCommand.COMMAND_WORD + " alice@example.com lydia@example.com";
        ModelHelper.setFilteredList(expectedModel, ALICE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple emails in address book, 2 keywords in reversed order -> 2 persons found */
        command = FindEmailCommand.COMMAND_WORD + " lydia@example.com alice@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple emails in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindEmailCommand.COMMAND_WORD + " lydia@example.com alice@example.com lydia@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple emails in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindEmailCommand.COMMAND_WORD + " lydia@example.com alice@example.com test@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find email in address book, keyword is substring of name -> 0 persons found */
        command = FindEmailCommand.COMMAND_WORD + " alic";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email in address book, name is substring of keyword -> 0 persons found */
        command = FindEmailCommand.COMMAND_WORD + " alice@example.coma";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email not in address book -> 0 persons found */
        command = FindEmailCommand.COMMAND_WORD + " bobby@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindEmailCommand.COMMAND_WORD + " " + FIONA.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindEmailCommand.COMMAND_WORD + " " + FIONA.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(FIONA.getTags());
        command = FindEmailCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindEmailCommand.COMMAND_WORD + " alice@example.com";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EMAILS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_EMAILS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
###### \java\systemtests\FindPhoneCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PHONES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindPhoneCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findPhone() {
        Model expectedModel = getModel();

        /* Case: find phone where phone list is not displaying the phone we are finding -> 1 phone found */
        String command = FindPhoneCommand.COMMAND_WORD + " 95352563";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 98765432 87652533";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords in reversed order -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 keywords with 1 repeat -> 2 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432 87652533";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 phones found
         */
        command = FindPhoneCommand.COMMAND_WORD + " 87652533 98765432 NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find phone in address book, keyword is substring of name -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 9876";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in address book, name is substring of keyword -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 987654321";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone not in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " 82345678";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + BENSON.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 phones found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 phones found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindPhoneCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in empty address book -> 0 phones found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindPhoneCommand.COMMAND_WORD + " 87652533";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdpHONE 87652533";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PHONES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PHONES_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

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
###### \java\systemtests\FindPriorityCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PRIORITY_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.DATING;
import static seedu.address.testutil.TypicalReminders.GATHERING;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindPriorityCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindPriorityCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        Model expectedModel = getModel();

        /* Case: find priority where reminder list is not displaying the reminder we are finding -> 1 reminder found */
        String command = FindPriorityCommand.COMMAND_WORD + " Low";
        ModelReminderHelper.setFilteredReminderList(expectedModel, DATING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where reminder list is displaying the reminders we are finding
         * -> 2 reminders found
         */
        command = FindPriorityCommand.COMMAND_WORD + " Low";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple priorities in address book, 2 keywords -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Low Medium";
        ModelReminderHelper.setFilteredReminderList(expectedModel, BIRTHDAY, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 keywords in reversed order -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 keywords with 1 repeat -> 2 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low Medium";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple priorities in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 reminders found
         */
        command = FindPriorityCommand.COMMAND_WORD + " Medium Low NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find priority in address book, keyword is substring of name -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Med";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority in address book, name is substring of keyword -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Lows";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority not in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " Average";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getTask().taskName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getDate().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find message of reminder in address book -> 0 reminders found */
        command = FindPriorityCommand.COMMAND_WORD + " " + GATHERING.getMessage().message;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of reminder in address book -> 0 reminders found */
        List<Tag> tags = new ArrayList<>(GATHERING.getTags());
        command = FindPriorityCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority in empty address book -> 0 reminders found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getReminderList().size() == 0;
        command = FindPriorityCommand.COMMAND_WORD + " Medium";
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdpRIORITY Low";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PRIORITY_LISTED_OVERVIEW} with the number of reminders in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PRIORITY_LISTED_OVERVIEW, expectedModel.getFilteredReminderList().size());

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
###### \java\systemtests\FindReminderCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.testutil.TypicalReminders.DATING;
import static seedu.address.testutil.TypicalReminders.GATHERING;
import static seedu.address.testutil.TypicalReminders.KEYWORD_MATCHING_GROUP;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindReminderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {

        Model expectedModel = getModel();

        /* Case: find reminder where reminder list is not displaying the reminder we are finding -> 1 reminder found */
        String command = FindReminderCommand.COMMAND_WORD + " Dating";
        ModelReminderHelper.setFilteredReminderList(expectedModel, DATING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where reminder list is displaying the reminders we are finding
         * -> 2 reminders found
         */
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " birthday Gathering";
        ModelReminderHelper.setFilteredReminderList(expectedModel, BIRTHDAY, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords in reversed order -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 keywords with 1 repeat -> 2 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday Gathering";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple reminders in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 reminders found
         */
        command = FindReminderCommand.COMMAND_WORD + " Gathering birthday NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same reminders in address book after deleting 1 of them -> 1 reminder found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getReminderList().contains(BIRTHDAY);
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, keyword is same as name but of different case -> 1 reminder found */
        command = FindReminderCommand.COMMAND_WORD + " GrOuP";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, keyword is substring of name -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Gro";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in address book, name is substring of keyword -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Groups";
        ModelReminderHelper.setFilteredReminderList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder not in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " Fishing";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getPriority().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find date of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getDate().date;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find message of reminder in address book -> 0 reminders found */
        command = FindReminderCommand.COMMAND_WORD + " " + GATHERING.getMessage().message;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of reminder in address book -> 0 reminders found */
        List<Tag> tags = new ArrayList<>(GATHERING.getTags());
        command = FindReminderCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find reminder in empty address book -> 0 reminders found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getReminderList().size() == 0;
        command = FindReminderCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_GROUP;
        expectedModel = getModel();
        ModelReminderHelper.setFilteredReminderList(expectedModel, GATHERING);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdRemiNdeR Group";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_REMINDER_LISTED_OVERVIEW} with the number of reminders in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_REMINDERS_LISTED_OVERVIEW, expectedModel.getFilteredReminderList().size());

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
###### \java\systemtests\ModelReminderHelper.java
``` java
package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelReminderHelper {
    private static final Predicate<ReadOnlyReminder> PREDICATE_MATCHING_NO_PRIORITY = unused -> false;

    /**
     * Updates {@code model}'s filtered reminder list to display only {@code toDisplay}.
     */
    public static void setFilteredReminderList(Model model, List<ReadOnlyReminder> toDisplay) {
        Optional<Predicate<ReadOnlyReminder>> predicate =
                toDisplay.stream().map(ModelReminderHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredReminderList(predicate.orElse(PREDICATE_MATCHING_NO_PRIORITY));
    }

    /**
     * @see ModelReminderHelper#setFilteredReminderList(Model, List)
     */
    public static void setFilteredReminderList(Model model, ReadOnlyReminder... toDisplay) {
        setFilteredReminderList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ReadOnlyReminder} equals to {@code other}.
     */
    private static Predicate<ReadOnlyReminder> getPredicateMatching(ReadOnlyReminder other) {
        return reminder -> reminder.equals(other);
    }
}
```
