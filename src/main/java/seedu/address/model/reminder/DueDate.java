package seedu.address.model.reminder;
//@@author RonakLakhotia
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Reminders DueDate in the address book.
 */

public class DueDate {

    /**
     * Represents a Reminders DueDate
     * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
     */

    public static final String DUE_DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Due Date must be a Valid Date and in the following format: \n"
                    + "'.' and '/' can be used as separators. \n";

    public final String date;

    public DueDate(String date) throws IllegalValueException {

        String trimmedDate;
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (!date.equals("")) {
            trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = "";
        }

    }

    @Override
    public String toString() {
        return date;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidDate(String dueDate) {

        String trimmedDate = dueDate.trim();
        if (trimmedDate.isEmpty()) {
            return false;
        }
        if (!trimmedDate.matches(DUE_DATE_VALIDATION_REGEX)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DueDate
                && this.date.equals(((DueDate) other).date));
    }
    @Override
    public int hashCode() {
        return date.hashCode();
    }


}
