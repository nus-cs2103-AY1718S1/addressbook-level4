# lauy99
###### /java/seedu/address/logic/commands/ExpireCommand.java
``` java
/**
 * Sets expiry date of a person in the address book.
 */

public class ExpireCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "expire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the expiry date "
            + "of the person identified by the index number used in the last person listing. "
            + "Existing expiry date will be updated with the input. "
            + "Expiry date will be removed if input is empty. \n"
            + "Parameters: INDEX (must be positive integer) "
            + PREFIX_EXPIRE + "[DATE in YYYY-MM-DD format]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRE + "2017-09-09";

    public static final String MESSAGE_SET_EXPIRY_DATE_SUCCESS = "Expiry date of %1$s set as %2$s.";
    public static final String MESSAGE_DELETE_EXPIRY_DATE_SUCCESS = "Removed expiry date of Person: %1$s.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No such person in Address Book.";

    private final Index index;
    private final ExpiryDate date;

    public ExpireCommand(Index index, ExpiryDate date) {
        requireNonNull(index);
        requireNonNull(date);

        this.index = index;
        // create ExpiryDate object
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), date, personToEdit.getRemark(),
                personToEdit.getImage());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generate success message for:
     * Set expiry date: with person name and new expiry date
     * Remove expiry date: with person name
     */
    private String generateSuccessMessage(ReadOnlyPerson person) {
        if (!date.toString().isEmpty()) {
            return String.format(MESSAGE_SET_EXPIRY_DATE_SUCCESS, person.getName(), date.toString());
        } else {
            return String.format(MESSAGE_DELETE_EXPIRY_DATE_SUCCESS, person.getName());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpireCommand)) {
            return false;
        }

        ExpireCommand temp = (ExpireCommand) other;
        return (index.equals(temp.index) && date.equals(temp.date));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            ExpiryDate expiryDate;
            if (arePrefixesPresent(argMultimap, PREFIX_EXPIRE)) {
                expiryDate = ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRE)).get();
            } else {
                expiryDate = new ExpiryDate("");
            }
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ExpiryDate editedDate;
            try {
                editedDate = ParserUtil.parseExpiryDate(argMultimap.getValue(PREFIX_EXPIRE)).get();
            } catch (NoSuchElementException nsee) {
                editedDate = null;
            }
            editPersonDescriptor.setExpiryDate(editedDate);
```
###### /java/seedu/address/logic/parser/ExpireCommandParser.java
``` java
/**
 * Parses arguments of expire command
 */

public class ExpireCommandParser implements Parser<ExpireCommand> {
    /**
     * Parse given {@code String} of arguements into context of ExpireCommand
     * and returns an ExpireCommand object for execution.
     * @throws ParseException if user input does not conform the expected format
     */
    public ExpireCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXPIRE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String dateString = argMultimap.getValue(PREFIX_EXPIRE).orElse("");
            ExpiryDate date = new ExpiryDate(dateString);
            return new ExpireCommand(index, date);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpireCommand.MESSAGE_USAGE));
        }

    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> dateString} into an {@code Optional<ExpiryDate>}
     * if {@code dateString} is present.
     */
    public static Optional<ExpiryDate> parseExpiryDate(Optional<String> dateString) throws IllegalValueException {
        requireNonNull(dateString);
        if (dateString.isPresent()) {
            return Optional.of(new ExpiryDate(dateString.get()));
        } else {
            return Optional.empty();
        }
    }
}
```
###### /java/seedu/address/model/person/ExpiryDate.java
``` java
/**
 * Represents a Person's expiry date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {

    public static final String MESSAGE_EXPIRY_DATE_CONSTRAINTS =
            "Person's expiry date should be dates later than current date, in the form of YYYY-MM-DD";

    /*
     * Date format is YYYY-MM-DD
     */
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);


    public final Date value;

    /**
     * Validates given date.
     *     Input string can be empty (expiry date = NULL)
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public ExpiryDate(String date) throws IllegalValueException {
        if (date == null) {
            this.value = null;
            return;
        } else if (date.isEmpty()) {
            this.value = null;
            return;
        } else if (!isValidExpiryDate(date)) {
            throw new IllegalValueException(MESSAGE_EXPIRY_DATE_CONSTRAINTS);
        }
        // catches invalid month/day combination
        // will check for invalid date combinations
        DATE_FORMATTER.setLenient(false);
        ParsePosition parsePos = new ParsePosition(0);
        this.value = DATE_FORMATTER.parse(date, parsePos);

        // date formatter parse error
        if (parsePos.getIndex() == 0) {
            throw new IllegalValueException(MESSAGE_EXPIRY_DATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid date format.
     */
    public static boolean isValidExpiryDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        } else {
            return DATE_FORMATTER.format(value);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // same object
            return true;
        } else if (this.value == null) {
            // to prevent NullPointerException from expiry date
            // both objects have null expiry date
            return ((ExpiryDate) other).value == null;
        } else {
            // date must not be null
            return this.value.equals(((ExpiryDate) other).value);
        }
    }
}
```
