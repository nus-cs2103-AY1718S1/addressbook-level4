# thehelpfulbees
###### \java\seedu\address\logic\commands\FindCommand.java
``` java

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords,
 * or are included in a specified tag.
 * Keyword matching is case sensitive.
 * At present, only one tag can be searched for at a time
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Also allows for finding all persons in a specified tag, using the identifier \\t.\n"
            + "Parameters: [KEYWORD [MORE_KEYWORDS]]|[t\\TAG_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie \n"
            + "Example: " + COMMAND_WORD + " t\\friend";

    private static final String MESSAGE_DUPLICATE_PERSON_WHEN_UPDATING =
            "Duplicate person error when updating num times searched.";
    private static final String MESSAGE_PERSON_NOT_FOUND_WHEN_UPDATING =
            "Person not found when updating num times searched.";


    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }



    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(predicate);
        ObservableList<ReadOnlyPerson> filteredPersonList = model.getFilteredPersonList();

        for (ReadOnlyPerson personToEdit : filteredPersonList) {
            ReadOnlyPerson editedPerson = personToEdit;
            editedPerson.incrementNumTimesSearched();
            try {
                model.updatePerson(editedPerson, personToEdit);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON_WHEN_UPDATING);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError(MESSAGE_PERSON_NOT_FOUND_WHEN_UPDATING);
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(filteredPersonList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand) // other is FindCommand AND...
                && (((this.predicate instanceof NameContainsKeywordsPredicate) //it has a matching Name...Predicate OR
                && (((FindCommand) other).predicate) instanceof NameContainsKeywordsPredicate)
                && (this.predicate.equals(((FindCommand) other).predicate)))
                || (((this.predicate instanceof TagsContainKeywordPredicate) //it has a matching Tag...Predicate
                && (((FindCommand) other).predicate) instanceof TagsContainKeywordPredicate)
                && (this.predicate.equals(((FindCommand) other).predicate))); // state check
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        //command is used to search by tag
        if (nameKeywords[0].startsWith("t/")) {
            return new FindCommand(new TagsContainKeywordPredicate(nameKeywords[0].substring(2)));
        }

        //command is used to search by name
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java

    /**
     * Tests whether a {@code inputString} is contained in the array {@code items}.
     * @return True if it is contained, false otherwise
     */
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        for (String item:items) {
            if (item.equals(inputStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses {@code sortType}returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not valid sorting type).
     */
    public static String parseSortType(String sortType) throws IllegalValueException {
        String toSort = sortType.trim().toLowerCase();
        if (!stringContainsItemFromList(toSort, SORTNAME_ARGS)
                && !stringContainsItemFromList(toSort, SORTNUM_ARGS)
                && !stringContainsItemFromList(toSort, SORTADD_ARGS)
                && !stringContainsItemFromList(toSort, SORTEMAIL_ARGS)
                && !stringContainsItemFromList(toSort, SORTREMARK_ARGS)
                && !stringContainsItemFromList(toSort, SORTBIRTHDAY_ARGS)
                && !stringContainsItemFromList(toSort, SORTREMARK_ARGS)
                && !stringContainsItemFromList(toSort, SORTFAVOURITE_ARGS)
                && !stringContainsItemFromList(toSort, SORTNUMTIMESSEARCHED_ARGS)) {
            throw new IllegalValueException(MESSAGE_INVALID_SORT);
        }
        return toSort;
    }
```
###### \java\seedu\address\model\person\NumTimesSearched.java
``` java
/**
 * Counts number of times a person has been searched for
 * Guarantees: immutable;
 */
public class NumTimesSearched {

    public static final String MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS =
            "Initial value of NumTimesSearched should be >= 0";

    private static final int STARTING_VALUE = 0;

    private int value = STARTING_VALUE; //num times searched

    /**
     * Validates given Favourite.
     *
     * @throws IllegalValueException if given favourite string is invalid.
     */
    public NumTimesSearched(int initialValue) throws IllegalValueException {
        if (!isValidValue(initialValue)) {
            throw new IllegalValueException(MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS);
        }
        this.value = initialValue;
    }

    public NumTimesSearched() {
        this.value = STARTING_VALUE;
    }

    public void incrementValue() {
        value++;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidValue(int value) {
        return (value >= 0);
    }

    public int getValue() {
        return value;
    }

    public void setValue (int newValue) throws IllegalValueException {
        if (!isValidValue(newValue)) {
            throw new IllegalValueException(MESSAGE_NUM_TIMES_SEARCHED_CONSTRAINTS);
        }
        this.value = newValue;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumTimesSearched // instanceof handles nulls
                && this.value == ((NumTimesSearched) other).value); // state check
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java

    @Override
    public NumTimesSearched getNumTimesSearched() {
        return numTimesSearched.get();
    }

    @Override
    public void incrementNumTimesSearched() {
        this.numTimesSearched.get().incrementValue();
    }

```
###### \java\seedu\address\model\person\TagsContainKeywordPredicate.java
``` java

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagsContainKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagsContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        //return false;

        Iterator<Tag> tags = person.getTags().iterator();

        while (tags.hasNext()) {
            if (tags.next().toString().equals("[" + keyword + "]")) {
                return true;
            }
            //else tags.remove();
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagsContainKeywordPredicate) other).keyword)); // state check
    }

}
```
