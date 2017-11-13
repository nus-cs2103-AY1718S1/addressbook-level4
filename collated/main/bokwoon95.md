# bokwoon95
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, AND a full word match is NOT required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.toLowerCase().contains(preppedWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\logic\commands\FuzzyfindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FuzzyfindCommand extends Command {

    public static final String COMMAND_WORD = "fuzzyfind";
    public static final String COMMAND_ALIAS = "ff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "This behaves just like 'Find' except it will match match substrings instead of just words.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsSubstringsPredicate predicate;

    public FuzzyfindCommand(NameContainsSubstringsPredicate predicate) {
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
                || (other instanceof FuzzyfindCommand // instanceof handles nulls
                && this.predicate.equals(((FuzzyfindCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\FuzzyfindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FuzzyFindCommand object
 */
public class FuzzyfindCommandParser implements Parser<FuzzyfindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FuzzyfindCommand
     * and returns an FuzzyfindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FuzzyfindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FuzzyfindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FuzzyfindCommand(new NameContainsSubstringsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        try {
            persons.add(newPerson);
        } catch (DuplicatePersonException dpe) {
            throw new DuplicatePersonException();
        }
        syncMasterTagListWith(newPerson);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        try {
            persons.setPerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new DuplicatePersonException();
        } catch (PersonNotFoundException pnfe) {
            throw new PersonNotFoundException();
        }
        syncMasterTagListWith(editedPerson);
    }
```
###### \java\seedu\address\model\person\Instagram.java
``` java
public class Instagram implements Comparable {

    public static final String MESSAGE_INSTAGRAM_CONSTRAINTS =
            "Person addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INSTAGRAM_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Instagram(String instagram) throws IllegalValueException {
        requireNonNull(instagram);
        if (!isValidInstagram(instagram)) {
            throw new IllegalValueException(MESSAGE_INSTAGRAM_CONSTRAINTS);
        }
        this.value = instagram;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidInstagram(String test) {
        return test.matches(INSTAGRAM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        Instagram comparedInstagram = (Instagram) o;
        return this.value.compareTo(comparedInstagram.toString());
    }

}
```
###### \java\seedu\address\model\person\NameContainsSubstringsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsSubstringsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsSubstringsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsSubstringsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsSubstringsPredicate) other).keywords)); // state check
    }

}
```
