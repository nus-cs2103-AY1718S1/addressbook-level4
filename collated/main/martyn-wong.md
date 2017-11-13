# martyn-wong
###### /java/seedu/address/commons/events/ui/MapPersonEvent.java
``` java
/**
 * Represents a mapping function call by user
 */
public class MapPersonEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public MapPersonEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }
}
```
###### /java/seedu/address/commons/core/index/Selection.java
``` java
/**
 * To get or set the selection state of the application
 */
public class Selection {

    private static boolean isPersonSelected = false;

    public static void setPersonSelected() {
        isPersonSelected = true;
    }

    public static void setPersonNotSelected() {
        isPersonSelected = false;
    }

    public static boolean getSelectionStatus() {
        return isPersonSelected;
    }
}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com.sg/search?safe=off&q=";
    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h";
    private static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps?safe=off&q=";
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /***
     * Loads google map of person
     * @param person
     */
    private void loadPersonMap(ReadOnlyPerson person) throws CommandException {
        if (personSelected == null) {
            throw new CommandException("Please select a person");
        }
        setMapsChosenTrue();
        setLinkedinChosenFalse();
        loadPage(GOOGLE_MAPS_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * Setter method to set the Boolean value of hasMapsBeenChosen
     */
    public void setMapsChosenTrue () {
        hasMapsBeenChosen = true;
    }

    public void setMapsChosenFalse () {
        hasMapsBeenChosen = false;
    }
```
###### /java/seedu/address/logic/parser/SearchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/MapCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapCommand object
 */
public class MapCommandParser implements Parser<MapCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public MapCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MapCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/commands/SearchCommand.java
``` java
/**
 * Searches the address book for any parameters that match the given keyword.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "se";

    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " KEYWORDS";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "bernice serangoon";

    private final PersonContainsKeywordsPredicate predicate;

    public SearchCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);

        int searchResultsCount = model.getFilteredPersonList().size();

        if (searchResultsCount != NO_RESULTS) {
            model.recordSearchHistory();
        }

        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && this.predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/MapCommand.java
``` java
/**
 *  Returns selected person's address in google map search in browser panel
 */
public class MapCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " INDEX";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the address of person on Google Maps "
            + "identified by the index number used in the last person listing. "
            + "Parameters: INDEX "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_MAP_SHOWN_SUCCESS = "Map Display Successful! Address of: %1$s";

    public final Index index;

    public MapCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (Selection.getSelectionStatus() == false && index.getOneBased() <= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_SELECTED);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        try {
            model.mapPerson(personToShow);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_MAP_SHOWN_SUCCESS, personToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.index.equals(((MapCommand) other).index)); // state check
    }
}
```
###### /java/seedu/address/model/person/PersonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Parameters} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAsText(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList() {
        updateFilteredPersonList();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        raise(new MapPersonEvent(target));
    }

}
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    default void updateFilteredPersonList() {
        updateFilteredPersonList();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Shows the google map for the selected person in the browser panel
     */
    void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException;
}
```
