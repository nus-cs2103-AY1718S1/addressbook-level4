# Zzmobie
###### \java\seedu\address\commons\events\ui\AccessCountDisplayToggleEvent.java
``` java
/** Toggles access count display as necessary*/
public class AccessCountDisplayToggleEvent extends BaseEvent {

    private final boolean isDisplayed;

    public AccessCountDisplayToggleEvent(boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
/**
 * Finds and lists all persons in address book who has any tag containing any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who has any tag containing any "
            + "of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ToggleAccessDisplayCommand.java
``` java
/**
 * Toggles the display of the access count.
 */
public class ToggleAccessDisplayCommand extends Command {
    public static final String COMMAND_WORD = "accessdisplay";
    public static final String COMMAND_ALIAS = "ad";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles the display of the access count. "
            + "Parameters: TYPE (either \"on\" or \"off\")\n"
            + "Example: " + COMMAND_WORD + " on";
    public static final String MESSAGE_SUCCESS = "Display toggled ";

    public static final String TYPE_ON = "on";
    public static final String TYPE_OFF = "off";

    private boolean isDisplayed;

    public ToggleAccessDisplayCommand (boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new AccessCountDisplayToggleEvent(isDisplayed));
        return new CommandResult(MESSAGE_SUCCESS + (isDisplayed ? TYPE_ON : TYPE_OFF) + ". ");
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ToggleAccessDisplayCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ToggleAccessDisplayCommand object
 */
public class ToggleAccessDisplayCommandParser implements Parser<ToggleAccessDisplayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleAccessDisplayCommand
     * and returns an ToggleAccessDisplayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleAccessDisplayCommand parse(String args) throws ParseException {
        if (args.trim().equalsIgnoreCase(TYPE_ON)) {
            return new ToggleAccessDisplayCommand(true);
        } else if (args.trim().equalsIgnoreCase(TYPE_OFF)) {
            return new ToggleAccessDisplayCommand(false);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ToggleAccessDisplayCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\person\AccessCount.java
``` java
/**
 * Represents the number of accesses to a person's data.
 */
public class AccessCount {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Number of access cannot be less than 0.";

    private int value;

    public AccessCount(int accessCount) {
        this.value = accessCount;
    }

    @Override
    public String toString() {
        return "Accesses: " + Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessCount // instanceof handles nulls
                && this.value == ((AccessCount) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

    public int numAccess() {
        return value;
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tags = person.getTags();
        for (Tag t : tags) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(t.toTagName(), keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleAccessCountDisplayToggleEvent(AccessCountDisplayToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + event.isDisplayed());
        if (event.isDisplayed()) {
            personListView.setItems(mappedListWithAccessCount);
        } else {
            personListView.setItems(mappedListWithoutAccessCount);
        }
        personListView.refresh();
    }

```
