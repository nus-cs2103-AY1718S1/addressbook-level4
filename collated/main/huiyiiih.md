# huiyiiih
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.exceptions.InvalidSortTypeException;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_UNKNOWN_SORT_TYPE = "Sorting type not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list according to either name, tag, "
            + "company, priority, status\n"
            + "Parameters: TYPE  (name, tag, company, priority, status)\n"
            + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "Sorted according to ";
    //{"Sorted according to name", "Sorted according to tags", "Sorted " + " according to address"};
    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortPerson(type);
            return new CommandResult(MESSAGE_SUCCESS + type);
        } catch (InvalidSortTypeException iste) {
            return new CommandResult(MESSAGE_UNKNOWN_SORT_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.type == (((SortCommand) other).type)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\CheckCommandsParser.java
``` java
package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Parses the command entered by the user
 * and check it with the synonyms provided
 * so that the user does not have to remember specific commands
 */
public class CheckCommandsParser {

    /**
     * Parsers user input command and match it with the synonyms/aliases
     *
     * @param userCommand user input command string
     * @return the relevant command it matches
     */
    public static String matchCommand(String userCommand) {
        /**
         * sets the initial finalUserCommand to error
         */
        String finalUserCommand = "error";

        /**
         * used to store the userCommand in a set
         */
        Set<String> userInputCommand = new HashSet<>();
        userInputCommand.add(userCommand);

        /**
         * All the synonyms/aliases of the different commands
         */
        final String[] subAddCommands = new String[] { "add", "a", "input", "create", "insert" };
        final String[] subClearCommands = new String[] { "clear", "c", "empty", "clean" };
        final String[] subDeleteCommands = new String[] { "delete", "d", "remove", "throw", "erase" };
        final String[] subEditCommands = new String[] { "edit", "e", "change", "revise", "modify" };
        final String[] subExitCommands = new String[] { "exit", "quit" };
        final String[] subFindCommands = new String[] { "find", "f", "look", "search", "check" };
        final String[] subHelpCommands = new String[] { "help", "info" };
        final String[] subHistoryCommands = new String[] { "history", "h", "past" };
        final String[] subListCommands = new String[] { "list", "l", "show", "display" };
        final String[] subRedoCommands = new String[] { "redo", "r" };
        final String[] subSelectCommands = new String[] { "select", "s", "choose", "pick" };
        final String[] subSortCommands = new String[] { "sort", "arrange", "organise" };
        final String[] subUndoCommands = new String[] { "undo", "u" };
        final String[] subCheckScheduleCommands = new String[] {"thisweek",
            "schedule", "checkschedule", "tw", "cs"};
        final String[] subAddEventsCommands = new String[] { "eventadd", "addevent", "ae", "ea" };
        final String[] subDeleteEventsCommands = new String[] { "eventdel",
            "delevent", "deleteevent", "eventdelete", "de", "ed" };
        final String[] subEditEventsCommands = new String[] { "eventedit", "editevent", "ee" };
        final String[] subFindEventsCommands = new String[] { "eventfind", "findevent", "fe", "ef" };
        final String[] subSetRelCommands = new String[] { "set", "rel", "setrel" };


        /**
         * Sets all the strings in each command into a HashSet
         */
        final Set<String> commandsForAdd = new HashSet<>(Arrays.asList(subAddCommands));
        final Set<String> commandsForClear = new HashSet<>(Arrays.asList(subClearCommands));
        final Set<String> commandsForDelete = new HashSet<>(Arrays.asList(subDeleteCommands));
        final Set<String> commandsForEdit = new HashSet<>(Arrays.asList(subEditCommands));
        final Set<String> commandsForExit = new HashSet<>(Arrays.asList(subExitCommands));
        final Set<String> commandsForFind = new HashSet<>(Arrays.asList(subFindCommands));
        final Set<String> commandsForHelp = new HashSet<>(Arrays.asList(subHelpCommands));
        final Set<String> commandsForHistory = new HashSet<>(Arrays.asList(subHistoryCommands));
        final Set<String> commandsForList = new HashSet<>(Arrays.asList(subListCommands));
        final Set<String> commandsForRedo = new HashSet<>(Arrays.asList(subRedoCommands));
        final Set<String> commandsForSelect = new HashSet<>(Arrays.asList(subSelectCommands));
        final Set<String> commandsForSort = new HashSet<>(Arrays.asList(subSortCommands));
        final Set<String> commandsForUndo = new HashSet<>(Arrays.asList(subUndoCommands));
        final Set<String> commandsForCheckSchedule = new HashSet<>(Arrays.asList
                (subCheckScheduleCommands));
        final Set<String> commandsForAddEvent = new HashSet<>(Arrays.asList(subAddEventsCommands));
        final Set<String> commandsForDeleteEvent = new HashSet<>(Arrays.asList(subDeleteEventsCommands));
        final Set<String> commandsForEditEvent = new HashSet<>(Arrays.asList(subEditEventsCommands));
        final Set<String> commandsForFindEvent = new HashSet<>(Arrays.asList(subFindEventsCommands));
        final Set<String> commandsForSetRel = new HashSet<>(Arrays.asList(subSetRelCommands));

        /**
         * Compares the userInputCommand with the different commands set
         */
        if (!Collections.disjoint(userInputCommand, commandsForAdd)) {
            finalUserCommand = "add";
        } else if (!Collections.disjoint(userInputCommand, commandsForClear)) {
            finalUserCommand = "clear";
        } else if (!Collections.disjoint(userInputCommand, commandsForDelete)) {
            finalUserCommand = "delete";
        } else if (!Collections.disjoint(userInputCommand, commandsForEdit)) {
            finalUserCommand = "edit";
        } else if (!Collections.disjoint(userInputCommand, commandsForExit)) {
            finalUserCommand = "exit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFind)) {
            finalUserCommand = "find";
        } else if (!Collections.disjoint(userInputCommand, commandsForHelp)) {
            finalUserCommand = "help";
        } else if (!Collections.disjoint(userInputCommand, commandsForHistory)) {
            finalUserCommand = "history";
        } else if (!Collections.disjoint(userInputCommand, commandsForList)) {
            finalUserCommand = "list";
        } else if (!Collections.disjoint(userInputCommand, commandsForRedo)) {
            finalUserCommand = "redo";
        } else if (!Collections.disjoint(userInputCommand, commandsForSelect)) {
            finalUserCommand = "select";
        } else if (!Collections.disjoint(userInputCommand, commandsForSort)) {
            finalUserCommand = "sort";
        } else if (!Collections.disjoint(userInputCommand, commandsForUndo)) {
            finalUserCommand = "undo";
        } else if (!Collections.disjoint(userInputCommand, commandsForCheckSchedule)) {
            finalUserCommand = "thisweek";
        } else if (!Collections.disjoint(userInputCommand, commandsForAddEvent)) {
            finalUserCommand = "eventadd";
        } else if (!Collections.disjoint(userInputCommand, commandsForDeleteEvent)) {
            finalUserCommand = "eventdel";
        } else if (!Collections.disjoint(userInputCommand, commandsForEditEvent)) {
            finalUserCommand = "eventedit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFindEvent)) {
            finalUserCommand = "eventfind";
        } else if (!Collections.disjoint(userInputCommand, commandsForSetRel)) {
            finalUserCommand = "set";
        }
        return finalUserCommand;
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    /* Prefix definitions for Relationship*/
    public static final Prefix PREFIX_ADD_RELATIONSHIP = new Prefix("ar/");
    public static final Prefix PREFIX_DELETE_RELATIONSHIP = new Prefix("dr/");
    public static final Prefix PREFIX_EMPTY_RELATIONSHIP = new Prefix("cr/");
    //@author
}
```
###### \java\seedu\address\logic\parser\relationship\SetRelCommandParser.java
``` java
package seedu.address.logic.parser.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY_RELATIONSHIP;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.Relationship;

/**
 * Parses all input arguments and create a new AddRelCommand object
 */
public class SetRelCommandParser implements Parser<SetRelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRelCommand
     * and returns an AddRelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_RELATIONSHIP,
            PREFIX_DELETE_RELATIONSHIP, PREFIX_EMPTY_RELATIONSHIP);
        Index indexOne;
        Index indexTwo;
        String value;
        String[] indexes;

        value = argMultimap.getPreamble();
        try {
            indexes = value.split("\\s+");
            indexOne = ParserUtil.parseIndex(indexes[0]);
            indexTwo = ParserUtil.parseIndex(indexes[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        EditPerson editPerson = new EditPerson();
        try {
            parseRelForEdit(argMultimap.getAllValues(PREFIX_ADD_RELATIONSHIP)).ifPresent
                (editPerson::setToAdd);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_DELETE_RELATIONSHIP)).ifPresent
                (editPerson::setToRemove);
            editPerson.setClearRels(argMultimap.containsPrefix(PREFIX_EMPTY_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (!editPerson.isAnyFieldEdited()) {
            throw new ParseException(SetRelCommand.MESSAGE_NOT_EDITED);
        }

        return new SetRelCommand(indexOne, indexTwo, editPerson);
    }
    /**
     * Parses {@code Collection<String> relationships} into a {@code Set<Relationship>}
     * if {@code relationships} is non-empty.
     * If {@code relationship} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Relationship>} containing zero relationships.
     */
    private Optional<Set<Relationship>> parseRelForEdit(Collection<String> rel) throws IllegalValueException {
        assert rel != null;

        if (rel.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> relSet = rel.size() == 1 && rel.contains("") ? Collections.emptySet() : rel;
        return Optional.of(ParserUtil.parseRel(relSet));
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedValue = args.trim();
        if (trimmedValue.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedValue);
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts person according to user input option
     */
    void sortPerson(String type) throws InvalidSortTypeException;
    //@author
    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPerson(String type) throws InvalidSortTypeException {
        addressBook.sortPerson(type);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    Set<Relationship> getRelation();
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the person list according to the type entered by user
     * @param type                          Sorting type entered by user
     * @throws InvalidSortTypeException     Indicates that the sorting type empty by user is not valid
     */
    public void sortPersonList(String type) throws InvalidSortTypeException {
        final Comparator<Person> sortByName = (
                Person a, Person b) -> a.getName().toString().compareToIgnoreCase(b.getName().toString());
        final Comparator<Person> sortByTags = (Person a, Person b) -> a.getTags().toString().compareToIgnoreCase((b
                .getTags().toString()));
        final Comparator<Person> sortByCompany = (Person a, Person b) -> a.getCompany().toString().compareToIgnoreCase(b
                .getCompany().toString());
        final Comparator<Person> sortByPriority = (Person a, Person b) -> a.getPriority().toString()
                .compareToIgnoreCase(b.getPriority().toString());
        final Comparator<Person> sortByStatus = (Person a, Person b) -> a.getStatus().toString().compareToIgnoreCase(b
                .getStatus().toString());
        switch (type) {
        case "name":
            internalList.sort(sortByName);
            break;
        case "tag":
            internalList.sort(sortByTags);
            break;
        case "company":
            internalList.sort(sortByCompany);
            break;
        case "priority":
            internalList.sort(sortByPriority);
            break;
        case "status":
            internalList.sort(sortByStatus);
            break;
        default:
            //System.out.printf("Sorting type entered not found!\n");
            throw new InvalidSortTypeException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    ObservableList<Relationship> getRelList();
    //author
    /**
     * Returns the last changed event.
     */
    ReadOnlyEvent getLastChangedEvent();

    Date getCurrentDate();
}
```
###### \java\seedu\address\model\relationship\Relationship.java
``` java
    /**
     * Concatenates the name of the given person and the relationship to form a string
     * @param name          Name of the person
     * @param relation      Relationship of the person
     */
    public Relationship(String name, Set<Relationship> relation) {
        this.relType = name + " " +  relation;
    }
```
