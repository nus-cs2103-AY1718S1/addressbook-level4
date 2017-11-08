# Affalen
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (non-case sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/Alice \n"
            + "Example: " + COMMAND_WORD + " p/12345678 \n"
            + "Example: " + COMMAND_WORD + " a/138 Clementi Road \n"
            + "Example: " + COMMAND_WORD + " t/[Friends] \n"
            + "Example: " + COMMAND_WORD + " r/Likes coffee \n"
            + "Example: " + COMMAND_WORD + " b/21-10-1995 \n";

    private Predicate predicate;
    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    private final Index index;
    private final Remark remark;
    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);
        this.index = index;
        this.remark = remark;
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getBirthday(),
                personToEdit.getAge(), personToEdit.getPhoto(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.getFilteredPersonList();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**Generate success message*/
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }
        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case RemarkCommand.COMMAND_ALIAS:
        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        /**
         * Used for initial separation of command word and args.
         */
        final Pattern commandFormat = Pattern.compile("(?<commandWord>\\w/)(?<arguments>.*)");
        final Matcher matcher = commandFormat.matcher(args.trim());
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        String[] keywords = arguments.split("\\s", 0);

        switch(commandWord) {

        case ("n/"):
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("p/"):
            return new FindCommand(new NumberContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("e/"):
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(keywords)));

        case ("t/"):
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("a/"):
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("r/"):
            return new FindCommand(new RemarkContainsKeywordsPredicate(Arrays.asList(keywords)));

        case("b/"):
            return new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList(keywords)));

        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/***/
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\person\BirthdayContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches any of the keywords given.
 */
public class BirthdayContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public BirthdayContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBirthday().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((BirthdayContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\NumberContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Number} matches any of the keywords given.
 */
public class NumberContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumberContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NumberContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public Remark getRemark() {
        return remark.get();
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                && other.getRemark().equals(this.getRemark()))
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Remark: ")
                .append(getRemark())
```
###### \java\seedu\address\model\person\Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";
    public final String value;
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\RemarkContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Remark} matches any of the keywords given.
 */
public class RemarkContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getRemark().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((RemarkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        ArrayList <Tag> tags = new ArrayList<>(person.getTags());
        return keywords.stream()
                .anyMatch(keywords -> tags.stream().anyMatch(tagLabel -> keywords.contains(tagLabel.toString())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String remark;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label remark;
```
