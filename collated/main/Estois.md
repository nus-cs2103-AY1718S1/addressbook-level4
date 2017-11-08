# Estois
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
            + "or " + COMMAND_WORD + " 1 3 4";
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    private ArrayList <Index> targetIndex = new ArrayList <>();

    public DeleteCommand(ArrayList <Index> targetIndex) {
        this.targetIndex = targetIndex;
    }
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (int x = 0; x < targetIndex.size(); x++) {
            if (targetIndex.get(x).getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        ArrayList<ReadOnlyPerson> personToDelete = new ArrayList<>();
        for (int x = 0; x < targetIndex.size(); x++) {
            personToDelete.add(lastShownList.get(targetIndex.get(x).getZeroBased()));
        }
        try {
            for (int x = 0; x < personToDelete.size(); x++) {
                model.deletePerson(personToDelete.get(x));
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        String outputResult = MESSAGE_DELETE_PERSON_SUCCESS;
        outputResult = String.format(outputResult, personToDelete.get(0));
        for (int x = 1; x < personToDelete.size(); x++) {
            outputResult = outputResult + "\n";
            String temp = personToDelete.get(x).toString();
            outputResult += temp;
        }
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EmptyBookException;


/**
 * Sorts the list of people based on given parameter
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";
    public static final String MESSAGE_SORT_SUCCESS = "Peersonals sorted by %1$s.";
    public static final String MESSAGE_EMPTY_BOOK = "Peersonals is currently empty. Unable to sort.";
    public static final String SORT_MULTIPLE_INPUT = "Only one parameter can be entered.";

    private static final String PREFIX_NAME = "n/";
    private static final String PREFIX_PHONE = "p/";
    private static final String PREFIX_EMAIL = "e/";
    private static final String PREFIX_ADDRESS = "a/";
    private static final String PREFIX_AGE = "o/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts peers in ascending order according to specified parameter (Sorts by name by default)\n"
            + "Parameters: "
            + "Prefix\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "\n"
            + "Accepted Parameters: (n/ p/ e/ a/ o/)";

    private final String parameter;
    private String sortParam;

    public SortCommand(String parameter) {
        requireNonNull(parameter);

        this.parameter = parameter;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> sortComparator = getSortComparator(this.parameter);
        try {
            model.sortPerson(sortComparator);
        } catch (EmptyBookException a) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortParam));
    }

    private Comparator<ReadOnlyPerson> getSortComparator(String parameter) {
        switch (parameter) {
        case PREFIX_NAME:
            this.sortParam = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());

        case PREFIX_EMAIL:
            this.sortParam = "email";
            return (o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(o2.getEmail().toString());

        case PREFIX_PHONE:
            this.sortParam = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString());

        case PREFIX_ADDRESS:
            this.sortParam = "address";
            return (o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(o2.getAddress().toString());

        case PREFIX_AGE:
            this.sortParam = "age";
            return (o1, o2) -> o1.compareAge(o2);

        default:
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case SortCommand.COMMAND_ALIAS:
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_AGE = new Prefix("o/");
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
            ArrayList<Index> index = ParserUtil.parseMultiIndex(args);
            if (index.size() == 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses multiple {@code oneBasedIndex} into a {@code Index} ArrayList and returns it. Leading
     * and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static ArrayList<Index> parseMultiIndex(String multiBasedIndex) throws IllegalValueException {
        ArrayList<Index> parsedIndexes = new ArrayList <> ();
        multiBasedIndex = multiBasedIndex.trim();
        String[] splitString = multiBasedIndex.split(" ");
        ArrayList<String> splitParse = new ArrayList<>();
        for (int x = 0; x < splitString.length; x++) {
            splitParse.add(splitString[x]);
        }
        for (int x = 0; x < splitParse.size(); x++) {
            splitParse.set(x, splitParse.get(x).trim());
            if (!StringUtil.isNonZeroUnsignedInteger(splitParse.get(x))) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
        }
        for (int x = 0; x < splitParse.size(); x++) {
            parsedIndexes.add(Index.fromOneBased(Integer.parseInt(splitParse.get(x))));
        }
        return parsedIndexes;
    }
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.function.Consumer;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private String parameter;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        this.parameter = "invalid";

        if (args.equals("")) {
            args = " n/";
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_AGE);

        if (argMultimap.size() > 2) {
            throw new ParseException(String.format(SortCommand.SORT_MULTIPLE_INPUT, SortCommand.MESSAGE_USAGE));
        }

        if (argMultimap.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        argMultimap.getValue(PREFIX_NAME).ifPresent(updateParam(PREFIX_NAME));
        argMultimap.getValue(PREFIX_PHONE).ifPresent(updateParam(PREFIX_PHONE));
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(updateParam(PREFIX_EMAIL));
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(updateParam(PREFIX_ADDRESS));
        argMultimap.getValue(PREFIX_AGE).ifPresent(updateParam(PREFIX_AGE));

        if (parameter.equals("invalid")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand (parameter);
    }

    private Consumer<String> updateParam (Prefix prefix) {
        return s -> parameter = prefix.toString();
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void sortPerson(Comparator<ReadOnlyPerson> sortBy) throws EmptyBookException {
        persons.sort(sortBy);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public int compareAge(ReadOnlyPerson o1) {
        if (this.getAge().toString().equals("") && o1.getAge().toString().equals("")) {
            return 0;
        }

        if (this.getAge().toString().equals("")) {
            return 1;
        }

        if (o1.getAge().toString().equals("")) {
            return -1;
        }

        return this.getAge().toString().compareToIgnoreCase(o1.getAge().toString());
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts persons in address book by field and in order specified.
     * @param sortComparator
     * @throws EmptyBookException
     */
    public void sort(Comparator<ReadOnlyPerson> sortComparator) throws EmptyBookException {
        requireNonNull(sortComparator);
        if (internalList.size() < 1) {
            throw new EmptyBookException();
        }

        Collections.sort(internalList, sortComparator);
    }
```
