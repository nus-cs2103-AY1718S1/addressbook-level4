# cqhchan
###### \java\seedu\address\logic\commands\CreateAccountCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Database;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDatabase;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AccountBuilder;

public class CreateAccountCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAccountCommand(null);
    }

    @Test
    public void execute_accountAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAccountAdded modelStub = new ModelStubAcceptingAccountAdded();
        Account validAccount = new AccountBuilder().build();

        CommandResult commandResult = getCreateAccountCommandForAccount(validAccount, modelStub).execute();

        assertEquals(String.format(CreateAccountCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAccount), modelStub.accountsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAccountException();
        Account validAccount = new AccountBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);

        getCreateAccountCommandForAccount(validAccount, modelStub).execute();
    }

    @Test
    public void equals() {
        Account alice = new AccountBuilder().withUsername("Alice").build();
        Account bob = new AccountBuilder().withUsername("Bob").build();
        CreateAccountCommand addAliceCommand = new CreateAccountCommand(alice);
        CreateAccountCommand addBobCommand = new CreateAccountCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateAccountCommand addAliceCommandCopy = new CreateAccountCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new CreateAccountCommand with the details of the given person.
     */
    private CreateAccountCommand getCreateAccountCommandForAccount(Account account, Model model) {
        CreateAccountCommand command = new CreateAccountCommand(account);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            fail("This method should not be called");
        }
        @Override
        public void addReminder(ReadOnlyReminder newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetDatabase(ReadOnlyDatabase newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAccount(ReadOnlyAccount account) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        public void updateAccount(ReadOnlyAccount account, ReadOnlyAccount editedAccount)
                throws DuplicateAccountException {
            fail("This method should not be called.");
        }


        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
                throws DuplicateReminderException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteUnusedTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyAccount> getFilteredAccountList() {
            fail("This method should not be called.");
            return null;
        }


        @Override
        public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredAccountList(Predicate<ReadOnlyAccount> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePersonTag(Tag tag) {
            fail("This metthod should not be called.");
        }

        @Override
        public void deleteReminderTag(Tag tag) {
            fail("This metthod should not be called.");
        }

        public Boolean checkIfPersonListEmpty(ArrayList<ReadOnlyPerson> contactList) {
            fail("This method should not be called.");
            return false;
        }

        public Boolean checkIfReminderListEmpty(ArrayList<ReadOnlyReminder> reminderList) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void sortListByName(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByBirthday(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByAge(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByPriority(ArrayList<ReadOnlyReminder> contactList)  throws CommandException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateAccountException extends ModelStub {

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            throw new DuplicateAccountException();
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            return new Database();
        }
    }


    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingAccountAdded extends ModelStub {
        final ArrayList<Account> accountsAdded = new ArrayList<>();

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            accountsAdded.add(new Account(account));
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            return new Database();
        }
    }
}
```
###### \java\seedu\address\logic\commands\EditReminderCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstReminderOnly;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalReminders.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.ReminderBuilder;






public class EditReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Reminder editedReminder = new ReminderBuilder().build();
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(editedReminder).build();
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_REMINDER, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), editedReminder);

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastReminder = Index.fromOneBased(model.getFilteredReminderList().size());
        ReadOnlyReminder lastReminder = model.getFilteredReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder editedReminder = reminderInList.withTask(VALID_TASK_PROJECT).withDate(VALID_DATE_PROJECT)
                .withPriority(VALID_PRIORITY_PROJECT).build();

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withTask(VALID_TASK_PROJECT)
                .withDate(VALID_DATE_PROJECT).withPriority(VALID_PRIORITY_PROJECT).build();
        EditReminderCommand editReminderCommand = prepareCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updateReminder(lastReminder, editedReminder);

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_REMINDER, new EditReminderDescriptor());
        ReadOnlyReminder editedReminder = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderInFilteredList =
            model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());

        Reminder editedReminder = new ReminderBuilder(reminderInFilteredList).withTask(VALID_TASK_PROJECT).build();
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new EditReminderDescriptorBuilder().withTask(VALID_TASK_PROJECT).build());

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), editedReminder);

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminderUnfilteredList_failure() {
        Reminder firstReminder = new Reminder(model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased()));
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(firstReminder).build();
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_SECOND_REMINDER, descriptor);

        assertCommandFailure(editReminderCommand, model, EditReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_duplicateReminderFilteredList_failure() {
        showFirstReminderOnly(model);

        // edit reminder in filtered list into a duplicate in address book
        ReadOnlyReminder reminderInList =
            model.getAddressBook().getReminderList().get(INDEX_SECOND_REMINDER.getZeroBased());

        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new EditReminderDescriptorBuilder(reminderInList).build());

        assertCommandFailure(editReminderCommand, model, EditReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_invalidReminderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withTask(VALID_TASK_PROJECT).build();
        EditReminderCommand editReminderCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidReminderIndexFilteredList_failure() {
        showFirstReminderOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        EditReminderCommand editReminderCommand = prepareCommand(outOfBoundIndex,
                new EditReminderDescriptorBuilder().withTask(VALID_TASK_PROJECT).build());

        assertCommandFailure(editReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditReminderCommand standardCommand = new EditReminderCommand(INDEX_FIRST_REMINDER, DESC_PROJECT);



        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_SECOND_REMINDER, DESC_PROJECT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_FIRST_REMINDER, DESC_ASSIGNMENT)));
    }

    /**
     * Returns an {@code EditReminderCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditReminderCommand prepareCommand(Index index, EditReminderDescriptor descriptor) {
        EditReminderCommand editReminderCommand = new EditReminderCommand(index, descriptor);
        editReminderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editReminderCommand;
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Database;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;



/**
 *
 */
public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());


    @Test
    public void equals() throws Exception {


        LoginCommand findFirstCommand = prepareCommand("private", "password");
        LoginCommand findSecondCommand = prepareCommand("Bro", "password");
        LoginCommand findThirdCommand = prepareCommand("Bro", "123");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findSecondCommand.equals(findSecondCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));

        // different objects -> returns false
        LoginCommand findFirstCommandCopy = prepareCommand("private", "password");
        assertFalse(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findSecondCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_invalidLogin() throws Exception {
        String expectedMessage = String.format(LoginCommand.MESSAGE_FAILURE, 0);
        LoginCommand command = prepareCommand("unknown", "unknown");
        CommandResult commandResult = command.execute();
        assertTrue(commandResult.feedbackToUser.equals(expectedMessage));
    }

    @Test
    public void execute_validLogin() throws Exception {
        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS, 0);
        LoginCommand command = prepareCommand("private", "password");
        CommandResult commandResult = command.execute();
        assertTrue(commandResult.feedbackToUser.equals(expectedMessage));
    }


    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private LoginCommand prepareCommand(String userInput, String userPassword) throws Exception {

        LoginCommand command =
                new LoginCommand(new Account(new Username(userInput), new Password(userPassword)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;

    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(LoginCommand command,
                                      String expectedMessage, List<ReadOnlyAccount> expectedList) {
        Database expectedDatabase = new Database(model.getDatabase());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredAccountList());
        assertEquals(expectedDatabase, model.getDatabase());
    }

}
```
###### \java\seedu\address\logic\commands\LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.ui.testutil.EventsCollectorRule;

public class LogoutCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new LogoutCommand().execute();
    }
}
```
###### \java\seedu\address\logic\parser\CreateAccountCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_PRIVATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.CreateAccountCommand;

public class CreateAccountCommandParserTest {
    private CreateAccountCommandParser parser = new CreateAccountCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, CreateAccountCommand.COMMAND_WORD + VALID_USERNAME_PRIVATE
                + PASSWORD_DESC_PASSWORD , expectedMessage);

        // missing password prefix
        assertParseFailure(parser, CreateAccountCommand.COMMAND_WORD + USERNAME_DESC_USERNAME
                + VALID_PASSWORD_PASSWORD , expectedMessage);

    }

}
```
###### \java\seedu\address\logic\parser\LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USERNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_PRIVATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LoginCommand.COMMAND_WORD + VALID_USERNAME_PRIVATE
                + PASSWORD_DESC_PASSWORD , expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LoginCommand.COMMAND_WORD + USERNAME_DESC_USERNAME
                + VALID_PASSWORD_PASSWORD , expectedMessage);

    }

}
```
###### \java\seedu\address\model\account\PasswordTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordTest {

    @Test
    public void isValidPassword() {
        // invalid addresses
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only

        // valid addresses
        assertTrue(Password.isValidPassword("Passdwner"));
        assertTrue(Password.isValidPassword("11231784.;lkfw")); // one character
        assertTrue(Password.isValidPassword("Francisco CA 2349879; USA")); // long address
    }
}
```
###### \java\seedu\address\model\account\UsernameTest.java
``` java
package seedu.address.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class UsernameTest {

    @Test
    public void isValidUsername() {
        // invalid name
        assertFalse(Username.isValidName("")); // empty string
        assertFalse(Username.isValidName(" ")); // spaces only
        assertFalse(Username.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Username.isValidName("peter jack")); // alphabets only
        assertTrue(Username.isValidName("12345")); // numbers only
        assertTrue(Username.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Username.isValidName("Capital Tan")); // with capital letters
        assertTrue(Username.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
```
###### \java\seedu\address\model\DatabaseTest.java
``` java
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAccounts.PRIVATE;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;

public class DatabaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Database database = new Database();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), database.getAccountList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        database.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyDatabase_replacesData() {
        Database newData = getTypicalDatabase();
        database.resetData(newData);
        assertEquals(newData, database);
    }

    @Test
    public void resetData_withDuplicateAccounts_throwsAssertionError() {
        // Repeat ALICE twice
        List<Account> newAccounts = Arrays.asList(new Account(PRIVATE), new Account(PRIVATE));
        DatabaseStub newData = new DatabaseStub(newAccounts);

        thrown.expect(AssertionError.class);
        database.resetData(newData);
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        database.getAccountList().remove(0);
    }


    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class DatabaseStub implements ReadOnlyDatabase {
        private final ObservableList<ReadOnlyAccount> accounts = FXCollections.observableArrayList();
        DatabaseStub(Collection<? extends ReadOnlyAccount> accounts) {
            this.accounts.setAll(accounts);
        }

        @Override
        public ObservableList<ReadOnlyAccount> getAccountList() {
            return accounts;
        }

    }

}
```
###### \java\seedu\address\model\UniqueAccountListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.account.UniqueAccountList;

