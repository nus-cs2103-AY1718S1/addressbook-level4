# ngzuyao
###### /Nickname.java
``` java
/**
 * This code was not used because it was redundant after the implementation of a custom field.
 * Other codes were modified accordingly to allow nickname to work properly.
 */
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's nickname in the address book.
 * Guarantees: immutable.
 */
public class Nickname {

    public static final String MESSAGE_NICKNAME_CONSTRAINTS =
            "Person nickname should be in alphanumeric format, and it should not be blank";
    public static final String NICKNAME_VALIDATION_REGEX = "\\p{Alnum}+";

    /*
     * The first character of the nickname must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String value;

    /**
     * Default nickname string.
     */
    public Nickname() {
        String defaultNickname = "noNickname";
        this.value = defaultNickname;
    }
    /**
     * Validates given nickname.
     *
     * @throws IllegalValueException if given nickname string is invalid.
     */
    public Nickname(String nicknameStr) throws IllegalValueException {
        if (nicknameStr == null) {
            this.value = new Nickname().value;
        } else {
            requireNonNull(nicknameStr);
            if (!isValidNickname(nicknameStr)) {
                throw new IllegalValueException(MESSAGE_NICKNAME_CONSTRAINTS);
            }
            this.value = nicknameStr;
        }

    }

    /**
     * Returns true if a given string is a valid nickname.
     */
    public static boolean isValidNickname(String test) {
        return test.matches(NICKNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.Nickname // instanceof handles nulls
                && this.value.equals(((seedu.address.model.person.Nickname) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /ReadOnlyPerson.java
``` java
    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress());
        if (getNickname() != null) {
            builder.append(" Nickname: ");
            builder.append(getNickname());
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
```
