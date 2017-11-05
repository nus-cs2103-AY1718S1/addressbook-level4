# wynkheng
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday, String command)
            throws IllegalValueException {
        requireNonNull(birthday);
        if (command.equals("add")) {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.of(new Birthday(0));
        } else {
            return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
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
###### \java\seedu\address\model\person\Person.java
``` java
    public boolean setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return true;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
