package seedu.address.model.person;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's note in the address book.
 */
public class LastUpdated {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person notes can take any values";


    private String value;

    public LastUpdated(String value) {
        requireNonNull(value);
        this.value = value;
    }

    /**TODO: Restrict LastUpdated to date values
     *
     * @param test
     * @return
     */
    public static boolean isValidLastUpdated(String test) {
        return test != null;
    }

    public String getValue() {
        return this.value;
    }


    public void setValue (String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LastUpdated // instanceof handles nulls
                && this.value.equals(((LastUpdated) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
