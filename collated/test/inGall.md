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
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday("01/01/1991"));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().value);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday(""));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().toString());

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_DELETE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
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
    public void equals() {
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
    private BirthdayCommand prepareCommand(Index index, String birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, new Birthday(birthday));
        birthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayCommand;
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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        FindReminderCommand command = prepareCommand("birthday Gathering office");
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
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void equals() {
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
    public static final String DEFAULT_DATE = "02/02/2017 2017";
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
        this.reminder.setDate(new Date(date));
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
            .withPriority("Low").withDate("02/02/2017 1600").withMessage("Buy present with others.")
            .withTags("Watch", "friends", "retrieveTester").build();
    public static final ReadOnlyReminder DATING = new ReminderBuilder().withTask("Dating with Joanne")
            .withPriority("High").withDate("01/01/2017 1500").withMessage("Meet at Clementi")
            .withTags("Present", "retrieveTester").build();
    public static final ReadOnlyReminder GATHERING = new ReminderBuilder().withTask("Gathering with friends")
            .withMessage("Gather at John's house").withDate("05/05/2017 1200").withPriority("Medium").build();
    public static final ReadOnlyReminder LUNCH = new ReminderBuilder().withTask("Lunch with Joseph")
            .withMessage("Venue at JE").withDate("06/06/2017 1200").withPriority("Low").build();
    public static final ReadOnlyReminder MEETING = new ReminderBuilder().withTask("Meeting in the office")
            .withMessage("Have all reports ready").withDate("04/04/2017 0900").withPriority("High").build();
    public static final ReadOnlyReminder PARTY = new ReminderBuilder().withTask("Cocktail Party")
            .withMessage("DressCode is black and white").withDate("03/03/2017 2000").withPriority("Low").build();

    // Manually added
    public static final ReadOnlyReminder DINNER = new ReminderBuilder().withTask("Dinner at home")
            .withMessage("Steamboat").withDate("07/07/2017 1800").withPriority("Medium").build();
    public static final ReadOnlyReminder COLLECTION = new ReminderBuilder().withTask("Items Collection")
            .withMessage("Collect items at post office").withDate("08/08/2017 1800").withPriority("Low").build();

    // Manually added - Reminder's details found in {@code CommandTestUtil}
    public static final ReadOnlyReminder PROJECT = new ReminderBuilder().withTask(VALID_TASK_PROJECT)
            .withMessage(VALID_MESSAGE_PROJECT).withDate(VALID_DATE_PROJECT).withPriority(VALID_PRIORITY_PROJECT)
            .withTags(VALID_TAG_OFFICE).build();
    public static final ReadOnlyReminder ASSIGNMENT = new ReminderBuilder().withTask(VALID_TASK_ASSIGNMENT)
            .withMessage(VALID_MESSAGE_ASSIGNMENT).withDate(VALID_DATE_ASSIGNMENT)
            .withPriority(VALID_PRIORITY_ASSIGNMENT).withTags(VALID_TAG_SOFTCOPY, VALID_TAG_OFFICE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
