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
###### \java\seedu\address\logic\commands\RelationshipCommand.java
``` java
/**
  * Changes the relationship of an existing person in the address book.
  */
public class RelationshipCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "relation";
    public static final String COMMAND_ALIAS = "rel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the relationship of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing relationship will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RELATIONSHIP + "[RELATIONSHIP]\n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_RELATIONSHIP + "John Doe\n"
            + "Example 2: " + COMMAND_ALIAS + " 1 "
            + PREFIX_RELATIONSHIP + "Mary Jane\n"
            + "Example 3: " + COMMAND_WORD + " 1 "
            + PREFIX_RELATIONSHIP;

    public static final String MESSAGE_ADD_RELATIONSHIP_SUCCESS = "Added relationship to Person: %1$s";
    public static final String MESSAGE_DELETE_RELATIONSHIP_SUCCESS = "Removed relationship from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Relationship relation;

    /**
      * @param index of the person in the filtered person list to edit the relation
      * @param relation of the person
      */
    public RelationshipCommand(Index index, Relationship relation) {
        requireNonNull(index);
        requireNonNull(relation);

        this.index = index;
        this.relation = relation;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getBloodType(),
                personToEdit.getTags(), personToEdit.getRemark(), this.relation, personToEdit.getAppointments());

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

    /**
     * Outputs success message based on whether a relationship is added or removed
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (personToEdit.getRelationship().toString().isEmpty()) {
            return String.format(MESSAGE_DELETE_RELATIONSHIP_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_ADD_RELATIONSHIP_SUCCESS, personToEdit);
        }
    }

    /**
     * Checks if
     * (a) Object is the same object
     * (b) Object is an instance of the object and that index and relation are the same
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RelationshipCommand // instanceof handles nulls
                && this.index.equals(((RelationshipCommand) other).index))
                && this.relation.equals(((RelationshipCommand) other).relation); // state check
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
###### \java\seedu\address\logic\parser\RelationshipCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RelationshipCommand object
 */
public class RelationshipCommandParser implements Parser<RelationshipCommand> {
    /**
      * Parses the given {@code String} of arguments in the context of the RelationshipCommand
      * and returns an RelationshipCommand object for execution.
      *
      * @throws ParseException if the user input does not conform the expected format
      */
    public RelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RELATIONSHIP);

        Index index;

        if (!isPrefixPresent(argMultimap, PREFIX_RELATIONSHIP)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RelationshipCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        String relation = argMultimap.getValue(PREFIX_RELATIONSHIP).orElse("");

        return new RelationshipCommand(index, new Relationship(relation));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
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

    /**
     * Parses a {@code Optional<String> relation} into an {@code Optional<Relationship>} if {@code relation} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Relationship> parseRelationship(Optional<String> relation) throws IllegalValueException {
        requireNonNull(relation);
        return relation.isPresent() ? Optional.of(new Relationship(relation.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address) throws IllegalValueException {
        requireNonNull(address);
        if (!isValidAddress(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }

        // To make the first letter of each word capital letter and the rest lower case
        String[] arr = address.toLowerCase().split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        this.value = sb.toString().trim();
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
        this.type = bloodType.toUpperCase();
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
###### \java\seedu\address\model\person\Email.java
``` java
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = trimmedEmail.toLowerCase();
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }

        // To make the first letter of each word capital letter and the rest lower case
        String[] arr = trimmedName.toLowerCase().split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        this.fullName = sb.toString().trim();
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

    public void setRelationship(Relationship relation) {
        this.relation.set(requireNonNull(relation));
    }

    @Override
    public ObjectProperty<Relationship> relationshipProperty() {
        return relation;
    }

    @Override
    public Relationship getRelationship() {
        return relation.get();
    }
```
###### \java\seedu\address\model\person\Relationship.java
``` java
/**
  * Represents a Person's relationship in the address book.
  * Guarantees: immutable; is always valid
  */
public class Relationship {

    public final String value;

    public Relationship(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && this.value.equals(((Relationship) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
