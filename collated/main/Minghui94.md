# Minghui94
###### /java/seedu/address/logic/commands/FindTagCommand.java
``` java
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_HELP = "findtag [TAG]";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Find all persons whose tags contain any of "
            + "the specific keyword (case-sensitive) and display them as a list with index numbers.\n"
            + "Parameters: [MORE KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleague family";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FindTagCommand
                && this.predicate.equals(((FindTagCommand) other).predicate));
    }
}
```
###### /java/seedu/address/logic/commands/SortAscendingCommand.java
``` java
/**
 * Sort and list out all the contacts in the Address Book
 * Order is ascending, from W to A
 */
public class SortAscendingCommand extends Command {

    public static final String COMMAND_WORD = "sortAscend";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "List all persons by name "
            + "in ascending alphabetical order.\n ";

    @Override
    public CommandResult execute() {
        model.sortNameAscend();
        return new CommandResult("List is now arranged in ascending order.\n");
    }
}
```
###### /java/seedu/address/logic/parser/FindEmailCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindEmailCommand object
 */
public class FindEmailCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEmailCommand
     * and returns an FindEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEmailCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEmailCommand.MESSAGE_USAGE));
        }

        String[] emailKeywords = trimmedArgs.split("\\s+");

        return new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
    }
}
```
###### /java/seedu/address/logic/parser/FindTagCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     *  parses the given {@code String} of arguments in the context of the FindTagCommand
     *  and returns an FindTagCommand object for execution.
     *  @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public FindTagCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedInput.split("\\s+");
        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}
```
###### /java/seedu/address/model/person/EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
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
