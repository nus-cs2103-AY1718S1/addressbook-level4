package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Schedule's activity description in the address book.
 * Guarantees: immutable; is always valid
 */
public class Activity {

    public static final String MESSAGE_ACTIVITY_CONSTRAINTS =
            "Activity description can take any values, and should not be blank";

    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public Activity(String activity) {
        requireNonNull(activity);
        this.value = activity;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Activity // instanceof handles nulls
                && this.value.equals(((Activity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

