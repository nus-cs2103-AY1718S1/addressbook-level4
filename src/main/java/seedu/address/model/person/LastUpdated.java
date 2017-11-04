package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author derrickchua
/**
 * Represents a Person's note in the address book.
 */
public class LastUpdated {

    public static final String MESSAGE_LASTUPDATED_CONSTRAINTS =
            "Person update timings must fit UTC Zulu format, e.g. 2017-10-31T16:20:34.856Z, with 3 to 6 decimals";

    public static final String LASTUPDATED_VALIDATION_REGEX = "\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[1-2]\\d|3[0-1])T"
            + "(?:[0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d.\\d{3,6}Z";

    private String value;

    public LastUpdated(String value) throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(MESSAGE_LASTUPDATED_CONSTRAINTS);
        }
        if (!isValidLastUpdated(value)) {
            throw new IllegalValueException(MESSAGE_LASTUPDATED_CONSTRAINTS);
        }
        this.value = value;
    }

    public static boolean isValidLastUpdated(String test) {
        return test.matches(LASTUPDATED_VALIDATION_REGEX);
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
