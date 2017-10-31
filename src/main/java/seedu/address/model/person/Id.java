package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's note in the address book.
 */
public class Id {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person value can take any values";


    private String value;

    public Id(String value) {
        requireNonNull(value);
        this.value = value;
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
                || (other instanceof Id // instanceof handles nulls
                && this.value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
