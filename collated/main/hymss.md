# hymss
###### \java\seedu\address\logic\commands\person\ShowBirthdaysCommand.java
``` java
/**
 * Lists all persons in Bluebird whose birthday is on the current day with respect to the user.
 */

public class ShowBirthdaysCommand extends Command {

    public static final String COMMAND_WORD = "showbirthdays";

    public static final String MESSAGE_SUCCESS = "Chirp! Here are the birthdays for today.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all persons whose birthdays are today.\n"
            + "Parameters: KEYWORD\n"
            + "Example for showing birthdays: " + COMMAND_WORD;

    private CheckBirthdays checker = new CheckBirthdays();

    public ShowBirthdaysCommand() {

    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(checker);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthdays can only contain numbers and forward slashes, and it should not be blank."
                    + " The birthday must be in the form DD/MM/YY or DD/MM/YYYY";

    /**
     * The first character of the birthday must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";


    public final String value;

    /**
     * Validates given birthday
     *
     * @throws IllegalValueException if given birthday string is invalid
     */

    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Provides a default birthday (" ") when field is empty.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(int checkValue) throws IllegalValueException {
        if (checkValue != 0) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = " ";
    }
    /**
     * Returns true if a given string is a valid birthday
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this                                     // short circuit if same object
                || (other instanceof Birthday                    // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\CheckBirthdays.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday month and day} matches date of current day with respect to
 * user.
 */

public class CheckBirthdays implements Predicate<ReadOnlyPerson> {

    public CheckBirthdays() {

    }

    /**
     * Checks if a person's birthday falls on the current day.
     *
     * @param person
     * @return boolean
     * @throws ParseException
     */

    public boolean showBirthdays(ReadOnlyPerson person) throws ParseException {
        String birthday = person.getBirthday().toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (((calendar.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH))
                && ((calendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))));
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean index = false;
        try {
            index = showBirthdays(person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckBirthdays); // instanceof handles nulls
    }
}
```
