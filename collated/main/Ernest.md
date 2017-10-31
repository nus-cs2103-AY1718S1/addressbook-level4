# Ernest
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    public void setBloodType(Bloodtype bloodType) {
            this.bloodType = bloodType;
    }

    public Optional<Bloodtype> getBloodType() {
            return Optional.ofNullable(bloodType);
    }
```
###### \java\seedu\address\logic\commands\ListByBloodtypeCommand.java
``` java
/**
 * Finds and lists all persons in address book whose blood type matches the keyword.
 * Keyword matching is case insensitive.
 */
public class ListByBloodtypeCommand extends Command {

    public static final String COMMAND_WORD = "bloodtype";
    public static final String COMMAND_ALIAS = "bt"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons of a blood type "
            + " and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " AB+\n"
            + "Example: " + COMMAND_ALIAS + " O";

    private final BloodtypeContainsKeywordPredicate predicate;

    public ListByBloodtypeCommand(BloodtypeContainsKeywordPredicate predicate) {
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
                || (other instanceof ListByBloodtypeCommand // instanceof handles nulls
                && this.predicate.equals(((ListByBloodtypeCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\ListByBloodtypeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListByBloodtypeCommand object
 */
public class ListByBloodtypeCommandParser implements Parser<ListByBloodtypeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListByBloodtypeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListByBloodtypeCommand.MESSAGE_USAGE));
        }

        String[] bloodTypeKeyword = trimmedArgs.split("\\s+");

        return new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(Arrays.asList(bloodTypeKeyword)));

    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> bloodType} into an {@code Optional<Bloodtype>} if {@code bloodType} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Bloodtype> parseBloodType(Optional<String> bloodType) throws IllegalValueException {
        requireNonNull(bloodType);
        return bloodType.isPresent() ? Optional.of(new Bloodtype(bloodType.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Bloodtype.java
``` java
/**
 * Represents a Person's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class Bloodtype {

    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS = "Person blood type should not be blank.\n"
            + "Valid inputs are: A, A+, A-, B, B+, B-, O, O+, O-, AB, AB+, AB-. \n"
            + "Both capital letters and small letters are allowed.";

    // Checks for a, b, ab, or o at start of string.
    // Characters are case insensitive.
    // Next check is for "+" or "-". "+" and "-" does not have to be added.
    // Credit to lena15n for assistance with regex
    public static final String NON_COMPULSORY_BLOODTYPE = "xxx";
    public static final String BLOODTYPE_VALIDATION_REGEX = "(?i)^(a|b|ab|o|xxx)[\\+|\\-]{0,1}$";

    public final String type;

    /**
     * Validates given bloodtype.
     *
     * @throws IllegalValueException if given bloodtype string is invalid.
     */
    public Bloodtype(String bloodType) throws IllegalValueException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();
        if (!isValidBloodType(trimmedBloodType)) {
            throw new IllegalValueException(MESSAGE_BLOODTYPE_CONSTRAINTS);
        }
        this.type = bloodType;
    }


    /**
     * Returns true if a given string is a valid person blood type.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(BLOODTYPE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bloodtype // instanceof handles nulls
                && this.type.equals(((Bloodtype) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
```
###### \java\seedu\address\model\person\BloodtypeContainsKeywordPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Bloodtype} matches any of the keywords given.
 */
public class BloodtypeContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keyword;

    public BloodtypeContainsKeywordPredicate(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBloodType().type, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodtypeContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((BloodtypeContainsKeywordPredicate) other).keyword)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBloodType(Bloodtype bloodType) {
        this.bloodType.set(requireNonNull(bloodType));
    }

    @Override
    public ObjectProperty<Bloodtype> bloodTypeProperty() {
        return bloodType;
    }

    @Override
    public Bloodtype getBloodType() {
        return bloodType.get();
    }
```