public class UniqueAccountListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAccountList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\AccountBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;
/**
 *
 */
public class AccountBuilder {

    public static final String DEFAULT_NAME = "private";
    public static final String DEFAULT_PASSWORD = "password";

    private Account account;

    public AccountBuilder() {
        try {
            Username defaultName = new Username(DEFAULT_NAME);
            Password defaultPassword = new Password(DEFAULT_PASSWORD);
            this.account = new Account(defaultName, defaultPassword);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }

    /**
     * Initializes the AccountBuilder with the data of {@code personToCopy}.
     */
    public AccountBuilder(ReadOnlyAccount accountToCopy) {
        this.account = new Account(accountToCopy);
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        try {
            this.account.setUsername(new Username(username));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        try {
            this.account.setPassword(new Password(password));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Password is expected to be unique.");
        }
        return this;
    }


    public Account build() {
        return this.account;
    }


}
```
###### \java\seedu\address\testutil\DatabaseBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.Database;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class DatabaseBuilder {
    private Database database;

    public DatabaseBuilder() {
        database = new Database();
    }

    public DatabaseBuilder(Database database) {
        this.database = database;
    }

    /**
     * Adds a new {@code Account} to the {@code Database} that we are building.
     */
    public DatabaseBuilder withAccount(ReadOnlyAccount account) {
        try {
            database.addAccount(account);
        } catch (DuplicateAccountException dpe) {
            throw new IllegalArgumentException("account is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Database} that we are building.
     */

    public Database build() {
        return database;
    }
}

```
###### \java\seedu\address\testutil\TypicalAccounts.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Database;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class TypicalAccounts {

    public static final ReadOnlyAccount PRIVATE = new AccountBuilder().withUsername("private")
            .withPassword("password").build();
    public static final ReadOnlyAccount BROTHER = new AccountBuilder().withUsername("BROTHER")
            .withPassword("password").build();


    public static Database getTypicalDatabase() {
        Database ab = new Database();
        for (ReadOnlyAccount account : getTypicalAccounts()) {
            try {
                ab.addAccount(account);
            } catch (DuplicateAccountException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyAccount> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(BROTHER, PRIVATE));
    }

}
```
