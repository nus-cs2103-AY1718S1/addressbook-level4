# cqhchan
###### \java\seedu\address\commons\events\model\DatabaseChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyDatabase;

/** Indicates the AddressBook in the model has changed*/
public class DatabaseChangedEvent extends BaseEvent {

    public final ReadOnlyDatabase data;

    public DatabaseChangedEvent(ReadOnlyDatabase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getAccountList().size();
    }
}
```
###### \java\seedu\address\logic\commands\CreateAccountCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String COMMAND_ALIAS = "ca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Account to the database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "New account added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the address book";

    private final Account toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public CreateAccountCommand(ReadOnlyAccount account) {
        toAdd = new Account(account);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAccountException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateAccountCommand // instanceof handles nulls
                && toAdd.equals(((CreateAccountCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.account.ReadOnlyAccount;
/**
 *
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_FAILURE = "Username or Password Incorrect";
    public static final String MESSAGE_SUCCESS = "Login Successful";
    private static String MESSAGE_LOGIN_ACKNOWLEDGEMENT;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to private Database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private ReadOnlyAccount account;

    public LoginCommand(ReadOnlyAccount account) {
        this.account = account;
    }

    @Override
    public CommandResult execute() {

        for (ReadOnlyAccount tempaccount : model.getFilteredAccountList()) {
            if (account.getUsername().fullName.equals(tempaccount.getUsername().fullName)
                    && account.getPassword().value.equals(tempaccount.getPassword().value)) {
                logger.info("Credentials Accepted");
                try {
                    MainApp.getUi().restart(account.getUsername().fullName);
                } catch (Exception e) {

                    logger.info("Exception caught" + e.toString());
                }
                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);

    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.MainApp;
import seedu.address.ui.Ui;

/**
 * The LOgout Function
 **/
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_LOGOUT_FAILURE = "Logout Failed ...";
    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logout as requested ...";

    @Override
    public CommandResult execute() {
        Ui ui = MainApp.getUi();
        try {
            ui.restart("addressbook");
            return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
        } catch (Exception e) {

            return new CommandResult(MESSAGE_LOGOUT_FAILURE);
        }

    }

}
```
###### \java\seedu\address\logic\commands\SelectReminderCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToReminderRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;


/**
 * Selects a reminder identified using it's last displayed index from the address book.
 */
public class SelectReminderCommand extends Command {

    public static final String COMMAND_WORD = "selectReminder";
    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the Reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_REMINDER_SUCCESS = "Selected Reminder: %1$s";

    private final Index targetIndex;

