package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author chuaweiwen
/**
 * Represents a Person's nickname in the address book.
 * Guarantees: immutable; is valid
 */
public class Nickname {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person's nickname can take any values.";

    public final String value;

    public Nickname(String nickname) {
        requireNonNull(nickname);
        this.value = nickname;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nickname // instanceof handles nulls
                && this.value.equals(((Nickname) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//@@author
