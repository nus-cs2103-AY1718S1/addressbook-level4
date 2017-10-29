package seedu.address.model.person;

/**
 * Represents a Person's note in the address book.
 */
public class LastUpdated {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person notes can take any values";


    private String value;

    public LastUpdated(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }


    public void setValue (String lastUpdated) {
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
                && this.value.equals(((LastUpdated) other).value));// state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}