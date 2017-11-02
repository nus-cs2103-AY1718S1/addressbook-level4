# majunting
###### /java/seedu/address/logic/commands/DeleteTagCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * deletes a tag from all persons in addressbook.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a specific tag from all persons in addressbook.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " friends";

    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag deleted.";
    private static final String MESSAGE_DELETE_TAG_FAILED = "Tag deletion unsuccessful.";
    private final String keyword;

    public DeleteTagCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        boolean isTagDeleted;
        try {
            isTagDeleted = deleteTag(new Tag(keyword));
        } catch (IllegalValueException ive) {
            throw new CommandException(" ");
        }
        if (isTagDeleted) {
            return new CommandResult(MESSAGE_DELETE_TAG_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_DELETE_TAG_FAILED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.keyword.equals(((DeleteTagCommand) other).keyword)); // state check
    }

    /**
     * executes delete tag action, returns a boolean value to indicate status of tag deletion
     * @param tag
     * @return whether a tag is deleted successfully
     * @throws CommandException
     */
    private boolean deleteTag(Tag tag) throws CommandException {
        boolean isTagDeleted = false;
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();

        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson originalPerson = personList.get(i);
            Set<Tag> tagList = originalPerson.tagProperty().get().toSet();
            if (tagList.contains(tag)) {
                tagList.remove(tag);
                isTagDeleted = true;
            }
            Person newPerson = new Person(originalPerson.getName(), originalPerson.getPhone(),
                    originalPerson.getEmail(), originalPerson.getAddress(),
                    originalPerson.getBirthday(), originalPerson.getRemark(), tagList);
            try {
                model.updatePerson(originalPerson, newPerson);
                model.propagateToGroup(originalPerson, newPerson, this.getClass());
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, "Duplicate person found in addressbook");
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, "Person not found exception");
            }
        }
        return isTagDeleted;
    }
}
```
###### /java/seedu/address/logic/commands/ListAlphabetCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.NameStartsWithAlphabetPredicate;

/**
 * find persons whose name starts with a certain alphabet
 */

public class ListAlphabetCommand extends Command {

    public static final String COMMAND_WORD = "listalp";
    public static final String COMMAND_ALIAS = "la";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names start with "
            + "the specified alphabet (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: Alphabet\n"
            + "Example: " + COMMAND_WORD + "a";

    private final NameStartsWithAlphabetPredicate predicate;

    public ListAlphabetCommand(NameStartsWithAlphabetPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAlphabetCommand // instanceof handles nulls
                && this.predicate.equals(((ListAlphabetCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * sort the current person list by an attribute specified by the user
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts currently displayed contact list"
                        + " based on the attribute that is input by the user.\n"
                        + "Parameter: keyword (must be within 'name, phone, email, address')\n"
                        + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "All persons in the list are sorted by %1$s.\n";

    private final int keyword;
    private String attribute;

    public SortCommand(int argument) {
        this.keyword = argument;
    }

    public int getKeyword () {
        return keyword;
    }

    @Override
    public CommandResult execute () throws CommandException {
        model.sortBy(keyword);
        switch (keyword) {
        case 1: attribute = "phone";
                break;
        case 2: attribute = "email";
                break;
        case 3: attribute = "address";
                break;
        default: attribute = "name";
                break;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, attribute));
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.keyword == ((SortCommand) other).keyword); // state check
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses given {@code String} of argument in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);
        }

        String keyword = trimmedArgs;

        return new DeleteTagCommand(keyword);
    }

}
```
###### /java/seedu/address/logic/parser/ListAlphabetCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListAlphabetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;


/**
 * Parses input argument and creates a new ListAlphabetCommand object
 */
public class ListAlphabetCommandParser implements Parser<ListAlphabetCommand> {

    /**
     * Parses given {@code String} of argument in the context of the ListAlphabetCommand
     * and returns a ListAlphabetCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAlphabetCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ListAlphabetCommand.MESSAGE_USAGE);
        }

        String keyword = trimmedArgs;

        return new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(keyword));
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse user Input and create a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private final String[] attributes = {"name", "email", "phone", "address"};
    private final ArrayList<String> attributeList = new ArrayList<>(Arrays.asList(attributes));

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException
     */
    public SortCommand parse(String argument) throws ParseException {
        String trimmedArg = argument.trim();
        if (attributeList.contains(trimmedArg)) {
            switch (trimmedArg) {
            case "phone": return new SortCommand(1);
            case "email": return new SortCommand(2);
            case "address": return new SortCommand(3);
            default: return new SortCommand(0);
            }
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        }
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * sort the unique person list by the given attribute, then put the favorite contacts at the top of the list
     */
    public void sortPersonBy(int attribute) {
        persons.sortPersonBy(attribute);
        persons.sort();
    }
```
###### /java/seedu/address/model/person/NameStartsWithAlphabetPredicate.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Test that a {@code ReadOnlyPerson}'s {@code Name} starts with a certain alphabet
 */
public class NameStartsWithAlphabetPredicate implements Predicate<ReadOnlyPerson> {
    private final String alphabet;

    public NameStartsWithAlphabetPredicate(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return alphabet.charAt(0) == person.getName().fullName.charAt(0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameStartsWithAlphabetPredicate
                && this.alphabet.equals(((NameStartsWithAlphabetPredicate) other).alphabet));
    }
}
```
###### /java/seedu/address/model/person/predicates/FavoritePersonsPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests a {@code ReadOnlyPerson} is favorite
 */
public class FavoritePersonsPredicate implements Predicate<ReadOnlyPerson> {
    private final boolean favorite;
    public FavoritePersonsPredicate(String keyword) {
        if ("favorite".equals(keyword)) { this.favorite = true; }
        else { this.favorite = false; }
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getFavorite().favorite == this.favorite;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoritePersonsPredicate // instanceof handles nulls
                && this.favorite == ((FavoritePersonsPredicate) other).favorite); // state check
    }
}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * sort the list by a user specified attribute
     */
    public void sortPersonBy(int attribute) {
        this.attribute = attribute;
        internalList.sort((ReadOnlyPerson p1, ReadOnlyPerson p2) -> {
            switch (attribute) {
            case 1: return p1.getPhone().value.compareTo(p2.getPhone().value);
            case 2: return p1.getEmail().value.compareTo(p2.getEmail().value);
            case 3: return p1.getAddress().value.compareTo(p2.getAddress().value);
            default: return p1.getName().fullName.compareTo(p2.getName().fullName);
            }
        });
    }
```
