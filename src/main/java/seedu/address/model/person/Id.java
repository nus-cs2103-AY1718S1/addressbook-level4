package seedu.address.model.person;

//@@author derrickchua
/**
 * Represents a Person's note in the address book.
 */
public class Id {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person id can take any values";
    public static final String EMPTY_ID = "";

    private String value;

    public Id(String value) {
        if (value == null) {
            value = EMPTY_ID;
        }
        this.value = value;
    }

    public static boolean isValidId(String test) {
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
                || (other instanceof Id // instanceof handles nulls
                && this.value.equals(((Id) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
