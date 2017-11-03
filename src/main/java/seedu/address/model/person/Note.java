package seedu.address.model.person;

//@@author derrickchua
/**
 * Represents a Person's note in the address book.
 */
public class Note {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person notes can take any values";
    public static final String EMPTY_NOTE = "";

    private final String value;

    public Note(String value) {
        if (value == null) {
            value = EMPTY_NOTE;
        }
        this.value = value;
    }

    public static boolean isValidNote(String test) {
        return test != null;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && this.value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