    public SelectReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToReminderRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_REMINDER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectReminderCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\address\logic\parser\CreateAccountCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;

/**
 *
 */
public class CreateAccountCommandParser implements Parser<CreateAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAccountCommand.MESSAGE_USAGE));
        }

        try {
            Password userPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            Username userName = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            ReadOnlyAccount account = new Account(userName, userPassword);
            return new CreateAccountCommand(account);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;


/**
 *
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        try {

            Password userPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            Username userName = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            ReadOnlyAccount account = new Account(userName, userPassword);
            return new LoginCommand(account);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\SelectReminderCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectReminderCommandParser implements Parser<SelectReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectReminderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectReminderCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\account\Account.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 */
public class Account implements ReadOnlyAccount {
    private ObjectProperty<Username> username;
    private ObjectProperty<Password> password;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = new SimpleObjectProperty<>(username);
        this.password = new SimpleObjectProperty<>(password);
    }

    public Account(ReadOnlyAccount source) {
        this(source.getUsername(), source.getPassword());
    }


    public void setUsername(Username username) {
        this.username.set(requireNonNull(username));
    }

    @Override
    public ObjectProperty<Username> usernameProperty() {
        return username;
    }

    @Override
    public Username getUsername() {
        return username.get();
    }

    public void setPassword(Password password) {
        this.password.set(requireNonNull(password));
    }

    @Override
    public ObjectProperty<Password> passwordProperty() {
        return password;
    }

    @Override
    public Password getPassword() {
        return password.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyAccount // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAccount) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### \java\seedu\address\model\account\Password.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 *
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
        "Password can take any values, and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_PASSWORD_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given password.
     *
     * @throws IllegalValueException if given password string is invalid.
     */
    public Password(String password) throws IllegalValueException {
        requireNonNull(password);
        if (!isValidPassword(password)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_CONSTRAINTS);
        }
        this.value = password;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(ADDRESS_PASSWORD_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\account\ReadOnlyAccount.java
``` java
package seedu.address.model.account;

import javafx.beans.property.ObjectProperty;

/**
 *
 */
public interface ReadOnlyAccount {

    ObjectProperty<Username> usernameProperty();
    Username getUsername();
    ObjectProperty<Password> passwordProperty();
    Password getPassword();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyAccount other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUsername().equals(this.getUsername()) // state checks here onwards
                && other.getPassword().equals(this.getPassword()));

    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Password: ")
                .append(getPassword());
        return builder.toString();
    }

}

```
###### \java\seedu\address\model\account\UniqueAccountList.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *
 */
public class UniqueAccountList implements Iterable<Account> {

    private final ObservableList<Account> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyAccount> mappedList = EasyBind.map(internalList, (account) -> account);

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(ReadOnlyAccount toCheck) {
        requireNonNull(toCheck);

        for (Account account : internalList) {

            if (account.getUsername().fullName.equals(toCheck.getUsername().fullName)) {
                return true;
            }

        }
        return false;
    }
    /**
     * Adds a account to the list.
     *
     * @throws DuplicatePersonException if the account to add is a duplicate of an existing account in the list.
     */
    public void add(ReadOnlyAccount toAdd) throws DuplicateAccountException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalList.add(new Account(toAdd));
    }

    /**
     * Replaces the account {@code target} in the list with {@code editedAccount}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing account in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setAccount(ReadOnlyAccount target, ReadOnlyAccount editedAccount)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedAccount);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedAccount) && internalList.contains(editedAccount)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, new Account(editedAccount));
    }

    /**
     * Removes the equivalent account from the list.
     *
     * @throws PersonNotFoundException if no such account could be found in the list.
     */
    public boolean remove(ReadOnlyAccount toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean accountFoundAndDeleted = internalList.remove(toRemove);
        if (!accountFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return accountFoundAndDeleted;
    }

    public void setAccounts(UniqueAccountList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAccounts(List<? extends ReadOnlyAccount> accounts) throws DuplicateAccountException {
        final UniqueAccountList replacement = new UniqueAccountList();
        for (final ReadOnlyAccount account : accounts) {
            replacement.add(new Account(account));
        }
        setAccounts(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyAccount> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Account> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccountList // instanceof handles nulls
                && this.internalList.equals(((UniqueAccountList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\account\Username.java
``` java
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

/**
 *
 */
public class Username {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Username(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
```
###### \java\seedu\address\model\account\UsernamePasswordCheck.java
``` java
package seedu.address.model.account;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 *
 */
public class UsernamePasswordCheck  implements Predicate<ReadOnlyAccount> {

    private final List<String> keywords;

    public UsernamePasswordCheck(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyAccount account) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(account.getUsername().fullName, keyword));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernamePasswordCheck // instanceof handles nulls
                && this.keywords.equals(((UsernamePasswordCheck) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\Database.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *
 */
public class Database implements ReadOnlyDatabase {

    private final UniqueAccountList accounts;

    /*
    * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */ {
        accounts = new UniqueAccountList();
    }

    public Database() {
    }

    public Database(ReadOnlyDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setAccounts(List<? extends ReadOnlyAccount> accounts) throws DuplicateAccountException {
        this.accounts.setAccounts(accounts);
    }

    /**
     *
     * @param newData
     */
    public void resetData(ReadOnlyDatabase newData) {
        requireNonNull(newData);
        try {
            setAccounts(newData.getAccountList());

        } catch (DuplicateAccountException dpe) {
            assert false : "Database should not have duplicate persons";
        }

    }

    /**
     *
     * @param p
     * @throws DuplicateAccountException
     */
    public void addAccount(ReadOnlyAccount p) throws DuplicateAccountException {
        Account newAccount = new Account(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        accounts.add(newAccount);
    }

    /**
     *
     * @param target
     * @param editedReadOnlyAccount
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    public void updateAccount(ReadOnlyAccount target, ReadOnlyAccount editedReadOnlyAccount)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyAccount);

        Account editedAccount = new Account(editedReadOnlyAccount);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        accounts.setAccount(target, editedAccount);
    }

    /**
     *
     * @param key
     * @return
     * @throws PersonNotFoundException
     */
    public boolean removeAccount(ReadOnlyAccount key) throws PersonNotFoundException {
        if (accounts.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public ObservableList<ReadOnlyAccount> getAccountList() {
        return accounts.asObservableList();
    }

    @Override
    public String toString() {
        return accounts.asObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Database // instanceof handles nulls
                && this.accounts.equals(((Database) other).accounts));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(accounts);

    }
}
```
###### \java\seedu\address\model\ReadOnlyDatabase.java
``` java
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.account.ReadOnlyAccount;

/**
 *
 */
public interface ReadOnlyDatabase {

    ObservableList<ReadOnlyAccount> getAccountList();
}
```
###### \java\seedu\address\storage\DataBaseStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDatabase;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface DataBaseStorage {

    /**
     * Returns the file path of the data file.
     */
    String getDatabaseFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDatabase> readDatabase() throws DataConversionException, IOException;

    /**
     * @see #getDatabaseFilePath()
     */
    Optional<ReadOnlyDatabase> readDatabase(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to the storage.
     * @param database cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDatabase(ReadOnlyDatabase database) throws IOException;

    /**
     * @see #saveDatabase(ReadOnlyDatabase) (ReadOnlyAddressBook)
     */
    void saveDatabase(ReadOnlyDatabase database, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to a fixed temporary location.
     * @param database cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupDatabase(ReadOnlyDatabase database) throws IOException;
}
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    void saveDatabase(ReadOnlyDatabase database) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    void handleDatabaseChangedEvent(DatabaseChangedEvent abce);


}
```
###### \java\seedu\address\storage\XmlAdaptedAccount.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;

/**
 * JAXB-friendly version of the Account.
 */
public class XmlAdaptedAccount {

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}


    /**
     * Converts a given Account into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAccount
     */
    public XmlAdaptedAccount(ReadOnlyAccount source) {
        username = source.getUsername().fullName;
        password = source.getPassword().value;

    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account
     */
    public Account toModelType() throws IllegalValueException {
        final Username username = new Username(this.username);
        final Password password = new Password(this.password);
        return new Account(username, password);
    }
}
```
###### \java\seedu\address\storage\XmlDatabaseStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyDatabase;

/**
 * A class to access Database data stored as an xml file on the hard disk.
 */
public class XmlDatabaseStorage implements DataBaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDatabaseStorage.class);

    private String filePath;

    public XmlDatabaseStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDatabase> readDatabase() throws DataConversionException, IOException {
        return readDatabase(filePath);
    }

    /**
     * Similar to {@link #readDatabase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDatabase> readDatabase(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File databaseFile = new File(filePath);

        if (!databaseFile.exists()) {
            logger.info("Database file "  + databaseFile + " not found");
            return Optional.empty();
        }

        ReadOnlyDatabase databaseOptional = XmlFileStorage.loadDataBaseFromSaveFile(new File(filePath));

        return Optional.of(databaseOptional);
    }

    @Override
    public void saveDatabase(ReadOnlyDatabase database) throws IOException {
        saveDatabase(database, filePath);
    }

    /**
     * Similar to {@link #saveDatabase(ReadOnlyDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDatabase(ReadOnlyDatabase database, String filePath) throws IOException {
        requireNonNull(database);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataBaseToFile(file, new XmlSerializableDatabase(database));
    }

    @Override
    public void backupDatabase(ReadOnlyDatabase database) throws IOException {
        String databaseBackupFilePath = "backup/addressbook-backup.xml";
        saveDatabase(database, databaseBackupFilePath);
    }

}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     *
     * @param file
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
     */
    public static XmlSerializableDatabase loadDataBaseFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableDatabase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \java\seedu\address\storage\XmlSerializableDatabase.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyDatabase;
import seedu.address.model.account.ReadOnlyAccount;


/**
 * An Immutable Database that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableDatabase implements ReadOnlyDatabase {

    @XmlElement
    private List<XmlAdaptedAccount> accounts;
    @XmlElement
    private List<XmlAdaptedReminder> reminders;
    @XmlElement
    private List<XmlAdaptedTag> tags;


    /**
     * Creates an empty XmlSerializableDatabase.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDatabase() {
        accounts = new ArrayList<>();
        reminders = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDatabase(ReadOnlyDatabase src) {
        this();
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyAccount> getAccountList() {
        final ObservableList<ReadOnlyAccount> accounts = this.accounts.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(accounts);
    }



}
```
###### \java\seedu\address\ui\Browser.java
``` java
package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * The Browser of the App.
 */
public class Browser extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    private static final String FXML = "Browser.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;


    public Browser() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        loadDefaultPage();

    }


    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     *  Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        // loadPage("https://i.pinimg.com/736x/25/9e/ab/
        // 259eab749e20a2594e83025c5cf9c79c--being-a-gentleman-gentleman-rules.jpg");

        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @FXML
    private StackPane browserPanel;

    public BrowserPanel() {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        browser = new Browser();
        browserPanel.getChildren().add(browser.getRoot());
    }

    /**
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        try {
            browserPanel.getChildren().remove(personProfile.getRoot());
        } catch (Exception e) {
            logger.info("PersonProfilePanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }

        browserPanel.getChildren().add(browser.getRoot());
        String personUrl = GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString() + GOOGLE_SEARCH_URL_SUFFIX;
        browser.loadPage(personUrl);
    }

    /**
     * @param reminder
     */
    private void displayReminder(ReadOnlyReminder reminder) {
        try {
            browserPanel.getChildren().remove(personProfile.getRoot());
        } catch (Exception e) {
            logger.info("PersonProfilePanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(displayPanel.getRoot());
        } catch (Exception e) {
            logger.info("DisplayPanel does not exist");
        }
        try {
            browserPanel.getChildren().remove(browser.getRoot());
        } catch (Exception e) {
            logger.info("BrowserPanel does not exist");
        }
        displayPanel = new DisplayPanel(reminder);
        browserPanel.getChildren().add(displayPanel.getRoot());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser.freeResources();
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleReminderPanelSelectionChangedEvent(ReminderPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayReminder(event.getNewSelection().reminder);
    }
```
###### \java\seedu\address\ui\DisplayPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * The Display Panel of the App.
 */
public class DisplayPanel  extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String FXML = "ReminderDisplay.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;


    @FXML
    private Label task;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private TextArea message;
    @FXML
    private FlowPane tags;

    public DisplayPanel(ReadOnlyReminder reminder) {
        super(FXML);
        this.reminder = reminder;
        initTags(reminder);
        setDisplay(reminder);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void setDisplay(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
```
###### \java\seedu\address\ui\ReminderCard.java
``` java
package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";

    private static String[] colors = { "red", "gold", "blue", "purple", "orange", "brown",
        "green", "magenta", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyReminder reminder;

    @javafx.fxml.FXML
    private HBox remindercardPane;
    @FXML
    private Label task;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label datentime;
    @FXML
    private Label message;
    @FXML
    private FlowPane tags;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        initTags(reminder);
        bindListeners(reminder);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder reminder) {
        task.textProperty().bind(Bindings.convert(reminder.taskProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        datentime.textProperty().bind(Bindings.convert(reminder.dateProperty()));
        message.textProperty().bind(Bindings.convert(reminder.messageProperty()));
        reminder.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(reminder);
        });
    }

    /**
     * @param reminder
     */
    private void initTags(ReadOnlyReminder reminder) {
        reminder.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && reminder.equals(card.reminder);
    }
}

```
###### \java\seedu\address\ui\ReminderListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToReminderRequestEvent;
import seedu.address.commons.events.ui.ReminderPanelSelectionChangedEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @javafx.fxml.FXML
    private ListView<ReminderCard> reminderListView;

    public ReminderListPanel(ObservableList<ReadOnlyReminder> reminderList) {
        super(FXML);
        setConnections(reminderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyReminder> reminderList) {
        ObservableList<ReminderCard> mappedList = EasyBind.map(
                reminderList, (reminder) -> new ReminderCard(reminder, reminderList.indexOf(reminder) + 1));
        reminderListView.setItems(mappedList);
        reminderListView.setCellFactory(listView -> new ReminderListPanel.ReminderListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        reminderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new ReminderPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            reminderListView.scrollTo(index);
            reminderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToReminderRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class ReminderListViewCell extends ListCell<ReminderCard> {

        @Override
        protected void updateItem(ReminderCard reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(reminder.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Override
    public void restart(String userName) {
        stop();
        primaryStage = new Stage();

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(userPrefs.getAddressBookFilePath(userName));
        storage = new StorageManager(addressBookStorage, userPrefsStorage, dataBaseStorage);
        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        prefs = userPrefs;


        primaryStage.setTitle(config.getAppTitle() + " " + userName);


        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            logger.info("Fatal error during initializing" + e);
        }
    }

    /**
     * get new user prefs
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {

        String prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;

        try {

            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();

            initializedPrefs = prefsOptional.orElse(new UserPrefs());

        } catch (DataConversionException e) {

            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {

            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {

            storage.saveUserPrefs(initializedPrefs);

        } catch (IOException e) {

            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }
    /**
     *
     * @param storage
     * @param userPrefs
     * @return
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        Optional<ReadOnlyDatabase> databaseOptional;
        ReadOnlyAddressBook initialData;
        ReadOnlyDatabase initialDatabase;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }

        try {
            databaseOptional = storage.readDatabase();
            if (!databaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a empty AddressBook");
                initialDatabase = new Database();
            }
            initialDatabase = databaseOptional.orElseGet(SampleDataUtil::getSampleDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialDatabase = new Database();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialDatabase = new Database();
        }

        return new ModelManager(initialData, initialDatabase, userPrefs);
    }
```
###### \resources\view\Browser.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>


<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <WebView fx:id="browser" />
</StackPane>
```
###### \resources\view\BrowserPanel.fxml
``` fxml
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane fx:id="browserPanel" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.111">

</StackPane>
```
###### \resources\view\ReminderDisplay.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<?import javafx.scene.control.TextArea?>
<HBox id="remindercardPane" fx:id="remindercardPane" maxWidth="350" maxHeight="400" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="25" right="25" bottom="25" left="25" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="task" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="priority" styleClass="cell_big_label" text="\$priority" />
            <Label fx:id="datentime" styleClass="cell_big_label" text="\$datentime" />
            <TextArea fx:id="message" styleClass="reminder-display" editable="false" text="\$message" wrapText="true" maxWidth="300" prefRowCount="10" />
            <FlowPane fx:id="tags" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\ReminderListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<HBox id="remindercardPane" fx:id="remindercardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="task" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="priority" styleClass="cell_small_label" text="\$priority" />
            <Label fx:id="datentime" styleClass="cell_small_label" text="\$datentime" />
            <Label fx:id="message" styleClass="cell_small_label" text="\$message" />
            <FlowPane fx:id="tags" />
        </VBox>
    </GridPane>
</HBox>
```
