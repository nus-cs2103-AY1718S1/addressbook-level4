package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is always valid
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person's birthday have to be in the format DD/MMM/YYYY, and should not be blank";
    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))"
                    + "\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May"
                    + "|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|"
                    + "^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)"
                    + "?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])"
                    + "00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|"
                    + "Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))"
                    + "\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$\n";

    public final String value;

    public Birthday(String birthday) {
        requireNonNull(birthday);
        this.value = birthday;
    }

    /*public static boolean isValidBirthday(String birthday) {
        return birthday.matches(BIRTHDAY_VALIDATION_REGEX);
    }
    */

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
