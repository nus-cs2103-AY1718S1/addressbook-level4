package seedu.address.model.person;

/**
 * Represents a Person's link in the address book.
 * Guarantees: immutable; is always valid
 */
public class Link {

    public static final String MESSAGE_LINK_CONSTRAINTS =
            "Links need to be a facebook.com link";

    public final String value;

    public Link(String link) {
        if (link == null) {
            this.value = "";
        } else {
            this.value = link;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
