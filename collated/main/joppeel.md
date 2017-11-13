# joppeel
###### /java/seedu/address/logic/commands/LoadCommand.java
``` java
package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Loads contacts from a pre-existing address book to the current one.
 * The pre-existing address book' name is given as a parameter.
 */
public class LoadCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads contacts from a pre-existing address "
        + "book to the current one. The pre-existing address book' name is given as a parameter.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " myaddressbook.xml";

    public static final String MESSAGE_LOAD_ADDRESSBOOK_SUCCESS = "Successfully loaded the address book.";
    public static final String MESSAGE_ERROR_LOADING_ADDRESSBOOK = "The address book couldn't be read. "
        + "Make sure your file is in the right directory and that it's in the correct format.";

    private final ReadOnlyAddressBook addressBook;

    public LoadCommand(ReadOnlyAddressBook loadedAddressbook) {
        this.addressBook = loadedAddressbook;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> persons = addressBook.getPersonList();

        for (ReadOnlyPerson person : persons) {
            try {
                model.addPerson(person);

            } catch (DuplicatePersonException dpe) {
                // don't have to do anything as the person is already in the address book
            }
        }

        return new CommandResult(MESSAGE_LOAD_ADDRESSBOOK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LoadCommand // instanceof handles nulls
            && this.addressBook.equals(((LoadCommand) other).addressBook)); // state check
    }

}
```
###### /java/seedu/address/logic/parser/LoadCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.LoadCommand;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {

    private final String filePath = "data/";

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }

        String filePathWithFileName = filePath + trimmedArgs;

        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(filePathWithFileName);

        Optional<ReadOnlyAddressBook> inputtedAddressBook;
        try {
            inputtedAddressBook = addressBookStorage.readAddressBook();
        } catch (DataConversionException e) {
            throw new ParseException(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK);
        } catch (IOException e) {
            throw new ParseException(LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK);
        }

        return new LoadCommand(inputtedAddressBook.orElseThrow(() -> new ParseException(
            LoadCommand.MESSAGE_ERROR_LOADING_ADDRESSBOOK)));
    }

}

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birtday) throws IllegalValueException {
        requireNonNull(birtday);
        return birtday.isPresent() ? Optional.of(new Birthday(birtday.get())) : Optional.empty();
    }

```
###### /java/seedu/address/model/person/Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person's birthday should be in format: DD/MM/YYYY";

    /*
     * Birthday must be in the following format: DD/MM/YYYY,
     * Otherwise it's invalid.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d/\\d\\d/\\d\\d\\d\\d";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
