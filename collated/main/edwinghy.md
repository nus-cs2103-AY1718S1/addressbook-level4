# edwinghy
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;

import java.io.IOException;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Export selected person/s by the index number or range in the last person listing.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export selected person/s\n"
            + "by the index number or range in the last person listing\n"
            + "Parameters: " + PREFIX_RANGE + "[RANGE] "
            + PREFIX_PATH + "[PATH]\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_RANGE + "all " + PREFIX_PATH + "C:\\Exports\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_RANGE + "1 " + PREFIX_PATH + "C:\\Exports\n"
            + "Example 3: " + COMMAND_WORD + " " + PREFIX_RANGE + "1,2 " + PREFIX_PATH + "C:\\Exports\n"
            + "Example 4: " + COMMAND_WORD + " " + PREFIX_RANGE + "1-5 " + PREFIX_PATH + "C:\\Exports";

    public static final String MESSAGE_ARGUMENTS = "Range: %1$s, Path: %2$s";

    public static final String MESSAGE_EXPORT_FAIL = "Export Failed Invalid RANGE or PATH";
    public static final String MESSAGE_EXPORT_SUCCESS = "Export Successful";

    private final String range;
    private final String path;
    private AddressBook exportBook;

    public ExportCommand(String range, String path) {
        requireNonNull(range);
        requireNonNull(path);

        this.range = range;
        this.path = path;
        exportBook = new AddressBook();
    }

    @Override
    public CommandResult execute() throws CommandException {

        String[] multipleRange = getRangeFromInput();

        if (multipleRange[0].equals("all")) {
            exportAll();
        } else {
            for (int i = 0; i < multipleRange.length; i++) {
                if (multipleRange[i].contains("-")) {
                    String[] rangeToExport = multipleRange[i].split("-");
                    try {
                        exportRange(Integer.parseInt(rangeToExport[0]), Integer.parseInt(rangeToExport[1]));
                    } catch (NumberFormatException e) {
                        throw new CommandException(MESSAGE_EXPORT_FAIL);
                    }

                } else {
                    try {
                        exportSpecific(Integer.parseInt(multipleRange[i]));
                    } catch (NumberFormatException e) {
                        throw new CommandException(MESSAGE_EXPORT_FAIL);
                    }
                }
            }
        }

        try {
            AddressBookStorage storage = new XmlAddressBookStorage(path + ".xml");
            storage.saveAddressBook(exportBook);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_EXPORT_FAIL);
        }
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand e = (ExportCommand) other;
        return range.equals(e.range) && path.equals(e.path);
    }

    /**
     *Export all contacts from last shown list
     */
    private void exportAll() {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        try {
            exportBook.setPersons(lastShownList);
        } catch (DuplicatePersonException e) {
            assert false : "export file should not have duplicate persons";
        }
    }

    /**
     *Export a specific contact from last shown list
     */
    private void exportSpecific(int index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        try {
            if (index > lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            exportBook.addPerson(lastShownList.get(index - 1));
        } catch (DuplicatePersonException e) {
            assert false : "export file should not have duplicate persons";
        }
    }

    /**
     *Export a range of contacts from last shown list
     */
    private void exportRange(int start, int end) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        try {
            for (int i = start - 1; i <= end - 1; i++) {
                exportBook.addPerson(lastShownList.get(i));
            }
        } catch (DuplicatePersonException e) {
            assert false : "export file should not have duplicate persons";
        }
    }

    private String[] getRangeFromInput() {
        String[] splitStringComma = this.range.split(",");

        return splitStringComma;
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.PopularityCounter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Imports contacts to the address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import contacts to the address book."
            + "Parameters: "
            + PREFIX_PATH + "PATH";

    public static final String MESSAGE_SUCCESS = "%1$s person/s imported %2$s duplicate/s found";
    public static final String MESSAGE_INVALID_FILE = "Invalid file path %1$s.";

    private final String path;
    private final AddressBookStorage addressBookStorage;
    private AddressBook addressBook;
    private int numSuccess;
    private int numDuplicates;
    /**
     * Creates an ImportCommand to import the specified File
     */
    public ImportCommand(String path) {
        this.path = path;
        this.addressBookStorage = new XmlAddressBookStorage(path);
        numSuccess = 0;
        numDuplicates = 0;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            if (addressBookStorage.readAddressBook().isPresent()) {
                this.addressBook = new AddressBook(addressBookStorage.readAddressBook().get());
                for (ReadOnlyPerson person : this.addressBook.getPersonList()) {
                    Person personToAdd = new Person(person);
                    personToAdd.setPopularityCounter(new PopularityCounter());
                    personToAdd.setDisplayPicture(new DisplayPicture(""));
                    try {
                        model.clearSelection();
                        model.showDefaultPanel();
                        model.addPerson(personToAdd);
                        numSuccess++;
                    } catch (DuplicatePersonException e) {
                        numDuplicates++;
                    }
                }
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_FILE, path));
            }
        } catch (DataConversionException | IOException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_FILE, path));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numSuccess, numDuplicates));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && path.equals(((ImportCommand) other).path));
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

/**
 * Sorts the current list in alphabetical order
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the current list in alphabetical order "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "List Sorted";

    @Override
    public CommandResult execute() {

        model.sortFilteredPersonList();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@author


```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANGE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {
    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_RANGE, PREFIX_PATH);

        if (!arePrefixesPresent(argMultiMap, PREFIX_RANGE, PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        String range = argMultiMap.getValue(PREFIX_RANGE).orElse("");

        String path = argMultiMap.getValue(PREFIX_PATH).orElse("");

        return new ExportCommand(range, path);
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
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.util.stream.Stream;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    @Override
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_PATH);

        if (!arePrefixesPresent(argMultiMap, PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        String path = argMultiMap.getValue(PREFIX_PATH).orElse("");

        return new ImportCommand(path);
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
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersonList() {

        Comparator<ReadOnlyPerson> sortByName = (o1, o2) -> o1.getName().fullName.compareTo(o2.getName().fullName);
        sortedfilteredPersons.setComparator(sortByName);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNickname().value, keyword));
    }
```
