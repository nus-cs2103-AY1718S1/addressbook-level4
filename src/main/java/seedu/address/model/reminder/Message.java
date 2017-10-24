package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

/**
 * Represents a reminder's message in the address book.
 * Guarantees: immutable
 */
public class Message {

    public final String message;

    /**
     * Validates given message.
     */
    public Message(String message) {
        requireNonNull(message);
        this.message = message.trim();
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Message // instanceof handles nulls
                && this.message.equals(((Message) other).message)); // state check
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }

}
