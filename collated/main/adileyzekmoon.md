# adileyzekmoon
###### \java\seedu\address\logic\commands\FilterAllCommand.java
``` java
    public static final String COMMAND_WORD = "filterall";
    public static final String COMMAND_ALIAS = "fa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonContainsAllKeywordsPredicate predicate;

    public FilterAllCommand(PersonContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "fi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose parameters contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonContainsKeywordsPredicate predicate;

    public FilterCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
```
###### \java\seedu\address\model\person\PersonContainsAllKeywordsPredicate.java
``` java
public class PersonContainsAllKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsAllKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getAsText(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsAllKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicate.java
``` java
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(person.getAsText(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    public static final String DEFAULT_PAGE = "default.html";
    public static final String FACEBOOK_SEARCH_URL_PREFIX = "https://www.facebook.com/public/?query=";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "%20"));
    }
```
